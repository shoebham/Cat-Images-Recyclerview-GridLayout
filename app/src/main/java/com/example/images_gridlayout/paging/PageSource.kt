package com.example.images_gridlayout.paging

import android.util.Log
import androidx.paging.LoadType
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.images_gridlayout.models.CatImage
import com.example.images_gridlayout.models.CatsCategory
import com.example.images_gridlayout.remote.CatApiService
import java.lang.Exception
import java.util.concurrent.atomic.AtomicInteger

/**
 * class for paging that inherits from PagingSource
 * @param catApiService Api service for sending requests
 * @param categoryList list of Category for sending category in API calls
 */
class PageSource(
    private val catApiService: CatApiService,
    private val categoryList: List<CatsCategory>
) : PagingSource<Int, CatImage>() {
    companion object{
        private var count = AtomicInteger(0)
    }
    private var category=0;
    /**
     * Loads pages and calls api on each page
     */
    override suspend fun load(params: LoadParams<Int>): PagingSource.LoadResult<Int, CatImage> {
        try {
            var page = (params.key) ?: 0

            Log.i("shubham", "page:${page}")

//            if(page==0||page%3==0){
            category = categoryList.get(page / 3).id
//            }
            var response = catApiService.getCatsByCategory(20, page, category.toString())
            val loadsize = params.loadSize
            val newcount = response.size
            val total = 60 * 7
            val itemsbefore = page * 20
            val itemsafter = total - (itemsbefore + newcount)
            Log.i("shubham", "newcount:$newcount itemsbefore:$itemsbefore itemsafter:$itemsafter")
//            if(response.isEmpty())invalidate()
            return LoadResult.Page(
                data = response,
                prevKey = if (page == 0) null else page - 1,
                nextKey = page + 1,
                itemsBefore = itemsbefore,
                itemsAfter = itemsafter
            )
        } catch (e: Exception) {
            Log.i("Shubham", "${e}")
            return LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, CatImage>): Int? {
        state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            Log.i("shubham", "anchorpage${anchorPage}")
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
        Log.i("shubham", "anchor position:${state.anchorPosition}")
        return null

    }

    override val keyReuseSupported: Boolean
        get() = true
    override val jumpingSupported: Boolean
        get() = true
}

