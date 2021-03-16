package com.example.countriesflags.di

import com.example.countriesflags.model.CountriesService
import com.example.countriesflags.viewmodel.CountryViewModel
import dagger.Component

@Component(modules = [ApiModule::class])
interface ApiComponent {

    fun inject(service: CountriesService)

    fun inject(viewModel: CountryViewModel)
}