package ar.com.gififyapp.ui.gififytrending

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import ar.com.gififyapp.R
import ar.com.gififyapp.core.Result
import ar.com.gififyapp.data.local.LocalDatabase
import ar.com.gififyapp.data.local.LocalGififyDataSource
import ar.com.gififyapp.data.model.Gifify
import ar.com.gififyapp.data.remote.RemoteGififyDataSource
import ar.com.gififyapp.databinding.FragmentGififyTrendingBinding
import ar.com.gififyapp.presentation.GififyViewModel
import ar.com.gififyapp.presentation.GififyViewModelFactory
import ar.com.gififyapp.repository.GififyRepositoryImpl
import ar.com.gififyapp.repository.RetrofitClient
import ar.com.gififyapp.ui.gififytrending.adapter.GififyTrendingAdapter

class GififyTrendingFragment : Fragment(R.layout.fragment_gifify_trending), GififyTrendingAdapter.OnGififyTrendingClickListener {

    private lateinit var binding: FragmentGififyTrendingBinding

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
        binding = FragmentGififyTrendingBinding.bind(view)

        binding.rvGififysTrending.layoutManager = GridLayoutManager(requireContext(), 3)

        viewModel.fetchTrandingGifs().observe(viewLifecycleOwner, Observer {
            when(it){
                is Result.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                    Log.d("TAGG", "Elements trending: Loading")
                }
                is Result.Success -> {
                    binding.progressBar.visibility = View.GONE
                    binding.rvGififysTrending.adapter = GififyTrendingAdapter(requireContext(), it.data.data, this)
                    Log.d("TAGG", "Elements Trending: ${it.data.data.size}")
                }
                is Result.Failure -> {
                    binding.progressBar.visibility = View.GONE
                    binding.emptyContainer.root.visibility = View.VISIBLE
                    binding.emptyContainer.tvEmptyContainer.text = "Error to bring data"
                    Toast.makeText(requireContext(), "ERROR: ${it.exception}", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        })

    }

    override fun onGififyClick(gifify: Gifify, position: Int) {
        Toast.makeText(requireContext(), "click", Toast.LENGTH_SHORT).show()
    }
}