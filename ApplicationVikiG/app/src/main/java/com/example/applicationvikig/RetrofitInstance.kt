package com.example.applicationvikig

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {

    private val retrofit = Retrofit.Builder()
        .baseUrl("https://api.themoviedb.org/3/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val api: MovieApi = retrofit.create(MovieApi::class.java)
}

//https://www.themoviedb.org/
//.baseUrl("https://api.themoviedb.org/3/") this is the first link used, it is not the right one
//information from https://developer.themoviedb.org/reference/configuration-details