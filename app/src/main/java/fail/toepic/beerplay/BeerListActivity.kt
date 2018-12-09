package fail.toepic.beerplay


import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import fail.toepic.beerplay.connectivity.PunkApi
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers


class BeerListActivity : AppCompatActivity() , TitleChangeable {

    override fun changeTitle(title: String) {

    }

    val compositeDisposable : CompositeDisposable = CompositeDisposable()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_beer_list)

//        reloadList()

    }

    private fun reloadList() {
        val disposable = PunkApi.create().getBeers(1, 20)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(Consumer {
                for (beer in it) {
                    Log.d("dlwlrma", "$beer")
                }
            })
        //싱글이라서 바로 종료 되기 때문에 없어도 상관은 없음.
        compositeDisposable.add(disposable)
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.dispose()
    }
}

