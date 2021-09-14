package ar.com.gififyapp.ui.gifify

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.SearchView
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
import ar.com.gififyapp.data.model.Gifify
import ar.com.gififyapp.data.remote.RemoteGififyDataSource
import ar.com.gififyapp.databinding.FragmentGififyBinding
import ar.com.gififyapp.presentation.GififyViewModel
import ar.com.gififyapp.presentation.GififyViewModelFactory
import ar.com.gififyapp.repository.GififyRepositoryImpl
import ar.com.gififyapp.repository.RetrofitClient
import ar.com.gififyapp.ui.gifify.adapter.GififyAdapter

class GififyFragment : Fragment(R.layout.fragment_gifify), GififyAdapter.OnGififyClickListener {

    private lateinit var binding: FragmentGififyBinding

    private val viewModel by viewModels<GififyViewModel> {
        GififyViewModelFactory(GififyRepositoryImpl(RemoteGififyDataSource(RetrofitClient.webservice),
        LocalGififyDataSource(LocalDatabase.getDatabase(requireContext()).gififyDao())
        ))
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentGififyBinding.bind(view)

        Log.d("TAGG", "onViewCreated: ready")

        setUpSearch()

        setHasOptionsMenu(true)

        binding.rvGififys.layoutManager = LinearLayoutManager(requireContext())

        viewModel.fetchGififys.observe(viewLifecycleOwner, Observer {
            when (it) {
                is Result.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                    Log.d("TAGG", "Elements: Loading")
                }
                is Result.Success -> {
                    binding.progressBar.visibility = View.GONE
                    binding.rvGififys.adapter = GififyAdapter(
                        requireContext(),
                        it.data.toMutableList(), this
                    )
                    Log.d("TAGG", "Elements: ${it.data.size}")
                }
                is Result.Failure -> {
                    binding.progressBar.visibility = View.GONE
                    binding.emptyContainer.root.visibility = View.VISIBLE
                    binding.emptyContainer.tvEmptyContainer.text = "Error to bring data"
                    Toast.makeText(requireContext(), "ERROR: ${it.exception}", Toast.LENGTH_SHORT)
                        .show()
                    Log.d("TAGG", "ERROR: ${it.exception}")
                }
            }
        })
    }

    private fun setUpSearch() {
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                viewModel.setGif(query!!)
                return false
            }
            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })
    }

    override fun onGififyClick(gifify: Gifify, position: Int) {
        Log.d("TAGG", "onGififyClick: ${gifify.id}")
        val action = GififyFragmentDirections.actionGififyFragmentToGififyDetailFragment(
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
                findNavController().navigate(R.id.action_gififyFragment_to_gififyFavoriteFragment)
                false
            }
            else -> {
                false
            }
        }
    }
}