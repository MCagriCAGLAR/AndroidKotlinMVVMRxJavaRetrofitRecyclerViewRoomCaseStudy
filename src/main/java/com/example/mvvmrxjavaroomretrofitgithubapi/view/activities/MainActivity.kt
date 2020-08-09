package com.example.mvvmrxjavaroomretrofitgithubapi.view.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mvvmrxjavaroomretrofitgithubapi.R
import com.example.mvvmrxjavaroomretrofitgithubapi.model.RepoListResponse
import com.example.mvvmrxjavaroomretrofitgithubapi.persistence.RepositoryDatabase
import com.example.mvvmrxjavaroomretrofitgithubapi.view.adapters.RepoListAdapter
import com.example.mvvmrxjavaroomretrofitgithubapi.viewmodel.RepoListViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var repoListViewModel: RepoListViewModel
    private var adapter: RepoListAdapter? = null
    private var layoutManager: LinearLayoutManager? = null
    private var items: ArrayList<RepoListResponse>? = ArrayList()
    val compositeDisposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        repoListViewModel = ViewModelProviders.of(this).get(RepoListViewModel::class.java)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbarHome)

        compositeDisposable.add(
            RepositoryDatabase.create(this).getRepositoryDao().getAllRepositories()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ it ->
                    Log.d("MCC", it.toString())
                }, {
                    Log.e("MCC", "Room error: ${it.message}")
                })
        )

        rwRepo.setHasFixedSize(true)
        layoutManager = LinearLayoutManager(this)
        rwRepo.layoutManager = layoutManager
        adapter = RepoListAdapter(this, items!!)
        rwRepo.adapter = adapter
    }

    fun butonSubmitClick(view: View){
        if (etUserName.text!!.isEmpty()){
            Toast.makeText(this, "Please enter user name.", Toast.LENGTH_LONG).show()
        }
        else{
            progressBarHome.visibility = View.VISIBLE
            val userName = etUserName.text.toString()
            repoListViewModel.getReposList(userName)
            repoListViewModel.repoList.observe(this, Observer { data ->
                items!!.clear()
                items?.addAll(data)
                if (items!!.isNotEmpty()){
                    adapter?.notifyDataSetChanged()
                    progressBarHome.visibility = View.GONE
                }
                else{
                    progressBarHome.visibility = View.GONE
                    Toast.makeText(this, "Repository not found.", Toast.LENGTH_LONG).show()
                }
            })
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        when(id){
            android.R.id.home -> {
                onBackPressed()
                return true
            }
        }
        return false
    }

    override fun onRestart() {
        super.onRestart()
        adapter!!.notifyDataSetChanged()
        Log.d("MCC", "onRestart")
    }

    override fun onDestroy() {
        super.onDestroy()
        repoListViewModel.compositeDisposable.dispose()
    }

}