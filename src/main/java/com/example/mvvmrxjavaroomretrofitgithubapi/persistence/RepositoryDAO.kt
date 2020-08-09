package com.example.mvvmrxjavaroomretrofitgithubapi.persistence

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import io.reactivex.Completable
import io.reactivex.Flowable

@Dao
interface RepositoryDAO{
    @Insert
    fun addRepository(repository: Repository): Completable

    @Query("SELECT * FROM repository ORDER BY Id DESC")
    fun getAllRepositories(): Flowable<List<Repository>>

}