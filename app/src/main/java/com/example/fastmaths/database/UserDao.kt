package com.example.fastmaths.database

import androidx.room.*


@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun add(user: User)

    @Query("SELECT * FROM user ")
    fun get(): List<User>

    @Update
    suspend fun update(user:User)


}