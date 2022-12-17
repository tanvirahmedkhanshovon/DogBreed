package com.tanvir.dogbreedapp.db

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface FavItemDao {

    @Query("SELECT * FROM FavBreeds ORDER BY name")
    fun getFavBreeds():Flow<MutableList<FavBreeds>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(foodItem: FavBreeds)

    @Delete
    suspend fun delete(foodItem: FavBreeds)
}