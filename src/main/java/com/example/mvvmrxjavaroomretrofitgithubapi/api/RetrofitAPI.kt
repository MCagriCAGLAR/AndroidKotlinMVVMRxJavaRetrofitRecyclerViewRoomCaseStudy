package com.example.mvvmrxjavaroomretrofitgithubapi.api

import com.example.mvvmrxjavaroomretrofitgithubapi.baseURL
import com.example.mvvmrxjavaroomretrofitgithubapi.model.RepoListResponse
import io.reactivex.Single
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

interface RetrofitAPI {
    companion object{
        fun create(): RetrofitAPI{
            val retrofit = Retrofit.Builder()
                .baseUrl(baseURL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            return retrofit.create(RetrofitAPI::class.java)
        }
    }

    @GET("users/{user}/repos")
    fun getRepoList(@Path("user") user: String? = null): Single<ArrayList<RepoListResponse>>? = null

}