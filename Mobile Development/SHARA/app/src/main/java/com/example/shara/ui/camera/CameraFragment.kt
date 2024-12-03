package com.example.shara.ui.camera

import android.os.Bundle
import android.view.LayoutInflater
import android.content.Intent
import android.net.Uri
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.shara.data.Result
import com.example.shara.data.ViewModelFactory
import com.example.shara.ui.result.ResultActivity
import com.example.shara.databinding.FragmentDashboardBinding
import com.example.shara.util.CameraUtil
import com.example.shara.util.CameraUtil.reduceFileImage
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody

class CameraFragment : Fragment() {

    private lateinit var binding: FragmentDashboardBinding
    private var currentImage: Uri? = null
    private val cameraViewModel: CameraViewModel by viewModels {
        ViewModelFactory.getInstance(requireContext())
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupUpload()

        @Suppress("DEPRECATION")
        currentImage = savedInstanceState?.getParcelable(IMG)
        showImage()
        showLoading(false)
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
            currentImage?.let { uri ->
                val image = CameraUtil.uriToFile(uri, requireContext()).reduceFileImage()
                val requestImageFile = image.asRequestBody("image/jpeg".toMediaType())
                val multipartBody = MultipartBody.Part.createFormData(
                    "image",
                    image.name,
                    requestImageFile
                )

                cameraViewModel.uploadImg(multipartBody).observe(viewLifecycleOwner){result ->
                    when(result){
                        is Result.Loading ->  showLoading(true)
                        is Result.Success ->{
                            showLoading(false)
                            val intent = Intent(requireContext(), ResultActivity::class.java)
                            startActivity(intent)
                        }
                        is Result.Error ->{
                            showLoading(false)
                            Toast.makeText(requireContext(), "Error: ${result.error}", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
                
            }
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.pbUpload.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    companion object {
        private const val IMG = "currentImage"
    }
}