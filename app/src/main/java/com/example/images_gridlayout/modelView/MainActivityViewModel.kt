package com.example.images_gridlayout.modelView

import com.example.images_gridlayout.models.CatUiModel
import com.example.images_gridlayout.models.CatsCategory
import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import androidx.paging.*
import com.example.images_gridlayout.repository.CatRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

/**
 * ViewModel class which inherits from AndroidViewModel for context support
 * @param catRepository instance of repository class to get paging data from it
 * @param application instance of the current application
 */
class MainActivityViewModel(private val catRepository: CatRepository, application: Application) :
    AndroidViewModel(application) {

    /**
     * fetches images from server and inserts seperator on new category of images
     * @param categoryList List of category of cats for getting images from a specific category
     * @return paging data containing catimages or catheader
     */
    fun fetchImage(categoryList: List<CatsCategory>): Flow<PagingData<CatUiModel>> {
        return catRepository.letCatFlow(categoryList = categoryList).map {
            it.map {
                CatUiModel.CatItem(it)
            }.insertSeparators<CatUiModel.CatItem, CatUiModel> { before, after ->
//                Log.i(
//                    "mainactivityviewmodel",
//                    "${before} ${after?.catImage?.categories?.get(0)?.name!!}"
//                )
                when {
                    before == null -> CatUiModel.CatHeader(after?.catImage?.categories?.get(0)?.name!!)
                    after ==null-> return@insertSeparators null
                    after?.catImage?.categories?.get(0)?.name != before?.catImage?.categories?.get(0)?.name -> CatUiModel.CatHeader(
                        after?.catImage?.categories?.get(0)?.name!!
                    )
                    else -> null
                }
            }
        }
    }


    fun getCategories() = catRepository.categories
    fun getStatus() = catRepository.status

}