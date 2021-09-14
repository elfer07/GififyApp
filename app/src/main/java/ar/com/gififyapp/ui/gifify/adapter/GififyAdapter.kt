package ar.com.gififyapp.ui.gifify.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import ar.com.gififyapp.core.BaseViewHolder
import ar.com.gififyapp.data.model.Gifify
import ar.com.gififyapp.databinding.ItemGififyBinding
import com.bumptech.glide.Glide

/**
 * Created by Fernando Moreno on 12/9/2021.
 */
class GififyAdapter(
    private val context: Context,
    private val gififyList: List<Gifify>,
    private val itemClickListener: OnGififyClickListener
): RecyclerView.Adapter<BaseViewHolder<*>>() {

    interface OnGififyClickListener {
        fun onGififyClick(gifify: Gifify, position: Int)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<*> {
        val itemBinding = ItemGififyBinding.inflate(LayoutInflater.from(context), parent, false)
        val holder = GififyViewHolder(itemBinding)

        itemBinding.root.setOnClickListener {
            val position = holder.bindingAdapterPosition.takeIf {
                it != DiffUtil.DiffResult.NO_POSITION
            } ?: return@setOnClickListener
            itemClickListener.onGififyClick(gififyList[position], position)
        }

        return holder
    }

    override fun onBindViewHolder(holder: BaseViewHolder<*>, position: Int) {
        when(holder){
            is GififyViewHolder -> holder.bind(gififyList[position])
        }
    }

    override fun getItemCount(): Int = gififyList.size

    private inner class GififyViewHolder(val binding: ItemGififyBinding): BaseViewHolder<Gifify>(binding.root){
        override fun bind(item: Gifify) {
            Glide.with(context).load(item.images.original.url).centerCrop().into(binding.ivGifify)
            binding.tvTitle.text = item.title
        }

    }
}