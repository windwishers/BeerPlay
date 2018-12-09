package fail.toepic.beerplay.fragment

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.IdRes
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.NavOptions
import androidx.navigation.Navigation
import fail.toepic.beerplay.BeerPlayActivity

import fail.toepic.beerplay.R
import fail.toepic.beerplay.TitleChangeable
import fail.toepic.beerplay.util.MoveTo
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

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_beer_list, container, false)
        view.test.setOnClickListener {
            doShowDetail(it,"10")
        }
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        beyUser?.let {

            Toast.makeText(requireActivity(),"beer buy by $it",Toast.LENGTH_SHORT).show()

        }

    }

    override fun onResume() {
        super.onResume()
        val activity = requireActivity()
        if ( activity is TitleChangeable) {
            activity.changeTitle("Bear LIst")
        }
    }



}

