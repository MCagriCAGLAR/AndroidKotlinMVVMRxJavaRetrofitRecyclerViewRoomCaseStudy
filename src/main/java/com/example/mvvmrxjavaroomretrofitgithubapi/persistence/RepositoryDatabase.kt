package com.example.mvvmrxjavaroomretrofitgithubapi.persistence

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = arrayOf(Repository::class), version = 1)
abstract class RepositoryDatabase: RoomDatabase(){

    abstract fun getRepositoryDao(): RepositoryDAO

    companion object{
        fun create(context: Context): RepositoryDatabase{
            val databaseBuilder = Room.databaseBuilder(context, RepositoryDatabase::class.java, "repositorydatabase")
            return databaseBuilder
                .fallbackToDestructiveMigration()
                .build()
        }
    }

}