package com.example.images_gridlayout.modelView

import android.app.Application
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.images_gridlayout.repository.CatRepository

/**
 * Class to provide View Model with multiple arguments
 */
class MainActivityViewModelFactory(
    private val catRepository: CatRepository,
    private val application: Application
) : ViewModelProvider.NewInstanceFactory() {

    /**
     * creates a view model factory to provide access to application context
     *
     */
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MainActivityViewModel(catRepository, application) as T
    }
}