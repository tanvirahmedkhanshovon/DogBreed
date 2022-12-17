package com.tanvir.dogbreedapp.breeds.repository

import androidx.annotation.WorkerThread
import com.tanvir.dogbreedapp.db.FavBreeds
import com.tanvir.dogbreedapp.db.FavItemDao
import kotlinx.coroutines.flow.Flow

class FavBreedsRepository(private val favItemDaoDao: FavItemDao) {

    val alFavItemDaos:Flow<MutableList<FavBreeds>> = favItemDaoDao.getFavBreeds()

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(favBreeds: FavBreeds){
        favItemDaoDao.insert(favBreeds)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun delete(favBreeds: FavBreeds){
        favItemDaoDao.delete(favBreeds)
    }

}