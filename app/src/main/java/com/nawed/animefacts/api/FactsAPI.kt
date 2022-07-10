package com.nawed.animefacts.api

import com.nawed.animefacts.models.AnimeResponse
import com.nawed.animefacts.models.FactsResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface FactsAPI {

    @GET(".") // use "." if url is same as base url
    suspend fun getAllAnime(): Response<AnimeResponse>

    @GET("{anime_name}")
    suspend fun getAllFactsByAnime(@Path("anime_name") anime_name: String): Response<FactsResponse>
}