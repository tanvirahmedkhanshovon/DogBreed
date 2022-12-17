package com.tanvir.dogbreedapp.service

import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory


object RetrofitClient{
    val service: DogsService by lazy{
        Retrofit.Builder()
            .baseUrl(DogsService.ENDPOINT)
            .addConverterFactory(MoshiConverterFactory.create())
            .build().create(DogsService::class.java)
    }
}