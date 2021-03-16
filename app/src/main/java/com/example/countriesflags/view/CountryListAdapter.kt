package com.example.countriesflags.view

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.countriesflags.databinding.CountryItemBinding
import com.example.countriesflags.model.Country
import com.example.countriesflags.utils.getProgressDrawable
import com.example.countriesflags.utils.loadImage

class CountryListAdapter(
    private var countries: ArrayList<Country>
) : RecyclerView.Adapter<CountryListAdapter.CountryViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountryViewHolder {
        val binding = CountryItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CountryViewHolder(binding, parent.context)
    }

    override fun getItemCount() = countries.size

    override fun onBindViewHolder(holder: CountryViewHolder, position: Int) {
        holder.bind(countries[position])
    }

    fun updateCountries(newCountries: ArrayList<Country>) {
        countries.clear()
        countries.addAll(newCountries)
        notifyDataSetChanged()
    }

    class CountryViewHolder(private val binding: CountryItemBinding, context : Context) : RecyclerView.ViewHolder(binding.root) {

        private val progressDrawable = getProgressDrawable(context)

        fun bind(country: Country) {
            binding.apply {
                countryName.text = country.countryName
                capital.text = country.countryCapital
                imageView.loadImage(country.countryFlag, progressDrawable)
            }

        }
    }
}