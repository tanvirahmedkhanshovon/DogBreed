package com.tanvir.dogbreedapp.breeds.ui

import androidx.lifecycle.liveData
import com.tanvir.dogbreedapp.StructureViewModel
import com.tanvir.dogbreedapp.breeds.repository.BreedListRepository
import kotlinx.coroutines.Dispatchers

class BreedListViewModel: StructureViewModel(){
    private val repository: BreedListRepository = BreedListRepository()
    val dogBreeds = liveData(Dispatchers.IO){
        isLoading.postValue(true)
        val responseBreeds= repository.getAllBreeds()
        isLoading.postValue( false)
        if(responseBreeds.isSuccessful) {
            emit(responseBreeds.body()!!.message)
            breedEmpty.postValue( false)
        }else{
            breedEmpty.postValue(true)
        }
    }

}