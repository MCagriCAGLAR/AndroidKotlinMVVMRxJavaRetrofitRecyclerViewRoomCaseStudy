package com.example.mvvmrxjavaroomretrofitgithubapi.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.mvvmrxjavaroomretrofitgithubapi.R
import kotlinx.android.synthetic.main.fragment_repo_details.*

class RepoDetailsFragment : Fragment(){

    companion object{
        fun newInstance(): RepoDetailsFragment = RepoDetailsFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val myView = inflater.inflate(R.layout.fragment_repo_details, container, false)

        val toolbarRepoDetails = myView.findViewById<Toolbar>(R.id.toolbarRepoDetails)
        val activity = activity as AppCompatActivity
        activity.setSupportActionBar(toolbarRepoDetails)
        activity.supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        return myView
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val repoName = arguments!!.getString("repoName")
        val userName = arguments!!.getString("userName")
        val userAvatar = arguments!!.getString("userAvatar")
        val stars = arguments!!.getString("stars")
        val openIssues = arguments!!.getString("openIssues")

        Glide.with(this).load(userAvatar).into(iwUserPhoto)
        toolbarRepoDetails.title = repoName
        twUserName.text = userName
        twStars.text = "Stars: $stars"
        twOpenIssues.text = "Open Issues: $openIssues"
    }

}