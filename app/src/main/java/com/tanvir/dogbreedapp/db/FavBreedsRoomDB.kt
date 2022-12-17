package com.tanvir.dogbreedapp.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Database(entities = [FavBreeds::class], version = 1, exportSchema = false)
public abstract class FavBreedsRoomDB : RoomDatabase(){

    abstract fun favBreedsDAO() : FavItemDao

    companion object {

        @Volatile
        private var INSTANCE : FavBreedsRoomDB? = null

        fun getDatabase(context: Context, scope: CoroutineScope): FavBreedsRoomDB {
            return INSTANCE ?: synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    FavBreedsRoomDB::class.java,
                    "fav_item_database"
                ).addCallback(FavBreedsCallback(scope))
                    .build()

                INSTANCE = instance

                // return instance
                instance
            }
        }
    }

    private class FavBreedsCallback(val scope: CoroutineScope):RoomDatabase.Callback(){
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)


        }
    }
}