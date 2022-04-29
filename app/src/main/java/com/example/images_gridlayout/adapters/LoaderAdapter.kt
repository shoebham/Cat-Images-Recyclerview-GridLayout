package com.example.images_gridlayout.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ProgressBar
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.images_gridlayout.R
import com.example.images_gridlayout.databinding.LoadingBinding


/**
 * Loader Adapter for loading state at the bottom of pagination
 */
class LoaderAdapter(
    private val retry: () -> Unit
) : LoadStateAdapter<LoaderAdapter.LoadStateViewHolder>() {


    override fun onBindViewHolder(
        holder: LoadStateViewHolder,
        loadState: LoadState
    ) {
        holder.bind(loadState)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        loadState: LoadState
    ): LoadStateViewHolder {
        itemCount

        return LoadStateViewHolder(parent, retry)
    }


    class LoadStateViewHolder(
        parent: ViewGroup,
        retry: () -> Unit
    ) : RecyclerView.ViewHolder(
        LayoutInflater.from(parent.context)
            .inflate(R.layout.loading, parent, false)
    ) {
        private val binding = LoadingBinding.bind(itemView)
        private val progressBar: ProgressBar = binding.progressbar
        fun bind(loadState: LoadState) {
            Log.i("loaderadapter", " ${loadState}")
            progressBar.isVisible = loadState is LoadState.Loading
        }
    }
}