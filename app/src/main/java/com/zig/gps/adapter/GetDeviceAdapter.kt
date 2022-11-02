package com.zig.gps.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.zig.gps.databinding.GetDeviceDesignBinding
import com.zig.gps.model.GetDeviceData
import javax.inject.Inject

class GetDeviceAdapter @Inject constructor() : RecyclerView.Adapter<GetDeviceAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: GetDeviceDesignBinding) :
        RecyclerView.ViewHolder(binding.root)

    private val diffUtil = object : DiffUtil.ItemCallback<GetDeviceData>() {
        override fun areItemsTheSame(oldItem: GetDeviceData, newItem: GetDeviceData): Boolean {

            return oldItem.vehicleNo == newItem.vehicleNo
        }

        override fun areContentsTheSame(oldItem: GetDeviceData, newItem: GetDeviceData): Boolean {
            return oldItem == newItem
        }

    }

    val differ = AsyncListDiffer(this, diffUtil)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            GetDeviceDesignBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val article = differ.currentList[position]
        holder.binding.apply {
           /* ivArticle.loadImageFromGlide(article.urlToImage)
            tvTitle.text = article.title
            tvDescription.text = article.description
            tvSource.text = article.author
            tvPublished.text = article.publishedAt*/


            name.text = article.vehicleNo
            km.text = article.OdometerValue



        }

        holder.itemView.setOnClickListener {
            setArticleClickListener?.let {
                it(article)
            }
        }

    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    private var setArticleClickListener : ((article: GetDeviceData)->Unit)? =null

    fun onArticleClicked(listener:(GetDeviceData)->Unit){
        setArticleClickListener =listener
    }
}