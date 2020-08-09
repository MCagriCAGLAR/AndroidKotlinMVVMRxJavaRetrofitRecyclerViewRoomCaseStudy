package com.example.mvvmrxjavaroomretrofitgithubapi.model

data class RepoListResponse(
    var id: Int? = null,
    var name: String? = null,
    var stargazers_count: Int? = null,
    var open_issues: Int? = null,
    var owner: OwnerDetails? = null
)