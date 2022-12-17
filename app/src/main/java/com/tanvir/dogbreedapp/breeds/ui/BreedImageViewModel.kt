package com.tanvir.dogbreedapp.breeds.ui

import androidx.lifecycle.liveData
import com.tanvir.dogbreedapp.StructureViewModel
import com.tanvir.dogbreedapp.breeds.repository.BreedListRepository
import kotlinx.coroutines.Dispatchers

class BreedImageViewModel : StructureViewModel() {
    private val repository: BreedListRepository = BreedListRepository()
    fun breedImage(breedName:String)  = liveData(Dispatchers.IO){
        isLoading.postValue(true)
        val responseBreedImage= repository.getBreedImage(breedName)
        if(responseBreedImage.isSuccessful) {
            emit(responseBreedImage.body()!!.message)
            breedEmpty.postValue(false)
        }else{
            breedEmpty.postValue(true)
        }
    }
}
