package fail.toepic.beerplay.fragment

import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import fail.toepic.beerplay.R
import fail.toepic.beerplay.TitleChangeable
import fail.toepic.beerplay.adapter.BeerListItem
import fail.toepic.beerplay.adapter.BeerListAdapter
import fail.toepic.beerplay.connectivity.Repository
import fail.toepic.beerplay.model.Beer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.fragment_beer_list.*
import kotlinx.android.synthetic.main.fragment_beer_list.view.*


class BeerListFragment : Fragment(){

    companion object {
        val BUY_USER = "buy_user"
    }

    val beyUser : String?
        get() = arguments?.getString(BUY_USER)

    val doShowDetail : (View, String)->Unit =  { v, id->
        Navigation.findNavController(v).navigate(R.id.action_show_detail, bundleOf(BeerDetailFragment.BEER_ID to id))
    }

    val adapter = BeerListAdapter()

    val compositeDisposable : CompositeDisposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //NOTE 페이징 구현을 유보 하였음으로 옵션메뉴를 제거한다.
//        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_beer_list, container, false)
//        view.test.setOnClickListener {
//            doShowDetail(it,"10")
//        }

        view.list.layoutManager = LinearLayoutManager(requireContext(),RecyclerView.VERTICAL,false)
        view.list.adapter =  adapter

        val complite = Repository.instance.loadcomplete
                .observeOn(AndroidSchedulers.mainThread())
                .map { it.map { beer -> BeerListItem(beer,0) }}
                .subscribe {adapter.submitList(it)}
        compositeDisposable.add(complite)

        Repository.instance.load()

//        adapter.submitList(listOf(BeerListItem(Repository.instance.loadBeerDetail("1")!!,1),BeerListItem(Beer("0","name"),0)))
        return view
    }


    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater?.inflate(R.menu.beer_list_menu, menu)

    }


    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item!!.itemId) {
            R.id.load -> adapter.submitList(listOf(BeerListItem(Beer("0","name1"),0),BeerListItem(Beer("0","name"),0)))
            R.id.reload -> doShowDetail(list,"1")
//            R.id.add -> numbers.value = listOf(0, 1, 2, 3, 4, 5, 6, 7, 8, 9)
//            R.id.remove -> numbers.value = listOf(2, 4, 6, 8, 9)
//            R.id.reorder -> numbers.value = numbers.value!!.shuffled()
        }
        return true
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        beyUser?.let {

            Toast.makeText(requireActivity(),"beer buy by $it",Toast.LENGTH_SHORT).show()
            arguments?.clear()

        }

    }

    override fun onResume() {
        super.onResume()
        val activity = requireActivity()
        if ( activity is TitleChangeable) {
            activity.changeTitle("Bear LIst")
        }



        val onlcik = adapter.onClickSubject.observeOn(AndroidSchedulers.mainThread())
            .subscribe {

                findNavController().navigate(R.id.action_show_beer_detail, bundleOf(BeerDetailFragment.BEER_ID to it.id))

            }

        compositeDisposable.add(onlcik)
//

    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.dispose()
    }
}
