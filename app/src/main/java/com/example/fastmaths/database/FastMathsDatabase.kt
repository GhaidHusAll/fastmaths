package com.example.fastmaths.database

import android.content.Context
import androidx.room.*


@Database(entities = [User::class],version = 1,exportSchema = false)
abstract class FastMathsDatabase: RoomDatabase() {
    abstract fun dao(): UserDao

    companion object{
        //visible to other threads
        @Volatile
        private var INSTANCE: FastMathsDatabase? = null

        fun getDatabase(context: Context): FastMathsDatabase{
            val tempInstance = INSTANCE
            if (tempInstance != null){
                return tempInstance
            }
            synchronized(this){ // protection on multi threads
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    FastMathsDatabase::class.java,
                    "user"
                ).fallbackToDestructiveMigration() // destroy old DB if ver changes
                    .build()
                INSTANCE = instance
                return instance
            }
        }
    }

}
