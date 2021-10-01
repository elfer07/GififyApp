package ar.com.gififyapp.ui.gififytrending

import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.GridLayout
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import ar.com.gififyapp.R
import ar.com.gififyapp.core.Result
import ar.com.gififyapp.data.model.Gifify
import ar.com.gififyapp.databinding.FragmentGififyTrendingBinding
import ar.com.gififyapp.presentation.GififyViewModel
import ar.com.gififyapp.ui.gififytrending.adapter.GififyTrendingAdapter
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GififyTrendingFragment : Fragment(R.layout.fragment_gifify_trending), GififyTrendingAdapter.OnGififyTrendingClickListener {

    private lateinit var binding: FragmentGififyTrendingBinding

    private val viewModel by viewModels<GififyViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentGififyTrendingBinding.bind(view)

        setHasOptionsMenu(true)

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
        val action = GififyTrendingFragmentDirections.actionGififyTrendingFragmentToGififyDetailFragment(
            gifify.type,
            gifify.url,
            gifify.username,
            gifify.title,
            gifify.images,
            gifify.images.original,
            gifify.user,
            gifify.source,
            gifify.id
        )
        findNavController().navigate(action)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.main_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId){
            R.id.favorite -> {
                findNavController().navigate(R.id.action_gififyTrendingFragment_to_gififyFavoriteFragment)
                true
            }
            R.id.search -> {
                findNavController().navigate(R.id.action_gififyTrendingFragment_to_gififyFragment)
                true
            }
            R.id.about -> {
                findNavController().navigate(R.id.action_gififyTrendingFragment_to_aboutAppFragment)
                true
            }
            else -> {
                false
            }
        }
    }
}