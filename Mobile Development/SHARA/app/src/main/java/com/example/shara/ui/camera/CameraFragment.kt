package com.example.shara.ui.camera

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.shara.databinding.FragmentDashboardBinding

class CameraFragment : Fragment() {

    private lateinit var binding: FragmentDashboardBinding

//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {


        binding = FragmentDashboardBinding.inflate(inflater, container, false)
        return binding.root
    }

}