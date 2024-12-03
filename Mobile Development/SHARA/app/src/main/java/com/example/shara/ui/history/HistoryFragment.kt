package com.example.shara.ui.history

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.shara.data.Result
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.shara.adapter.HistoryAdapter
import com.example.shara.data.ViewModelFactory
import com.example.shara.databinding.FragmentNotificationsBinding

class HistoryFragment : Fragment() {

    private lateinit var binding: FragmentNotificationsBinding
    private lateinit var historyAdapter: HistoryAdapter
    private val historyViewModel: HistoryViewModel by viewModels {
        ViewModelFactory.getInstance(requireContext())
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        historyAdapter = HistoryAdapter()
        binding.rvHistory.apply {
            adapter = historyAdapter
            layoutManager = LinearLayoutManager(context)
        }
        observeHistories()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNotificationsBinding.inflate(inflater, container, false)
        return binding.root

    }

    private fun observeHistories() {
        historyViewModel.getHistories().observe(viewLifecycleOwner) { result ->
            when (result) {
                is Result.Loading -> {
                    showLoading(true)
                }
                is Result.Success -> {
                    showLoading(false)
                    val histories = result.data.history?.filterNotNull()
                    if (histories != null) {
                        if (histories.isNotEmpty()) {
                            historyAdapter.submitList(histories)
                        }
                    }
                }
                is Result.Error -> {
                    showLoading(false)
                    Toast.makeText(context, result.error, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.pbHistory.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

}