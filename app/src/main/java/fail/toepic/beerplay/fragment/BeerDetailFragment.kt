package fail.toepic.beerplay.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import fail.toepic.beerplay.R
import fail.toepic.beerplay.util.MoveTo
import kotlinx.android.synthetic.main.fragment_beer_list.*

class BeerDetailFragment : Fragment(){

    companion object {
        val BEER_ID = "beer_id"
    }

    val beerID : String
        get() = arguments?.getString(BEER_ID)?:""

    val doBuy : (View,String)->Unit =  { v,id->
        MoveTo(R.id.action_beer_buy,v, bundleOf(BeerBuyFragment.BUY_ID to id))
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_beer_detail, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        test.setOnClickListener {
            doBuy(it,"10")
        }

    }


}