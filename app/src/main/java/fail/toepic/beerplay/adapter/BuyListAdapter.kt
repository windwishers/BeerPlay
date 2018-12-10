package fail.toepic.beerplay.adapter

import android.text.Spannable
import android.text.SpannableString
import android.text.SpannableStringBuilder
import android.text.style.ForegroundColorSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton
import fail.toepic.beerplay.R
import fail.toepic.beerplay.model.Beer
import fail.toepic.beerplay.model.Ingredients
import fail.toepic.beerplay.model.ValueUnit
import kotlinx.android.synthetic.main.fragment_beer_detail.*
import kotlinx.android.synthetic.main.item_beer_buy.view.*
import kotlinx.android.synthetic.main.two_column.view.*
import java.lang.StringBuilder


class BuyListAdapter() :
    RecyclerView.Adapter<BuyItemViewHolder>() {

    var beers : List<BuyItem> = listOf()

    constructor(beers: Array<Beer>) : this() {
        beers.map { it -> BuyItem(it,0) }.let {
            this.beers = it
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BuyItemViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_beer_buy, parent, false)
        // set the view's size, margins, paddings and layout parameters

        return BuyItemViewHolder(view).init()
    }

    override fun getItemCount(): Int {
        return  beers.size
    }

    override fun onBindViewHolder(holder: BuyItemViewHolder, position: Int) {
        holder.bind(beers.get(position))
    }

}

class BuyItemViewHolder(view : View) : RecyclerView.ViewHolder(view){

    val name : TextView by lazy { itemView.name }
//    val count : EditText by lazy { itemView.count }
    var item : BuyItem? = null

    fun init() : BuyItemViewHolder {
        return this
    }

    fun bind(item : BuyItem){
        this.item = item
        name.text = item.beer.name

        setIngredients(item.beer.ingredients)
//        updateCount(item.count)
    }

//    fun updateCount(count : Int){
//        this.count.text = SpannableStringBuilder(count.toString())
//        item?.count = count
//    }

    private fun setIngredients(ingredients: Ingredients) {
        setTwoColumn(itemView.ingredients, R.string.ingredients, "")

        val malts = ingredients.malt.map {
            it.name + " :  " + getValueUnitString(it.amount).toString().replace(" ","")
        }
        setTwoColumnWithList(itemView.malt, R.string.malt, malts)

        val hops = ingredients.hops.map {
            it.name + " :  " + getValueUnitString(it.amount).toString().replace(" ","") + " + " + it.add + "(" +it.attribute + ")"
        }
        setTwoColumnWithList(itemView.hops, R.string.hops, hops)
        setTwoColumnWithList(itemView.yeast, R.string.yeast, arrayListOf(ingredients.yeast))
    }

    private fun getValueUnitString(amount: ValueUnit): String = "${amount.value} ${amount.unit}"

    private fun setTwoColumn(v: View, labelResId: Int, text: String) {

        v.label.text = v.context.getString(labelResId)
        v.text.text = text
    }

    private fun setTwoColumnWithList(v: View, labelResId: Int, list: List<String>) {
        v.label.text = v.context.getString(labelResId)

        val sb = StringBuilder()

        for (food in list) {
            sb.append(" * ")
            sb.append(food)
            sb.append("\n")
        }
        sb.append("\n")
        v.text.text = sb.toString().replace("\n\n","")

    }

}

data class BuyItem(val beer:Beer,var count:Int)