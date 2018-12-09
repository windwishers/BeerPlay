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
import kotlinx.android.synthetic.main.fragment_beer_buy.*

class BeerBuyFragment : Fragment(){

    companion object {
        val BUY_ID = "buy_id"
    }

    val butID : String
        get() = arguments?.getString(BUY_ID)?:""

    val doBuyConfirm : (View, String)->Unit =  { v, name->
        MoveTo(R.id.action_buy_confirm,v, bundleOf(BeerListFragment.BUY_USER to name))
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        Log.d("dlwlrma","BUY ID : "+butID)



        return inflater.inflate(R.layout.fragment_beer_buy, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        test.setOnClickListener {doBuyConfirm(it,"Dummy")
        }
    }


}