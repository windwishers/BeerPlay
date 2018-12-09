package fail.toepic.beerplay.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.NavOptions
import androidx.navigation.Navigation
import fail.toepic.beerplay.R
import fail.toepic.beerplay.TitleChangeable
import fail.toepic.beerplay.util.MoveTo
import kotlinx.android.synthetic.main.fragment_beer_list.*

class BeerDetailFragment : Fragment(){

    companion object {
        val BEER_ID = "beer_id"
    }

    val beerID : String
        get() = arguments?.getString(BEER_ID)?:""

    val doBuy : (View,String)->Unit =  { v,id->
        val option = NavOptions.Builder()
            .setPopUpTo(R.id.beerDetail,true).build()
        Navigation.findNavController(v).navigate(R.id.action_beer_buy, bundleOf(BeerBuyFragment.BUY_ID to id),option)
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

    override fun onResume() {
        super.onResume()
        val activity = requireActivity()
        if ( activity is TitleChangeable) {
            activity.changeTitle("Bear Detail")
        }
    }

}