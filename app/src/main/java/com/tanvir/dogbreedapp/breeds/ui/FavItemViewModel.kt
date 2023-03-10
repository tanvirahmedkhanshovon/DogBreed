package com.tanvir.dogbreedapp.breeds.ui

import androidx.lifecycle.*
import com.tanvir.dogbreedapp.StructureViewModel
import com.tanvir.dogbreedapp.breeds.repository.BreedListRepository
import com.tanvir.dogbreedapp.db.FavBreeds
import com.tanvir.dogbreedapp.breeds.repository.FavBreedsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FavItemViewModel(private val favBreedsRepository: FavBreedsRepository) : StructureViewModel() {
    private val repository: BreedListRepository = BreedListRepository()
    fun specificBreedImageList(breedName:String)  = liveData(Dispatchers.IO){
        isLoading.postValue(true)
        val responseBreedImage= repository.getBreedImageList(breedName)
        if(responseBreedImage.isSuccessful) {
            emit(responseBreedImage.body()!!.message)
            breedEmpty.postValue(false)
        }else{
            breedEmpty.postValue(true)
        }
    }
    val allFavBreeds : LiveData<MutableList<FavBreeds>> = favBreedsRepository.alFavItemDaos.asLiveData()
    fun updateSize() {
        viewModelScope.launch {   if(allFavBreeds.value?.isEmpty()!!) {

            breedEmpty.postValue(true)
        }
        else {

            breedEmpty.postValue(false)
        }

        }

    }

    // Launching a new coroutine to insert the data in a non-blocking way
    fun insert(favBreeds: FavBreeds) = viewModelScope.launch {
        favBreedsRepository.insert(favBreeds)
    }

    fun delete(favBreeds: FavBreeds) = viewModelScope.launch {
        favBreedsRepository.delete(favBreeds)
    }

    @Suppress("UNCHECKED_CAST")
    class FavBreedsViewModelFactory constructor(private var favBreedsRepository: FavBreedsRepository)
        :ViewModelProvider.Factory{
        override fun <Any : ViewModel> create(modelClass: Class<Any>): Any {
            if (modelClass.isAssignableFrom(FavItemViewModel::class.java)) {
                return FavItemViewModel(favBreedsRepository) as Any
            }
            throw IllegalArgumentException("Unknown VieModel Class")
        }


    }
}