package com.example.mvvmrxjavaroomretrofitgithubapi.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mvvmrxjavaroomretrofitgithubapi.api.RetrofitAPI
import com.example.mvvmrxjavaroomretrofitgithubapi.model.RepoListResponse
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class RepoListViewModel : ViewModel(){

    var repoList: MutableLiveData<ArrayList<RepoListResponse>> = MutableLiveData()
    var compositeDisposable = CompositeDisposable()
    var retrofitApi = RetrofitAPI.create()

    fun getReposList(userName: String? = null){
        compositeDisposable.add(retrofitApi.getRepoList(userName)
            ?.subscribeOn(Schedulers.io())
            ?.observeOn(AndroidSchedulers.mainThread())
            !!.subscribe({ data ->
                repoList.value = data
                Log.d("MCC", "ViewModel livedata worked.")
            }, {
                Log.e("MCC", "ViewModel Error: ${it.message}")
            }))
    }

}