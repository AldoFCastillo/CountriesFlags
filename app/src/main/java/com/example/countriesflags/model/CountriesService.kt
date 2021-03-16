package com.example.countriesflags.model

import com.example.countriesflags.di.DaggerApiComponent
import io.reactivex.Single
import javax.inject.Inject

class CountriesService {

    @Inject
    lateinit var api: CountriesApi

    init {
        DaggerApiComponent.create().inject(this)
    }


    fun getCountries(): Single<ArrayList<Country>>{
        return api.getCountries()
    }


}