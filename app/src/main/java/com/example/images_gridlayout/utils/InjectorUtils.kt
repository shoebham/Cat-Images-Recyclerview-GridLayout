package com.example.images_gridlayout.utils


import android.app.Application
import com.example.images_gridlayout.modelView.MainActivityViewModelFactory
import com.example.images_gridlayout.remote.CatApi
import com.example.images_gridlayout.repository.CatRepository

/**
 * Helper object to provide viewmodel factory
 */
object InjectorUtils {
    /**
     * Provides View Model Factory which provides access to repository instance and application
     */
    fun provideMainActivityViewModelFactory(application: Application): MainActivityViewModelFactory {
        val emojiRepository = CatRepository.getInstance(CatApi.retrofitService)
        return MainActivityViewModelFactory(emojiRepository, application)
    }
}