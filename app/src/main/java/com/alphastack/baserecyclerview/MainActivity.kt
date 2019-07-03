package com.alphastack.baserecyclerview

import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.alphastack.baserecyclerview.league.LeagueListAdapter
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var adapter: LeagueListAdapter
    private lateinit var layoutManager: LinearLayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        adapter = LeagueListAdapter()
        layoutManager = LinearLayoutManager(this)
        setUpRecyclerViewLeagueList()

        getLeagueList()
    }

    private fun setUpRecyclerViewLeagueList() {
        recyclerViewLeagueList.layoutManager = layoutManager
        recyclerViewLeagueList.adapter = adapter
    }

    private fun getLeagueList() {
        Handler().postDelayed({
            adapter.showDataNotFoundMessage("League list not found.")
        }, 2000)
    }
}
