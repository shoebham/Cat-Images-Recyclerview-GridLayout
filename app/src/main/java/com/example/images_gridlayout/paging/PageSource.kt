package com.example.images_gridlayout.paging

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.images_gridlayout.models.CatImage
import com.example.images_gridlayout.models.CatsCategory
import com.example.images_gridlayout.remote.CatApiService
import java.lang.Exception

/**
 * class for paging that inherits from PagingSource
 * @param catApiService Api service for sending requests
 * @param categoryList list of Category for sending category in API calls
 */
class PageSource(
    private val catApiService: CatApiService,
    private val categoryList: List<CatsCategory>
) : PagingSource<Int, CatImage>() {

    /**
     * Loads pages and calls api on each page
     */
    override suspend fun load(params: LoadParams<Int>): PagingSource.LoadResult<Int, CatImage> {
        try {
            val page = (params.key) ?: 0
            val category = categoryList.get(page).id
            val response = catApiService.getCatsByCategory(50, category.toString())
            return LoadResult.Page(
                data = response,
                prevKey = if (page == 0) null else page - 1,
                nextKey = (page + 1)
            )
        } catch (e: Exception) {
            Log.i("Shubham", "${e}")
            return LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, CatImage>): Int? {
        return 0;
    }
}