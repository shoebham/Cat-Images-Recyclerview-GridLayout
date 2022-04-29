package com.example.images_gridlayout.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.constraintlayout.widget.Placeholder
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.images_gridlayout.R
import com.example.images_gridlayout.databinding.ItemItemBinding
import com.example.images_gridlayout.models.CatUiModel

class PlaceHolderAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        Log.i("shubham", "here")
        return ItemViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        (holder as ItemViewHolder).bind()
    }

    override fun getItemCount(): Int {
        return 20
    }

    override fun getItemViewType(position: Int): Int {
        return R.layout.item_item
    }

    class ItemViewHolder(private val binding: ItemItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        companion object {
            fun from(parent: ViewGroup): ItemViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemItemBinding.inflate(layoutInflater, parent, false)
                return ItemViewHolder(binding)
            }
        }

        fun bind() {
            Glide.with(binding.root.context).load(R.drawable.ic_launcher_background)
                .into(binding.itemImageView)
//            binding.itemImageView.load(catItem.catImage.url)
        }
    }

}