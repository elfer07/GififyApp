package ar.com.gififyapp.ui.gififyfavorite

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import ar.com.gififyapp.R
import ar.com.gififyapp.core.Result
import ar.com.gififyapp.data.local.LocalDatabase
import ar.com.gififyapp.data.local.LocalGififyDataSource
import ar.com.gififyapp.data.model.GififyFavorite
import ar.com.gififyapp.data.remote.RemoteGififyDataSource
import ar.com.gififyapp.databinding.FragmentGififyFavoriteBinding
import ar.com.gififyapp.presentation.GififyViewModel
import ar.com.gififyapp.presentation.GififyViewModelFactory
import ar.com.gififyapp.repository.GififyRepositoryImpl
import ar.com.gififyapp.repository.RetrofitClient
import ar.com.gififyapp.ui.gififyfavorite.adapter.GififyFavoriteAdapter

class GififyFavoriteFragment : Fragment(R.layout.fragment_gifify_favorite), GififyFavoriteAdapter.OnGififyFavoriteClickListener {

    private lateinit var binding: FragmentGififyFavoriteBinding

    private val viewModel by viewModels<GififyViewModel> {
        GififyViewModelFactory(
            GififyRepositoryImpl(
                RemoteGififyDataSource(RetrofitClient.webservice),
                LocalGififyDataSource(LocalDatabase.getDatabase(requireContext()).gififyDao())
            )
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentGififyFavoriteBinding.bind(view)

        binding.rvGififysFavorites.layoutManager = LinearLayoutManager(requireContext())

        viewModel.getFavoritesGifify().observe(viewLifecycleOwner, Observer {
            when(it){
                is Result.Loading -> {}
                is Result.Success -> {
                    if (it.data.isEmpty()) {
                        binding.emptyContainer.root.visibility = View.VISIBLE
                    }
                     binding.rvGififysFavorites.adapter = GififyFavoriteAdapter(it.data, this)
                }
                is Result.Failure -> {
                    Toast.makeText(requireContext(), "Error: ${it.exception.message}", Toast.LENGTH_SHORT).show()
                }
            }
        })

    }

    override fun onGififyFavoriteClick(gififyFavorite: GififyFavorite) {
        val action = GififyFavoriteFragmentDirections.actionGififyFavoriteFragmentToGififyDetailFragment(
            gififyFavorite.type,
            gififyFavorite.urlOriginal,
            gififyFavorite.usernameOriginal,
            gififyFavorite.title,
            gififyFavorite.images,
            gififyFavorite.images.original,
            gififyFavorite.user,
            gififyFavorite.source,
            gififyFavorite.id
        )
        findNavController().navigate(action)

    }
}