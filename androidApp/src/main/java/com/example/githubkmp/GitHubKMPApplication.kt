package com.example.githubkmp

import android.app.Application
import com.example.api.GitHubApi
import com.example.model.MembersDataRepository
import com.example.presentation.DataRepository

class GitHubKMPApplication : Application() {
    val dataRepository: DataRepository by lazy {
        MembersDataRepository(GitHubApi())
    }
}