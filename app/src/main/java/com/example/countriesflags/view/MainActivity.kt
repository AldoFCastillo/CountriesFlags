package com.example.countriesflags.view

import androidx.lifecycle.ViewModelProvider
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.countriesflags.Outcome
import com.example.countriesflags.R
import com.example.countriesflags.databinding.ActivityMainBinding
import com.example.countriesflags.model.Country
import com.example.countriesflags.viewmodel.CountryViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: CountryViewModel
    private var countryListAdapter = CountryListAdapter(arrayListOf())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel = ViewModelProvider(this).get(CountryViewModel::class.java)
        lifecycle.addObserver(viewModel)

        listenToObserver()

        binding.countriesRecycler.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = countryListAdapter
        }

        binding.swipeRefreshLayout.apply {
            setOnRefreshListener {
                isRefreshing = false
                viewModel.refresh()
            }
        }

    }

    /*private fun listenToObserver() {
        viewModel.outcome.observe(this, {
            when (it) {
                is Outcome.Success -> {
                    countryListAdapter.updateCountries(it.data)
                    binding.countriesRecycler.visibility = View.VISIBLE
                }
                is Outcome.Failure -> {
                    binding.error.visibility = if (it.e) View.VISIBLE else View.GONE
                }
                is Outcome.Progress -> {
                    binding.progressBar.visibility = if (it.loading) View.VISIBLE else View.GONE
                    if (it.loading){
                        binding.error.visibility = View.GONE
                        binding.countriesRecycler.visibility = View.GONE
                    }
                }
            }
        })
    }*/

    private fun listenToObserver() {
        viewModel.countries.observe(this,{
            binding.countriesRecycler.visibility = View.VISIBLE
            countryListAdapter.updateCountries(it)
        })

        viewModel.error.observe(this,{
            binding.error.visibility = if (it) View.VISIBLE else View.GONE
        })

        viewModel.loading.observe(this,{
            binding.progressBar.visibility = if (it) View.VISIBLE else View.GONE
            if (it){
                binding.error.visibility = View.GONE
                binding.countriesRecycler.visibility = View.GONE
            }
        })
    }
}