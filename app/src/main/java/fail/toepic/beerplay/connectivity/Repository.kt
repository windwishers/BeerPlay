package fail.toepic.beerplay.connectivity

import android.util.Log
import com.google.gson.Gson
import fail.toepic.beerplay.model.Beer
import io.reactivex.Scheduler
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import java.util.concurrent.ConcurrentHashMap

class Repository private constructor() {

    private object Holder { val INSTANCE = Repository()}

    companion object {
        val instance: Repository by lazy { Holder.INSTANCE }
    }

    val loadcomplete : PublishSubject<List<Beer>> = PublishSubject.create()

    val beerMap : ConcurrentHashMap<String,Beer> = ConcurrentHashMap()

    fun loadBeerDetail(id : String) : Beer? {
        synchronized(beerMap){
            return beerMap[id]
        }
    }

    fun load() {
        val dispoble = PunkApi.create().getBeers(1, 20)
            .subscribeOn(Schedulers.io())
            .subscribe ({ li ->
                storeDetail(li)
                loadcomplete.onNext(li)
            },{e ->
            }
            )
    }

    private fun storeDetail(li: List<Beer>?) {
        synchronized(beerMap) {
            li?.forEach { beer: Beer ->
                beerMap[beer.id] = beer
            }
        }
    }

}