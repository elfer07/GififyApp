package ar.com.gififyapp.ui.gififyfavorite.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import ar.com.gififyapp.core.BaseViewHolder
import ar.com.gififyapp.data.model.GififyFavorite
import ar.com.gififyapp.databinding.ItemGififyBinding
import com.bumptech.glide.Glide

/**
 * Created by Fernando Moreno on 13/9/2021.
 */
class GififyFavoriteAdapter(
    private val gififyFavoriteList: List<GififyFavorite>,
    private val itemClickListener: OnGififyFavoriteClickListener
):RecyclerView.Adapter<BaseViewHolder<*>>() {

    interface OnGififyFavoriteClickListener{
        fun onGififyFavoriteClick(gififyFavorite: GififyFavorite)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<*> {
        val itemBinding = ItemGififyBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        val holder = GififyViewHolder(itemBinding, parent.context)

        itemBinding.root.setOnClickListener {
            val position = holder.bindingAdapterPosition.takeIf {
                it != DiffUtil.DiffResult.NO_POSITION
            } ?: return@setOnClickListener
            itemClickListener.onGififyFavoriteClick(gififyFavoriteList[position])
        }

        return holder
    }

    override fun onBindViewHolder(holder: BaseViewHolder<*>, position: Int) {
        when(holder){
            is GififyViewHolder -> holder.bind(gififyFavoriteList[position])
        }
    }

    override fun getItemCount(): Int = gififyFavoriteList.size

    private inner class GififyViewHolder(val binding: ItemGififyBinding, val context: Context): BaseViewHolder<GififyFavorite>(binding.root){
        override fun bind(item: GififyFavorite) {
            Glide.with(context).load(item.images.original.url).centerCrop().into(binding.ivGifify)
            binding.tvTitle.text = item.title
            //binding.tvDescription.text = item.user.description
        }

    }
}