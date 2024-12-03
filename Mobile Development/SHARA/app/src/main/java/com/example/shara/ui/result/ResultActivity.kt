package com.example.shara.ui.result

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.shara.adapter.RecommendationAdapter
import com.example.shara.data.Result
import com.example.shara.data.ViewModelFactory
import com.example.shara.databinding.ActivityResultBinding

class ResultActivity : AppCompatActivity() {
    private lateinit var binding: ActivityResultBinding
    private lateinit var recommendationAdapter: RecommendationAdapter
    private val resultViewModel: ResultViewModel by viewModels{
        ViewModelFactory.getInstance(this)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        recommendationAdapter = RecommendationAdapter()
        binding.rvRekomendasi.adapter = recommendationAdapter
        binding.rvRekomendasi.layoutManager = LinearLayoutManager(this)

        getRecommendations()
        getResult()
        showLoading(false)
    }

    private fun getResult(){
        resultViewModel.skinResult().observe(this){result ->
            when(result){
                is Result.Loading -> showLoading(true)

                is Result.Success ->{
                    showLoading(false)
                    showLoading(false)
                    val skinType = result.data.history?.firstOrNull()?.skinType
                    binding.tvResult.text = skinType ?: "Skin type not found"

                }

                is Result.Error ->{
                    showLoading(false)
                }
            }
        }
    }

    private fun getRecommendations() {
        resultViewModel.getRecommendations().observe(this) { result ->
            when (result) {
                is Result.Loading -> showLoading(true)
                is Result.Success -> {
                    showLoading(false)
                    val recommendations = result.data.history?.firstOrNull()?.recommendations
                    recommendationAdapter.submitList(recommendations?.filterNotNull())
                }
                is Result.Error -> {
                    showLoading(false)
                    Toast.makeText(this, "Failed to get result: ${result.error}", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }


    private fun showLoading(isLoading: Boolean) {
        binding.pbResult.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}