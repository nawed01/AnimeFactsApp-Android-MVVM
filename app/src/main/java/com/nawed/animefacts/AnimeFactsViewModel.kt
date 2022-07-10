package com.nawed.animefacts

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nawed.animefacts.models.AnimeResponse
import com.nawed.animefacts.models.FactsResponse
import com.nawed.animefacts.repository.AnimeFactsRepository
import com.nawed.animefacts.utils.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AnimeFactsViewModel @Inject constructor(private val animeFactsRepository: AnimeFactsRepository): ViewModel() {
    // Shared view model for the fragments
    val animeResponseLiveData: LiveData<NetworkResult<AnimeResponse>>
    get() = animeFactsRepository.animeResponseLiveData

    val factResponseLiveData: LiveData<NetworkResult<FactsResponse>>
    get() = animeFactsRepository.factResponseLiveData

    fun fetchAnimeList() {
        viewModelScope.launch {
            animeFactsRepository.getAllAnimeList()
        }
    }

    fun fetchAnimeFactList(animeName:String) {
        viewModelScope.launch {
            animeFactsRepository.getAnimeFactList(animeName)
        }
    }

}