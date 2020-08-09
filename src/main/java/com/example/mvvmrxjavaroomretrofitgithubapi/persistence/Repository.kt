package com.example.mvvmrxjavaroomretrofitgithubapi.persistence

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity
data class Repository(
    @PrimaryKey(autoGenerate = true)
    var Id: Int? = 0,
    @ColumnInfo
    var repoID: String? = null
): Serializable