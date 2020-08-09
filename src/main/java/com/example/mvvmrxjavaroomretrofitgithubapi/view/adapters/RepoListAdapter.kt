package com.example.mvvmrxjavaroomretrofitgithubapi.view.adapters

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.mvvmrxjavaroomretrofitgithubapi.R
import com.example.mvvmrxjavaroomretrofitgithubapi.model.RepoListResponse
import com.example.mvvmrxjavaroomretrofitgithubapi.persistence.Repository
import com.example.mvvmrxjavaroomretrofitgithubapi.persistence.RepositoryDatabase
import com.example.mvvmrxjavaroomretrofitgithubapi.view.activities.RepoDetails
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class RepoListAdapter(val context: Context, val repoList: ArrayList<RepoListResponse>): RecyclerView.Adapter<RepoListAdapter.RepoViewHolder>(){

    private val compositeDisposable = CompositeDisposable()
    private var repoIDArray: Array<String> = Array(10){ "null" }

    init {
        compositeDisposable.add(
            RepositoryDatabase.create(context).getRepositoryDao().getAllRepositories()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ it ->
                    for (i in 0 until it.size){
                        repoIDArray[i] = it[i].repoID.toString()
                    }
                }, {
                    Log.e("MCC", "Rx init error: ${it.message}")
                })
        )
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepoViewHolder {
        val myView = LayoutInflater.from(context).inflate(R.layout.recyclerview_adapter, parent, false)
        return RepoViewHolder(myView)
    }

    override fun getItemCount(): Int {
        return repoList.size
    }

    override fun onBindViewHolder(holder: RepoViewHolder, position: Int) {
        if (repoIDArray.contains(repoList[position].id.toString())){
            holder.iwStar.visibility = View.VISIBLE
        }
        holder.twRepoName.text = repoList[position].name.toString()
        holder.cwItem.setOnClickListener{
            val bundle = Bundle()
            bundle.putString("repoName", repoList[position].name.toString())
            bundle.putString("userName", repoList[position].owner?.login.toString())
            bundle.putString("userAvatar", repoList[position].owner?.avatar_url.toString())
            bundle.putString("stars", repoList[position].stargazers_count?.toString())
            bundle.putString("openIssues", repoList[position].open_issues?.toString())

            val intent = Intent(context, RepoDetails::class.java)
            intent.putExtra("repoName", repoList[position].name.toString())
            intent.putExtra("userName", repoList[position].owner?.login.toString())
            intent.putExtra("userAvatar", repoList[position].owner?.avatar_url.toString())
            intent.putExtra("stars", repoList[position].stargazers_count?.toString())
            intent.putExtra("openIssues", repoList[position].open_issues?.toString())
            intent.putExtra("repoID", repoList[position].id?.toString())
            it.context.startActivity(intent)
            /*
            val activity = it.context as AppCompatActivity?
            val fragment = RepoDetailsFragment.newInstance()
            fragment.arguments = bundle
            activity!!.supportFragmentManager.beginTransaction()
                .setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out)
                .replace(R.id.mainContainer, fragment)
                .addToBackStack(null)
                .commit()

             */
        }
    }

    class RepoViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val twRepoName = itemView.findViewById<TextView>(R.id.twRepoName)
        val cwItem = itemView.findViewById<CardView>(R.id.cwItem)
        val iwStar = itemView.findViewById<ImageView>(R.id.iwStar)
    }

}