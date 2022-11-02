package com.zig.gps.utils.pie

import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.zig.gps.R


open class LegendAdapter : RecyclerView.Adapter<LegendItemViewHolder>() {

    private val items = mutableListOf<Slice>()
    var onItemClickListener: ((Slice?) -> Unit)? = null

    fun setup(items: List<Slice>) {
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LegendItemViewHolder {
        return LegendItemViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_legend, parent, false)
        )
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: LegendItemViewHolder, position: Int) {
        holder.bind(items[position])

        holder.itemView.setOnClickListener {
            onItemClickListener?.invoke(items[position])
        }
    }
}

open class LegendItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    var boundItem: Slice? = null
    open fun bind(slice: Slice) {
        boundItem = slice

        val image = itemView.findViewById<View>(R.id.imageViewCircleIndicator) as ImageView
        image.imageTintList = ColorStateList.valueOf(ContextCompat.getColor(itemView.context, slice.color))

        val title = itemView.findViewById<View>(R.id.textViewSliceTitle) as TextView
        title.text = slice.name
       /* title.setOnClickListener {
            findNavController(it).navigate(R.id.action_FirstFragment_to_SecondFragment)
        }*/


    }
}


/*
class ComponentsAdapterViewHolder(val bindingDesign: ItemLegendBinding) : RecyclerView.ViewHolder(bindingDesign.root)

class ComponentsAdapter() : RecyclerView.Adapter<ComponentsAdapterViewHolder>() {

    private val itemDifferCallback = object: DiffUtil.ItemCallback<Slice>(){
        override fun areItemsTheSame(oldItem: Slice, newItem: Slice): Boolean {
            return oldItem.name == newItem.name
        }

        override fun areContentsTheSame(oldItem: Slice, newItem: Slice): Boolean {
            return oldItem == newItem
        }

    }
    // to do job asynchronously
    val itemDiffer = AsyncListDiffer(this, itemDifferCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ComponentsAdapterViewHolder {
        return ComponentsAdapterViewHolder(
            ItemLegendBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: ComponentsAdapterViewHolder, position: Int) {
        val currentItem = itemDiffer.currentList[position]
        with(holder) {
            Log.d("s1001","::: onBindViewHolder")
            bindingDesign.apply {


            }
        }
    }

    override fun getItemCount(): Int = itemDiffer.currentList.size
}
*/
