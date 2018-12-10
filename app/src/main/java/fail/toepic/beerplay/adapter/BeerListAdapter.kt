package fail.toepic.beerplay.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import fail.toepic.beerplay.R
import fail.toepic.beerplay.fragment.getBeerColorID
import fail.toepic.beerplay.model.Beer
import kotlinx.android.synthetic.main.item_beer_list.view.*
import kotlinx.android.synthetic.main.text_under_line.view.*
import java.lang.StringBuilder
import io.reactivex.subjects.PublishSubject




private val diffCallback = object : DiffUtil.ItemCallback<BeerListItem>() {

    override fun areItemsTheSame(oldItem: BeerListItem, newItem: BeerListItem): Boolean
            = oldItem.beer.id == newItem.beer.id

    override fun areContentsTheSame(oldItem: BeerListItem, newItem: BeerListItem): Boolean
            = oldItem == newItem   //NOTE Beer 객채의 생성자 내 프로퍼티 간에 비교로 같음을 판단 한다.
}



class BeerListAdapter : ListAdapter<BeerListItem, ListViewHolder>(diffCallback) {

    val onClickSubject = PublishSubject.create<Beer>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {

        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_beer_list, parent, false)
        // set the view's size, margins, paddings and layout parameters

        return when(viewType){
            0 ->  BeerListViewHolder(view).init(parent.context)
            1 -> BeerListViewHolder2(view).init(parent.context)
            2 -> BeerListViewHolder3(view).init(parent.context)
            else -> BeerListViewHolder(view).init(parent.context)}
        }

    override fun getItemViewType(position: Int): Int {
        return position%3
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.getview().setOnClickListener {
            onClickSubject.onNext(getItem(position).beer)
        }
        holder.bindTo(getItem(position))
    }
}


data class BeerListItem(val beer: Beer = Beer("0"), val isFiltered: Int = 0)

abstract class ListViewHolder(parentView: View) : RecyclerView.ViewHolder(parentView) {
    abstract fun init(context : Context): ListViewHolder
    abstract fun bindTo(item: BeerListItem)
    fun getview() : View = itemView

}

class BeerListViewHolder(parentView: View) : ListViewHolder(parentView) {

    private val name = itemView.name
    private val firstBrew = itemView.first
    private val tag = itemView.sec
    private val desc = itemView.third
    private val img = itemView.image
    private val frame = itemView.frame

    override fun init(context : Context): ListViewHolder {
        itemView.first_label.text.text = context.getString(R.string.first_brewed)
        itemView.sec_label.text.text = context.getString(R.string.tagline)
        itemView.third_label.text.text = context.getString(R.string.description)
        return this
    }

    override fun bindTo(item: BeerListItem) {

        Glide.with(img).load(item.beer.image_url).into(img)
        frame.setBackgroundResource(item.beer.srm.getBeerColorID())
        name.text = item.beer.name
        firstBrew.text = item.beer.first_brewed
        tag.text = item.beer.tagline
        desc.text = item.beer.description
    }
}



class BeerListViewHolder2(parentView: View) : ListViewHolder(parentView) {

    private val name = itemView.name
    private val abv = itemView.first
    private val original_gravity = itemView.sec
    private val final_gravity = itemView.third
    private val img = itemView.image
    private val frame = itemView.frame

    override fun init(context : Context): ListViewHolder {
        itemView.first_label.text.text = context.getString(R.string.abv)
        itemView.sec_label.text.text = context.getString(R.string.original_gravity)
        itemView.third_label.text.text = context.getString(R.string.final_gravity)
        return this
    }

    override fun bindTo(item: BeerListItem) {
        Glide.with(img).load(item.beer.image_url).into(img)
        frame.setBackgroundResource(item.beer.srm.getBeerColorID())
        name.text = item.beer.name
        abv.text = item.beer.ABV.toString()
        original_gravity.text = item.beer.target_original_gravity.toString()
        final_gravity.text = item.beer.target_final_gravity.toString()
    }
}

class BeerListViewHolder3(parentView: View) : ListViewHolder(parentView) {

    private val name = itemView.name
    private val desc = itemView.first
    private val food_pairing = itemView.sec
    private val brewers_tip = itemView.third

    override fun init(context : Context): ListViewHolder {
        itemView.image.visibility = View.GONE  // 이미지를 사용하지 않음.
        itemView.first_label.text.text = context.getString(R.string.description)
        itemView.sec_label.text.text = context.getString(R.string.food_pairing)
        itemView.third_label.text.text = context.getString(R.string.brewers_tips)
        return this
    }

    override fun bindTo(item: BeerListItem) {
        name.text = item.beer.name
        desc.text = item.beer.description
        val sb = StringBuilder()
        for (pairing in item.beer.food_pairing) {
            sb.append(" - " )
            sb.append(pairing)
            sb.append("\n")
        }
        sb.append("\n")
        food_pairing.text = sb.toString().replace("\n\n","")
        brewers_tip.text = item.beer.brewers_tips
    }
}