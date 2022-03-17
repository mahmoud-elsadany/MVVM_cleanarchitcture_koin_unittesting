package com.efg.valu.sales.view.demo

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.efg.valu.sales.R
import com.efg.valu.sales.databinding.ItemSearchBinding
import com.efg.valu.sales.model.response.demo.SearchModel

class DemoAdapter(private var data: MutableList<SearchModel>) :
    RecyclerView.Adapter<DemoAdapter.VH>() {
    class VH(var binding: ItemSearchBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val binding =
            ItemSearchBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return VH(binding)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        Glide.with(holder.binding.imgPoster.context).load(data[position].image)
            .placeholder(R.mipmap.ic_launcher)
            .into(holder.binding.imgPoster)
        holder.binding.txtBrand.text = data[position].brand
        holder.binding.txtName.text = data[position].name
        holder.binding.txtPrice.text = data[position].specialPrice
        holder.binding.txtOldPrice.text = data[position].price
        holder.binding.txtDiscount.text = "${data[position].maxSavingPercentage}"
        holder.binding.ratingBar.rating = data[position].ratingAverage ?: 0F
    }

    fun updateData(items: List<SearchModel>) {
        data.clear()
        data.addAll(items)
        notifyDataSetChanged()
    }

    override fun getItemCount() = data.size
}