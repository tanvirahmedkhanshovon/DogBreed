package com.tanvir.dogbreedapp.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName="FavBreeds")
data class FavBreeds(@PrimaryKey @ColumnInfo(name = "imagePath") val imagePath: String, @ColumnInfo(name = "name") val name: String)


