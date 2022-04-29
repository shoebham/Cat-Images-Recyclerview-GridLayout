package com.example.images_gridlayout.adapters

import android.graphics.Color
import android.graphics.ColorFilter
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.images_gridlayout.R
import com.example.images_gridlayout.databinding.ItemBinding
import com.example.images_gridlayout.databinding.ItemItemBinding
import com.example.images_gridlayout.models.CatUiModel
import java.util.*
import java.util.concurrent.atomic.AtomicInteger
/**
 * Recyclerview Adapter for Images with pagination support
 */
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

        if (item == null) {
            (holder as ItemViewHolder).bind(null)
        } else {
            if (holder is ItemViewHolder) {
                holder.bind(item as CatUiModel.CatItem)
            } else if (holder is HeaderViewHolder) {
                holder.bind(item as CatUiModel.CatHeader)
            } else {
                Log.i("shubham", "null viewholder")
            }
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
//        Log.i("shubham"," before crash getitemcoutn#${itemCount}")
        return when (getItem(position)) {
            is CatUiModel.CatItem -> 0
            is CatUiModel.CatHeader -> 1
            null -> 0
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

        fun bind(catItem: CatUiModel.CatItem?) {
            if (catItem == null) {
                Glide.with(binding.root.context).load(R.drawable.placeholder)
                    .into(binding.itemImageView)
                val rnd = Random()
                val color = Color.argb(255, rnd.nextInt(255), rnd.nextInt(255), rnd.nextInt(255))
                binding.itemImageView.setColorFilter(color)
            } else {
                binding.itemImageView.setColorFilter(null)
                Glide.with(binding.root.context).load(catItem?.catImage?.url)
                    .placeholder(R.drawable.placeholder).into(binding.itemImageView)
            }
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