package com.example.shara.ui.camera

import android.os.Bundle
import android.view.LayoutInflater
import android.Manifest
import android.content.pm.PackageManager
import android.net.Uri
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.shara.databinding.FragmentDashboardBinding
import com.example.shara.util.CameraUtil

class CameraFragment : Fragment() {

    private lateinit var binding: FragmentDashboardBinding
    private var currentImage: Uri? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUpload()

        @Suppress("DEPRECATION")
        currentImage = savedInstanceState?.getParcelable(IMG)
        showImage()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {


        binding = FragmentDashboardBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelable(IMG, currentImage)
    }

    private fun startGallery() {
        launcherGallery.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
    }

    private val launcherCamera =
        registerForActivityResult(ActivityResultContracts.TakePicture()) { isSuccess ->
            if (isSuccess) {
                showImage()
            } else {
                currentImage = null
                Toast.makeText(activity, "No Picture Selected", Toast.LENGTH_SHORT).show()
            }
        }


    private val launcherGallery = registerForActivityResult(
        ActivityResultContracts.PickVisualMedia()
    ) { uri ->
        if (uri != null) {
            currentImage = uri
            showImage()
        } else {
            Toast.makeText(activity, "No Media Selected", Toast.LENGTH_SHORT).show()
        }
    }

    private fun showImage(){
        currentImage?.let {
            binding.imgItemPhoto.setImageURI(it)
        }
    }

    private fun startCamera(){
        currentImage = CameraUtil.getImageUri(requireContext())
        launcherCamera.launch(currentImage!!)
    }

    private fun setupUpload(){
        binding.btnGallery.setOnClickListener {
            startGallery()
        }

        binding.btnCamera.setOnClickListener {
            startCamera()
        }

        binding.btnUpload.setOnClickListener {
            Toast.makeText(activity, "Gambar berhasil diupload", Toast.LENGTH_SHORT).show()
        }
    }

    companion object {
        private const val IMG = "currentImage"
    }
}