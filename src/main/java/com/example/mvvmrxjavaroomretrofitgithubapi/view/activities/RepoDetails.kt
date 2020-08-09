package com.example.mvvmrxjavaroomretrofitgithubapi.view.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.mvvmrxjavaroomretrofitgithubapi.R
import com.example.mvvmrxjavaroomretrofitgithubapi.persistence.Repository
import com.example.mvvmrxjavaroomretrofitgithubapi.persistence.RepositoryDatabase
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_repo_details.*
import java.time.LocalDate
import kotlin.random.Random

class RepoDetails : AppCompatActivity() {

    private var compositeDisposable = CompositeDisposable()
    private var repoID: String? = null
    private var repoName: String? = null
    private var userName: String? = null
    private var userAvatar: String? = null
    private var stars: String? = null
    private var openIssues: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_repo_details)
        setSupportActionBar(toolbarRepoDetails)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        val bundle = intent.extras
        repoID = bundle?.getString("repoID")
        repoName = bundle?.getString("repoName")
        userName = bundle?.getString("userName")
        userAvatar = bundle?.getString("userAvatar")
        stars = bundle?.getString("stars")
        openIssues = bundle?.getString("openIssues")

        Glide.with(this).load(userAvatar).into(iwUserPhoto)
        toolbarRepoDetails.title = repoName
        twUserName.text = userName
        twStars.text = "Stars: $stars"
        twOpenIssues.text = "Open Issues: $openIssues"

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_repo_detail, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item!!.itemId
        when(id){
            android.R.id.home -> {
                onBackPressed()
                return true
            }
            R.id.menuFavorites -> {
                Log.d("MCC", "repoID: $repoID")
                val randomNumber = (0..100).random()
                Log.d("MCC", "random: $randomNumber")
                val repository = Repository()
                repository.Id = randomNumber
                repository.repoID = repoID
                compositeDisposable.add(
                    RepositoryDatabase.create(this).getRepositoryDao().addRepository(repository)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe{Log.e("MCC-OnComplete", "a")}
                )
                Toast.makeText(this, "This repo added to Favorites", Toast.LENGTH_LONG).show()
            }
        }
        return false
    }
}