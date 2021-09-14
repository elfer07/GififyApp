package ar.com.gififyapp.ui.gififydetails

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import ar.com.gififyapp.R
import ar.com.gififyapp.data.local.LocalDatabase
import ar.com.gififyapp.data.local.LocalGififyDataSource
import ar.com.gififyapp.data.model.GififyFavorite
import ar.com.gififyapp.data.remote.RemoteGififyDataSource
import ar.com.gififyapp.databinding.FragmentGififyDetailBinding
import ar.com.gififyapp.presentation.GififyViewModel
import ar.com.gififyapp.presentation.GififyViewModelFactory
import ar.com.gififyapp.repository.GififyRepositoryImpl
import ar.com.gififyapp.repository.RetrofitClient
import com.bumptech.glide.Glide
import kotlinx.coroutines.launch
import java.util.*

class GififyDetailFragment : Fragment(R.layout.fragment_gifify_detail) {

    private lateinit var binding: FragmentGififyDetailBinding

    private var isGififyFavorite: Boolean? = null

    private val viewModel by viewModels<GififyViewModel> {
        GififyViewModelFactory(
            GififyRepositoryImpl(
                RemoteGififyDataSource(RetrofitClient.webservice),
            LocalGififyDataSource(LocalDatabase.getDatabase(requireContext()).gififyDao())
        )
        )
    }

    private val args: GififyDetailFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentGififyDetailBinding.bind(view)

        Log.d("TAGG", "source: ${args.source.lowercase(Locale.getDefault())}")

        followUs()

        dataSource()

        binding.tvTitleGifify.text = args.title
        binding.tvUserProfileName.text = args.user?.display_name ?: "NO USER"
        binding.tvUserProfileAccount.text = "@${args.user?.username ?: "NO USER"}"
        binding.tvDescriptionGifify.text = args.user?.let {
            it.description
        }
        Glide.with(requireContext()).load(args.user?.avatar_url ?: R.drawable.ic_baseline_person_off_24).circleCrop().into(binding.ivUserProfile)
        Glide.with(requireContext()).load(args.images.original.url).centerCrop().into(binding.ivGififyMain)

        fun buttonIconChanged(){
            val isGififyFavorite = isGififyFavorite ?: return
            binding.btnFavorite.setImageResource(
                when{
                    isGififyFavorite -> R.drawable.ic_baseline_delete_24
                    else -> R.drawable.ic_baseline_favorite_24
                }
            )
        }

        binding.btnFavorite.setOnClickListener {
            val isGififyFavorite = isGififyFavorite ?: return@setOnClickListener

            viewModel.saveOrDeleteFavoriteGifify(GififyFavorite(args.type, args.id, args.url, args.username, args.source, args.title, args.images, args.user))
            this.isGififyFavorite = !isGififyFavorite
            buttonIconChanged()
        }

        viewLifecycleOwner.lifecycleScope.launch {
            isGififyFavorite = viewModel.isGififyFavorite(GififyFavorite(args.type, args.id, args.url, args.username, args.source, args.title, args.images, args.user))
            buttonIconChanged()
        }
    }

    private fun dataSource() {
        binding.tvSourceGifify.setOnClickListener {
            goToSite(args.source)
        }
    }

    private fun followUs() {
        binding.btnInstagram.setOnClickListener {
            args.user?.let { it1 -> goToSite(it1.instagram_url) }
        }
    }

    fun goToSite(url: String){
        val uri: Uri = Uri.parse(url)
        val intent = Intent(Intent.ACTION_VIEW, uri)
        startActivity(intent)
    }
}