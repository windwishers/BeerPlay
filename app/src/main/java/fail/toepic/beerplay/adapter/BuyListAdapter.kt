package fail.toepic.beerplay.adapter

import android.text.SpannableString
import android.text.SpannableStringBuilder
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton
import fail.toepic.beerplay.R
import fail.toepic.beerplay.model.Beer
import kotlinx.android.synthetic.main.item_beer_buy.view.*


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
    val count : EditText by lazy { itemView.count }
    val add : MaterialButton by lazy { itemView.add }
    val remove : MaterialButton by lazy { itemView.remove }
    var item : BuyItem? = null

    fun init() : BuyItemViewHolder {
        add.setOnClickListener {
            item?.let {
                it.count++
                updateCount(it.count)
            }
        }

        remove.setOnClickListener {
            item?.let {
                if(it.count>0) {
                    it.count--
                    updateCount(it.count)
                }
            }
        }
        return this
    }

    fun bind(item : BuyItem){
        this.item = item
        name.text = item.beer.name
        updateCount(item.count)
    }

    fun updateCount(count : Int){
        this.count.text = SpannableStringBuilder(count.toString())
        item?.count = count
    }
}

data class BuyItem(val beer:Beer,var count:Int)