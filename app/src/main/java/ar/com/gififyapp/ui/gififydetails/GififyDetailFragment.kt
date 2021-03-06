package ar.com.gififyapp.ui.gififydetails

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import ar.com.gififyapp.R
import ar.com.gififyapp.application.Constants.IMAGE_TOOLBAR
import ar.com.gififyapp.data.model.GififyFavorite
import ar.com.gififyapp.databinding.FragmentGififyDetailBinding
import ar.com.gififyapp.databinding.ItemShowGififyBinding
import ar.com.gififyapp.presentation.GififyViewModel
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import es.dmoral.toasty.Toasty
import kotlinx.coroutines.launch
import java.util.*

@AndroidEntryPoint
class GififyDetailFragment : Fragment(R.layout.fragment_gifify_detail) {

    private lateinit var binding: FragmentGififyDetailBinding
    private lateinit var bindingItemShowGififyBinding: ItemShowGififyBinding

    private var isGififyFavorite: Boolean? = null

    private val viewModel by viewModels<GififyViewModel>()

    private val args: GififyDetailFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentGififyDetailBinding.bind(view)

        Log.d("TAGG", "source: ${args.source?.lowercase(Locale.getDefault())}")

        followUs()

        dataSource()

        contentHide()

        // Data
        binding.tvTitleGifify.text = args.title
        binding.tvUserProfileName.text = args.user?.display_name ?: "NO USER"
        binding.tvUserProfileAccount.text = "@${args.user?.username ?: "NO USER"}"
        binding.tvDescriptionGifify.text = args.user?.let {
            it.description
        }
        loadImageCircle(
            requireContext(),
            args.user?.avatar_url ?: R.drawable.ic_baseline_person_off_24,
            binding.ivUserProfile
        )
        Glide.with(requireContext()).load(args.images.original.url).centerCrop()
            .into(binding.ivGififyMain)

        fun buttonIconChanged() {
            val isGififyFavorite = isGififyFavorite ?: return
            binding.btnFavorite.setImageResource(
                when {
                    isGififyFavorite -> R.drawable.ic_baseline_delete_24
                    else -> R.drawable.ic_baseline_favorite_24
                }
            )
        }

        binding.btnFavorite.setOnClickListener {
            val isGififyFavorite = isGififyFavorite ?: return@setOnClickListener

            viewModel.saveOrDeleteFavoriteGifify(
                GififyFavorite(
                    args.type, args.id, args.url, args.username, args.source
                        ?: "", args.title, args.images, args.user
                )
            )
            this.isGififyFavorite = !isGififyFavorite
            buttonIconChanged()
        }

        viewLifecycleOwner.lifecycleScope.launch {
            isGififyFavorite = viewModel.isGififyFavorite(
                GififyFavorite(
                    args.type,
                    args.id,
                    args.url,
                    args.username,
                    args.source ?: "",
                    args.title,
                    args.images,
                    args.user
                )
            )
            buttonIconChanged()
        }
    }

    private fun loadImageCircle(context: Context, arguments: Comparable<*>, image: ImageView) {
        Glide.with(context).load(arguments).circleCrop().into(image)
    }

    private fun contentHide() {
        if (binding.tvDescriptionGifify.text == "") {
            binding.cardViewTvDescriptionGifify.visibility = View.GONE
        }
    }

    private fun dataSource() {
        if (args.source == "") {
            binding.ivSource.visibility = View.GONE
            binding.tvSourceGifify.visibility = View.GONE
        } else {
            binding.ivSource.visibility = View.VISIBLE
            binding.tvSourceGifify.visibility = View.VISIBLE
            binding.tvSourceGifify.setOnClickListener {
                args.source?.let { url ->
                    goToSite(url.lowercase(Locale.getDefault()))
                }
            }
        }
    }

    private fun followUs() {
        binding.btnInstagram.setOnClickListener {
            args.user?.let { it1 -> goToSite(it1.instagram_url) }
        }
        if (args.user?.website_url == ""){
            binding.btnWebSite.visibility = View.GONE
        } else {
            binding.btnWebSite.setOnClickListener {
                args.user?.let {
                    goToSite(it.website_url.lowercase(Locale.getDefault()))
                }
            }
        }
    }

    fun goToSite(url: String) {
        val uri: Uri = Uri.parse(url)
        val intent = Intent(Intent.ACTION_VIEW, uri)
        startActivity(intent)
    }
}