package ar.com.gififyapp.ui.about

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import ar.com.gififyapp.R
import ar.com.gififyapp.databinding.FragmentAboutAppBinding
import ar.com.gififyapp.presentation.GififyViewModel
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AboutAppFragment : Fragment(R.layout.fragment_about_app) {

    private lateinit var binding: FragmentAboutAppBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAboutAppBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Glide
            .with(requireContext())
            .load("https://media0.giphy.com/media/3o6gbbuLW76jkt8vIc/giphy.gif?cid=ecf05e475sxgc298ufim2cwxivn9ziihxd0wx1kdsucv0phg&rid=giphy.gif&ct=g")
            .centerCrop()
            .into(binding.ivApiLogo)
    }
}