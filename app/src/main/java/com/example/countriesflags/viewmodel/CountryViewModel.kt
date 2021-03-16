package com.example.countriesflags.viewmodel

import androidx.lifecycle.*
import com.example.countriesflags.Outcome
import com.example.countriesflags.di.DaggerApiComponent
import com.example.countriesflags.model.CountriesService
import com.example.countriesflags.model.Country
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class CountryViewModel : ViewModel(), LifecycleObserver {

    @Inject
    lateinit var service: CountriesService

    init {
        DaggerApiComponent.create().inject(this)
    }

    //val outcome by lazy { MutableLiveData<Outcome>() }
    val countries = MutableLiveData<ArrayList<Country>>()
    val error = MutableLiveData<Boolean>()
    val loading = MutableLiveData<Boolean>()
    private val disposable = CompositeDisposable()

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun onResume() {
        fetchCountries()
    }

    fun refresh() {
        fetchCountries()
    }

    private fun fetchCountries() {
        loading.value = true
        disposable.add(
            service.getCountries()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<ArrayList<Country>>() {

                    override fun onSuccess(value: ArrayList<Country>) {
                        countries.value = value
                        error.value = false
                        loading.value = false
                    }


                    override fun onError(e: Throwable) {
                        error.value = true
                        loading.value = false
                    }
                })
        )
    }

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }
}