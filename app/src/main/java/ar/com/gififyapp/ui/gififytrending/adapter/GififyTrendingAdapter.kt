package ar.com.gififyapp.ui.gififytrending.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import ar.com.gififyapp.core.BaseViewHolder
import ar.com.gififyapp.data.model.Gifify
import ar.com.gififyapp.databinding.ItemGififyTrendingBinding
import com.bumptech.glide.Glide

/**
 * Created by Fernando Moreno on 22/9/2021.
 */
class GififyTrendingAdapter(
    private val context: Context,
    private val gififyTrendingList: List<Gifify>,
    private val itemClickListener: OnGififyTrendingClickListener
    ) : RecyclerView.Adapter<BaseViewHolder<*>>() {

    interface OnGififyTrendingClickListener {
        fun onGififyClick(gifify: Gifify, position: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<*> {
        val itemBinding = ItemGififyTrendingBinding.inflate(LayoutInflater.from(context), parent, false)
        val holder = GififyTrendingViewHolder(itemBinding)

        itemBinding.root.setOnClickListener {
            val position = holder.bindingAdapterPosition.takeIf {
                it != DiffUtil.DiffResult.NO_POSITION
            } ?: return@setOnClickListener
            itemClickListener.onGififyClick(gififyTrendingList[position], position)
        }
        return holder
    }

    override fun onBindViewHolder(holder: BaseViewHolder<*>, position: Int) {
        when(holder){
            is GififyTrendingViewHolder -> holder.bind(gififyTrendingList[position])
        }
    }

    override fun getItemCount(): Int = gififyTrendingList.size

    private inner class GififyTrendingViewHolder(val binding: ItemGififyTrendingBinding): BaseViewHolder<Gifify>(binding.root){
        override fun bind(item: Gifify) {
            Glide.with(context).load(item.images.original.url).centerCrop().into(binding.imgGififyTrending)
        }

    }
}