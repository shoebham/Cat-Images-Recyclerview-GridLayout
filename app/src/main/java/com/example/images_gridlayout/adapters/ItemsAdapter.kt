package com.example.images_gridlayout.adapters

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.images_gridlayout.databinding.ItemBinding
import com.example.images_gridlayout.databinding.ItemItemBinding
import com.example.images_gridlayout.models.CatUiModel
import java.util.concurrent.atomic.AtomicInteger

class ItemsAdapter : PagingDataAdapter<CatUiModel, RecyclerView.ViewHolder>(diffutil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        return when (viewType) {
            0 -> ItemViewHolder.from(parent)
            else -> HeaderViewHolder.from(parent)
        }
//        return ItemViewHolder(EmojiCell(parent.context).apply { inflate() })
    }

    companion object {
        private val count: AtomicInteger = AtomicInteger(0)
    }

    object diffutil : DiffUtil.ItemCallback<CatUiModel>() {
        override fun areItemsTheSame(oldItem: CatUiModel, newItem: CatUiModel): Boolean {
            return (oldItem is CatUiModel.CatItem && newItem is CatUiModel.CatItem && oldItem.catImage.url == newItem.catImage.url)
        }

        override fun areContentsTheSame(oldItem: CatUiModel, newItem: CatUiModel): Boolean {
            Log.i("shubham", "EmojiRepo");
            return oldItem == newItem
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = getItem(position)
        if (holder is ItemViewHolder) {
            holder.bind(item as CatUiModel.CatItem)
        } else if (holder is HeaderViewHolder) {
            holder.bind(item as CatUiModel.CatHeader)
        }
//        (holder.itemView as EmojiCell).bindWhenInflated {
//            bind(getItem(position)!!, holder.itemView.binding!!)
//        }
//        Log.i("items", "${ItemsAdapter.count.incrementAndGet()}")
    }

    override fun onViewRecycled(holder: RecyclerView.ViewHolder) {
//        Log.i("ItemsAdapter","onviewrecycled:${count.incrementAndGet()}")
        super.onViewRecycled(holder)
    }


    override fun getItemViewType(position: Int): Int {
//        if (items.get(0).type?.javaClass == com.example.emoji_sticker_keyboard.models.Image::class.java) return 1;
        return when (getItem(position)) {
            is CatUiModel.CatItem -> 0
            is CatUiModel.CatHeader -> 1
            null -> throw IllegalStateException("Unknown view")
        }

    }

//    class ItemViewHolder(
//        private val binding: ItemItemBinding
//    ): RecyclerView.ViewHolder(binding.root) {
//        companion object {
//            fun from(parent: ViewGroup): ItemViewHolder {
//                val layoutInflater = LayoutInflater.from(parent.context)
//                val binding = ItemItemBinding.inflate(layoutInflater, parent, false)
//                return ItemViewHolder(binding)
//            }
//        }

    class ItemViewHolder(private val binding: ItemItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        companion object {
            fun from(parent: ViewGroup): ItemViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemItemBinding.inflate(layoutInflater, parent, false)
                return ItemViewHolder(binding)
            }
        }

        fun bind(catItem: CatUiModel.CatItem) {
            Glide.with(binding.root.context).load(catItem.catImage.url).into(binding.itemImageView)
//            binding.itemImageView.load(catItem.catImage.url)
        }
    }

    class HeaderViewHolder(private val binding: ItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        companion object {
            fun from(parent: ViewGroup): HeaderViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemBinding.inflate(layoutInflater, parent, false)
                return HeaderViewHolder(binding)
            }
        }

        fun bind(catHeader: CatUiModel.CatHeader) {
            Log.i("Itemspageadapter", "text ${catHeader.text}")
            binding.textitem.text = catHeader.text
//            binding.itemImageView.isVisible=false
        }
    }


}