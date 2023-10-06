package com.project.mealsapp

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.GridLayoutManager
import com.project.mealsapp.adapter.MealsAdapter
import com.project.mealsapp.databinding.ActivityMainBinding
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private val mealsViewModel: MealsViewModel by viewModels()

    private lateinit var binding: ActivityMainBinding
    private lateinit var mealsAdapter: MealsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initUI()
    }

    private fun initUI() {


        binding.svMeal.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query != null) {
                    mealsViewModel.findMeals(query)
                }

                lifecycleScope.launch {
                    repeatOnLifecycle(Lifecycle.State.STARTED) {
                        mealsViewModel.state.collect {
                            when (it) {
                                is MealsState.Error -> {
                                    Toast.makeText(this@MainActivity, it.error, Toast.LENGTH_LONG)
                                        .show()
                                    binding.progressBar.isVisible = false
                                }

                                MealsState.Loading -> {
                                    binding.progressBar.isVisible = true
                                }

                                is MealsState.Success -> {
                                    binding.progressBar.isVisible = false
                                    mealsAdapter.updateList(it.mealsData.body()!!.info)
                                }
                            }
                        }
                    }
                }
                return false
            }

            override fun onQueryTextChange(newText: String?) = false
        })
        mealsAdapter = MealsAdapter()
        binding.rvMeals.setHasFixedSize(true)
        binding.rvMeals.layoutManager = GridLayoutManager(this, 2)
        binding.rvMeals.adapter = mealsAdapter
    }
}