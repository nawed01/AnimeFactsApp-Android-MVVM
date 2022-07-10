package com.nawed.animefacts.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.nawed.animefacts.api.FactsAPI
import com.nawed.animefacts.models.AnimeResponse
import com.nawed.animefacts.models.FactsResponse
import com.nawed.animefacts.utils.AppUtils
import com.nawed.animefacts.utils.Constants.TAGG
import com.nawed.animefacts.utils.NetworkResult
import retrofit2.Response
import javax.inject.Inject

class AnimeFactsRepository @Inject constructor (private val factsAPI: FactsAPI){
    // it talks to remote data-source or local data-source
    // take Api reference in constructor and inject using hilt

    private val _animeResponseLiveData = MutableLiveData<NetworkResult<AnimeResponse>>() // can be modified only in this class
    val animeResponseLiveData: LiveData<NetworkResult<AnimeResponse>> // Exposed publicly but can't be modified
    get() = _animeResponseLiveData

    private val _factResponseLiveData = MutableLiveData<NetworkResult<FactsResponse>>() // can be modified only in this class
    val factResponseLiveData: LiveData<NetworkResult<FactsResponse>> // Exposed publicly but can't be modified
        get() = _factResponseLiveData

    suspend fun getAllAnimeList() {
        if (AppUtils.isNetWorkAvailable()){
            _animeResponseLiveData.postValue(NetworkResult.Loading())
            val response = factsAPI.getAllAnime()
            handleAllAnimeResponse(response)
        }
    }

    suspend fun getAnimeFactList(animeName:String) {
        if (AppUtils.isNetWorkAvailable()) {
            _factResponseLiveData.postValue(NetworkResult.Loading())
            val response = factsAPI.getAllFactsByAnime(animeName)
            handleAnimeFactResponse(response)
        }
    }

    private fun handleAnimeFactResponse(response: Response<FactsResponse>) {
        if (response.isSuccessful && response.body() != null) {
            _factResponseLiveData.postValue(NetworkResult.Success(response.body()!!))
        } else if (response.errorBody() != null) {
            Log.i(TAGG, "handleAnimeFactResponse: ${response.errorBody()!!.byteStream().read()}")
            _factResponseLiveData.postValue(NetworkResult.Error("Something went wrong"))
        } else {
            _factResponseLiveData.postValue(NetworkResult.Error("Something went wrong"))
        }
    }

    private fun handleAllAnimeResponse(response: Response<AnimeResponse>) {
        if (response.isSuccessful && response.body() != null) {
            _animeResponseLiveData.postValue(NetworkResult.Success(response.body()!!))
        } else if (response.errorBody() != null) {
            _animeResponseLiveData.postValue(NetworkResult.Error("Something went wrong"))
        } else {
            _animeResponseLiveData.postValue(NetworkResult.Error("Something went wrong"))
        }
    }
}