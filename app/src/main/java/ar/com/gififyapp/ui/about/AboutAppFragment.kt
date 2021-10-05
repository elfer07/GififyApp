package ar.com.gififyapp.ui.about

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import ar.com.gififyapp.R
import ar.com.gififyapp.application.Constants.API_WEB
import ar.com.gififyapp.application.Constants.EMAIL
import ar.com.gififyapp.application.Constants.FLAT_ICON_URL
import ar.com.gififyapp.application.Constants.GITHUB
import ar.com.gififyapp.application.Constants.IMAGE_TOOLBAR
import ar.com.gififyapp.application.Constants.VERSION
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

        binding.tvAcerca.text = "Gifify App Ver. $VERSION"
        Glide
            .with(requireContext())
            .load(IMAGE_TOOLBAR)
            .centerCrop()
            .into(binding.ivLogo)

        binding.btnApi.setOnClickListener {
            goToSite(API_WEB)
        }
        binding.btnFlaticon.setOnClickListener {
            goToSite(FLAT_ICON_URL)
        }
        binding.btnGithub.setOnClickListener {
            goToSite(GITHUB)
        }
        binding.btnEmail.setOnClickListener {
            sendEmail(EMAIL)
        }
    }

    private fun sendEmail(email: String) {
        val emailIntent = Intent(Intent.ACTION_SEND)
        emailIntent.type = "message/rfc822"
        emailIntent.putExtra(Intent.EXTRA_EMAIL, arrayOf(email))
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Consulta")
        emailIntent.putExtra(Intent.EXTRA_TEXT, "<Escriba aquí su consulta>")
        try {
            startActivity(Intent.createChooser(emailIntent, "Enviar Correo Electrónico:"))
        } catch (e: ActivityNotFoundException){
            Toast.makeText(requireContext(), "No hay ningun cliente de Correo Electrónico instalado!", Toast.LENGTH_SHORT).show()
        }
    }

    private fun goToSite(url: String) {
        val uri: Uri = Uri.parse(url)
        val intent = Intent(Intent.ACTION_VIEW, uri)
        startActivity(intent)
    }
}