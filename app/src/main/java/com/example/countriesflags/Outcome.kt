package com.example.countriesflags

import com.example.countriesflags.model.Country

sealed class Outcome {

    data class Success(var data: ArrayList<Country>) : Outcome()
    data class Failure(val e: Boolean) : Outcome()
    data class Progress(var loading: Boolean) : Outcome()

    companion object {

        fun success(data: ArrayList<Country>): Outcome = Success(data)

        fun failure(e: Boolean): Outcome = Failure(e)

        fun loading(isLoading: Boolean): Outcome = Progress(isLoading)
    }
}