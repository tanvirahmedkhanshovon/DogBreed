package com.tanvir.dogbreedapp

import android.app.Application
import com.tanvir.dogbreedapp.breeds.repository.FavBreedsRepository
import com.tanvir.dogbreedapp.db.FavBreedsRoomDB
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class MyApplication : Application() {

    private val applicationScope = CoroutineScope(SupervisorJob())
    private val database by lazy { FavBreedsRoomDB.getDatabase(this,applicationScope) }
    val repository by lazy { FavBreedsRepository(database.favBreedsDAO()) }

}