package com.example.githubkmp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.Greeting
import com.example.api.UpdateProblem
import com.example.model.Member
import com.example.presentation.DataRepository
import com.example.presentation.MembersPresenter
import com.example.presentation.MembersView
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity(), MembersView {

    private val repository by lazy {
        (application as GitHubKMPApplication).dataRepository
    }

    private val presenter by lazy {
        MembersPresenter(this, repository)
    }

    private lateinit var adapter: MemberAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        greeting.text = Greeting().greeting()
        setupRecyclerView()
        presenter.onCreate()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.onDestroy()
    }

    override var isUpdating = false

    override fun onUpdate(members: List<Member>) {
        adapter.members = members
        runOnUiThread {
            adapter.notifyDataSetChanged()
        }
    }

    override fun showError(error: Throwable) {
        val errMessage = when (error) {
            is UpdateProblem -> getString(R.string.update_problem)
            else -> getString(R.string.unknown_error)
        }

        runOnUiThread{
            Toast.makeText(this, errMessage, Toast.LENGTH_SHORT).show()
        }
    }

    private fun setupRecyclerView() {
        membersRecyclerView.layoutManager = LinearLayoutManager(this)
        adapter = MemberAdapter(listOf())
        membersRecyclerView.adapter = adapter
    }
}
