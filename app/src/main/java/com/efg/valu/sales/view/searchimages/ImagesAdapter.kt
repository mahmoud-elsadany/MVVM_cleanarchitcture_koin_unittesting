package com.efg.valu.sales.view.searchimages

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.efg.valu.sales.R
import com.efg.valu.sales.databinding.ImageRowBinding


class ImagesAdapter (private var data: MutableList<String?>) :
    RecyclerView.Adapter<ImagesAdapter.VH>() {
    class VH(var binding: ImageRowBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val binding =
            ImageRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return VH(binding)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        Glide.with(holder.binding.singleArtImageView.context).load(data[position].toString())
            .placeholder(R.mipmap.ic_launcher)
            .into(holder.binding.singleArtImageView)
    }

    fun updateData(items: List<String?>) {
        data.clear()
        data.addAll(items)
        notifyDataSetChanged()
    }

    override fun getItemCount() = data.size
}