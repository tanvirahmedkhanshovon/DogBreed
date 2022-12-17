package com.tanvir.dogbreedapp

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

open class StructureViewModel : ViewModel() {

    val breedEmpty = MutableLiveData<Boolean>().apply { value = false }

    val isLoading = MutableLiveData<Boolean>().apply { value = false }



}