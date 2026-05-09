package com.example.applicationvikig

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch


class MovieViewModel : ViewModel() {

    val sections = MutableLiveData<List<MovieSection>>()

    private val API_KEY = "d8a07dedcb5bf27a66c3c3621fb2cb84"

    fun fetchMovies() {
        viewModelScope.launch {
            try {
                val popular = RetrofitInstance.api.getPopularMovies(API_KEY).results
                val topRated = RetrofitInstance.api.getTopRatedMovies(API_KEY).results
                val upcoming = RetrofitInstance.api.getUpcomingMovies(API_KEY).results

                sections.value = listOf(
                    MovieSection("Popular", popular),
                    MovieSection("Top Rated", topRated),
                    MovieSection("Upcoming",upcoming)
                )

            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}