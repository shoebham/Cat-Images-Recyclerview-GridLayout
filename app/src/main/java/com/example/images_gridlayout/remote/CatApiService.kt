package com.example.images_gridlayout.remote

import com.example.images_gridlayout.models.CatImage
import com.example.images_gridlayout.models.CatsCategory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

private const val BASE_URL =
    "https://api.thecatapi.com/"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()


object CatApi {
    val retrofitService: CatApiService by lazy {
        retrofit.create(CatApiService::class.java)
    }
}

/**
 * interface for sending API request
 */
interface CatApiService {
    @GET("/v1/images/search")
    suspend fun getCatsByCategory(
        @Query("limit") n: Int,
        @Query("category_ids", encoded = true) breedIds: String
    ): List<CatImage>

    @GET("/v1/categories")
    suspend fun getCategories(): List<CatsCategory>
}