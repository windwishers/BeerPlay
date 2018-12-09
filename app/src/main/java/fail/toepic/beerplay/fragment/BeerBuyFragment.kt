package fail.toepic.beerplay.fragment

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.NavOptions
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import fail.toepic.beerplay.*
import fail.toepic.beerplay.adapter.BuyListAdapter
import fail.toepic.beerplay.connectivity.Repository
import fail.toepic.beerplay.util.MoveTo
import kotlinx.android.synthetic.main.fragment_beer_buy.*

class BeerBuyFragment : Fragment() {

    companion object {
        val BUY_ID = "buy_id"
    }

    val buyID : String
        get() = arguments?.getString(BUY_ID)?:""

    val doBuyConfirm : (View, String)->Unit =  { v, name->
        val option = NavOptions.Builder()
            .setPopUpTo(R.id.buy_confirm,true).build()
        Navigation.findNavController(v).navigate(R.id.beerList, bundleOf(BeerListFragment.BUY_USER to name),option)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        Log.d("dlwlrma","BUY ID : "+buyID)

        return inflater.inflate(R.layout.fragment_beer_buy, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        confirm.setOnClickListener {

            val len = buyer_text.text?.length?:0

            if(len <= 0){
                Toast.makeText(requireContext(),getString(R.string.check_name),Toast.LENGTH_SHORT).show()
            }else{
                doBuyConfirm(it,buyer_text.text.toString())
            }
        }

        val beers = Repository.instance.loadBeerDetail(buyID)

        beers?.let {
            list.adapter = BuyListAdapter(arrayOf(it))
        }
        list.layoutManager = LinearLayoutManager(requireContext(),RecyclerView.VERTICAL,false)

    }

    override fun onResume() {
        super.onResume()
        val activity = requireActivity()
        if ( activity is TitleChangeable) {
            activity.changeTitle(getString(R.string.title_buy_fragment))
        }
    }


}