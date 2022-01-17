package com.example.fastmaths.database

import androidx.room.*

@Entity
data class User(
    @PrimaryKey(autoGenerate = true)
    var pk:Int,
    var highScore:Int
    ,var level:Int)
