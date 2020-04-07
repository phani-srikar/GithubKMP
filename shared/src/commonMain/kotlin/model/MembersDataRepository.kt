package com.example.model

import com.example.api.GitHubApi
import com.example.api.UpdateProblem
import com.example.presentation.DataRepository

class MembersDataRepository(private val api: GitHubApi) : DataRepository {
    override var members: List<Member>? = null
    override var onRefreshListeners: List<() -> Unit> = emptyList()

    override suspend fun update() {
        val newMembers = try {
            api.getMembers()
        } catch (cause: Throwable) {
            throw UpdateProblem()
        }

        if (newMembers != members) {
            members = newMembers
            callRefreshListeners()
        }
    }

    private fun callRefreshListeners() {
        onRefreshListeners.forEach { it() }
    }

}