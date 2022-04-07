package com.example.images_gridlayout.modelView

import android.app.Application
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.images_gridlayout.repository.CatRepository

class MainActivityViewModelFactory(
    private val catRepository: CatRepository,
    private val application: Application
) : ViewModelProvider.NewInstanceFactory() {

    init {
        Log.i("shubham", "MainActivityViewModelFactory");

    }

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MainActivityViewModel(catRepository, application) as T
    }
}