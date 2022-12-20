package com.tanvir.dogbreedapp.breeds.repository

import com.tanvir.dogbreedapp.service.DogsService
import com.tanvir.dogbreedapp.service.RetrofitClient

class BreedListRepository{
    private var client : DogsService = RetrofitClient.service

    suspend fun getAllBreeds()= client.getAllBreeds()
    suspend fun getBreedImageList(breedName:String) = client.getBreedImages(breedName)
}