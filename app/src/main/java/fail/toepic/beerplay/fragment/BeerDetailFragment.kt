package fail.toepic.beerplay.fragment

import android.os.Bundle
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.NavOptions
import androidx.navigation.Navigation
import fail.toepic.beerplay.R
import fail.toepic.beerplay.TitleChangeable
import fail.toepic.beerplay.connectivity.Repository
import fail.toepic.beerplay.model.Beer
import fail.toepic.beerplay.model.ValueUnit
import kotlinx.android.synthetic.main.fragment_beer_detail.*
import kotlinx.android.synthetic.main.two_column.view.*
import android.text.Spannable
import android.text.style.ForegroundColorSpan
import androidx.core.content.ContextCompat
import android.text.SpannableString

import android.text.SpannableStringBuilder
import android.view.*
import java.lang.StringBuilder
import android.view.MenuInflater
import fail.toepic.beerplay.model.Ingredients
import fail.toepic.beerplay.model.Method


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

    private var beer: Beer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_beer_detail, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

//        test.setOnClickListener {
//            doBuy(it,"10")
//        }

        Repository.instance.loadBeerDetail(beerID)?.let{
            setDetail(it)
        }


    }

    override fun onResume() {
        super.onResume()


        changeTitle(getString(R.string.beer_detail_label))

        beer?.let {
            setDetail(it)
        }

    }

    private fun changeTitle(title : String) {
        val activity = requireActivity()
        if (activity is TitleChangeable) {
            activity.changeTitle(title)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater?.inflate(fail.toepic.beerplay.R.menu.beer_detail_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {

        item?.let {
            if (it.itemId == R.id.buy) {
                doBuy(first_brews,beerID)
            }
        }

        return super.onOptionsItemSelected(item)
    }

    private fun setDetail(beer: Beer) {
        this.beer = beer

        changeTitle("${beer.name} - ${beer.id}")

//        val image_url : String = ""

        // SECTION 1 START
        setTwoColumn(first_brews, R.string.first_brewed, beer.first_brewed)
        setTwoColumn(tags, R.string.tagline, beer.tagline)
        setTwoColumn(description, R.string.description, beer.description)
        // SECTION 1 END

        // SECTION 2 START
        setTwoColumn(abv, R.string.abv, beer.ABV)
        setTwoColumn(og, R.string.original_gravity, beer.target_original_gravity)
        setTwoColumn(fg, R.string.final_gravity, beer.target_final_gravity)
        setTwoColumn(attenuation_level, R.string.attenuation_level, beer.attenuation_level)
        setTwoColumn(ibu, R.string.international_bitterness_unit, beer.IBU)

        setTwoColumn(ebc, R.string.EBC, beer.EBC)
        color.setBackgroundResource(getBeerColor(beer.srm))
        setTwoColumn(srm, R.string.SRM, beer.srm)

        setTwoColumn(ph, R.string.ph, beer.ph)
        setTriColumnUnit(volume, R.string.volume, beer.volume)
        setTriColumnUnit(boil_volume, R.string.boil_volume, beer.boil_volume)
        // SECTION 2 END



//        val method :  Method = Method()

        setMethod(beer.method)

        setIngredients(beer.ingredients)

        setTwoColumnWithList(food_pairing, R.string.food_pairing,beer.food_pairing)
        setTwoColumn(brewers_tips, R.string.brewers_tips, beer.brewers_tips)
        setTwoColumn(contributed_by, R.string.contributed_by, beer.contributed_by)

    }

    private fun setMethod(method: Method) {
        setTwoColumn(this.method, R.string.method, "")
        val li = arrayListOf<String>()
        for (mashTemp in method.mash_temp) {
            val temp = getValueUnitString(mashTemp.temp).toString().replace(" ","")
            val string  = "${temp} ${mashTemp.duration}"
            li.add(string)
        }
        setTwoColumnWithList(this.mash, R.string.mash_temp, li)
        setTwoColumnWithList(this.fermentation, R.string.fermentation, arrayListOf(getValueUnitString(method.fermentation.temp).toString().replace(" ","")))
        setTwoColumnWithList(this.twist,R.string.twist, arrayListOf(method.twist))
    }

    private fun setIngredients(ingredients: Ingredients) {
        setTwoColumn(this.ingredients, R.string.ingredients, "")

        val malts = ingredients.malt.map {
                it.name + " :  " + getValueUnitString(it.amount).toString().replace(" ","")
            }
            setTwoColumnWithList(this.malt, R.string.malt, malts)

        val hops = ingredients.hops.map {
                it.name + " :  " + getValueUnitString(it.amount).toString().replace(" ","") + " + " + it.add + "(" +it.attribute + ")"
            }
            setTwoColumnWithList(this.hops, R.string.hops, hops)
        setTwoColumnWithList(this.yeast, R.string.yeast, arrayListOf(ingredients.yeast))
    }

    private fun setTwoColumnWithList(v: View, labelResId: Int, list: List<String>) {
        v.label.text = getString(labelResId)

        val sb = StringBuilder()

        for (food in list) {
            sb.append(" * ")
            sb.append(food)
            sb.append("\n")
        }
        sb.append("\n")
        v.text.text = sb.toString().replace("\n\n","")

    }

    /**
     * SRM : Sandard Reference Method 을 기준으로 맥주 색을 반환한다.
     */
    private fun getBeerColor(srm: Float): Int {
        if(srm > 40){
            return R.color.srm_40_over
        }else if(srm >= 35){
            return R.color.srm_35
        }else if(srm >= 29){
            return R.color.srm_29
        }else if(srm >= 24){
            return R.color.srm_24
        }else if(srm >= 20){
            return R.color.srm_20
        }else if(srm >= 17){
            return R.color.srm_17
        }else if(srm >= 13){
            return R.color.srm_13
        }else if(srm >= 10){
            return R.color.srm_10
        }else if(srm >= 8){
            return R.color.srm_8
        }else if(srm >= 6){
            return R.color.srm_6
        }else if(srm >= 4){
            return R.color.srm_4
        }else if(srm >= 3){
            return R.color.srm_3
        }else {
            return R.color.srm_2
        }
    }

    private fun setTwoColumn(v: View, labelResId: Int, int: Int) = setTwoColumn(v,labelResId,int.toString())
    private fun setTwoColumn(v: View, labelResId: Int, float: Float) = setTwoColumn(v,labelResId,float.toString())

    private fun setTwoColumn(v: View, labelResId: Int, text: String) {
        v.label.text = getString(labelResId)
        v.text.text = text
    }

    private fun setTriColumnUnit(v: View, label: Int, volume: ValueUnit) {
        v.label.text = getString(label)


        val ssb = getValueUnitString(volume)

        v.text.text = ssb
    }

    private fun getValueUnitString(volume: ValueUnit): SpannableStringBuilder {
        val color = ContextCompat.getColor(requireContext(), R.color.colorPrimary)
        val backgroundSpan = ForegroundColorSpan(color)
        val spannableString = SpannableString(volume.unit)
        spannableString.setSpan(backgroundSpan, 0, spannableString.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)

        val ssb = SpannableStringBuilder()
        ssb.append(volume.value.toString())
        ssb.append("  ")
        ssb.append(spannableString)
        return ssb
    }

}