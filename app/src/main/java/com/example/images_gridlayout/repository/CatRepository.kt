package com.example.images_gridlayout.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.images_gridlayout.models.CatImage
import com.example.images_gridlayout.models.CatsCategory
import com.example.images_gridlayout.paging.PageSource
import com.example.images_gridlayout.remote.CatApi
import com.example.images_gridlayout.remote.CatApiService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

/**
 * Repository class that gets the values from api calls
 * @param catApiService Api service for sending requests
 */
class CatRepository private constructor(private val catApiService: CatApiService) {

    enum class CatApiStatus { LOADING, ERROR, DONE }

    private val _status = MutableLiveData<CatApiStatus>()
    val status = _status

    var _categories = MutableLiveData<List<CatsCategory>>()
    val categories = _categories

    /**
     * Posts the value of categories and API status to LiveData of respective things
     */
    fun getCatCategories() {
        CoroutineScope(Dispatchers.IO).launch {
            _status.postValue(CatApiStatus.LOADING)
            try {
                categories.postValue(CatApi.retrofitService.getCategories())
                status.postValue(CatApiStatus.DONE)
            } catch (e: Exception) {
                status.postValue(CatApiStatus.ERROR)
                categories.postValue(listOf())
                Log.i("shubham", "Exception $e")
            }

        }
    }

    init {
        getCatCategories()
    }

    /**
     * provides method for singleton object
     */
    companion object {
        @Volatile
        private var instance: CatRepository? = null
        fun getInstance(catApiService: CatApiService) = instance ?: synchronized(this) {
            instance ?: CatRepository(catApiService).also { instance = it }
        }
    }

    /**
     * @return a flow of Paging Data with Cat Images
     */
    fun letCatFlow(
        pagingConfig: PagingConfig = getDefaultPagingConfig(),
        categoryList: List<CatsCategory>
    ): Flow<PagingData<CatImage>> {
        return Pager(
            config = pagingConfig,
            pagingSourceFactory = { PageSource(catApiService, categoryList) }
        ).flow
    }

    fun getDefaultPagingConfig(): PagingConfig {
        return PagingConfig(pageSize = 1, enablePlaceholders = false, initialLoadSize = 3, jumpThreshold = 5)
    }

}