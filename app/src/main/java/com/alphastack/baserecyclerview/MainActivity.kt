package com.alphastack.baserecyclerview

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.alphastack.baserecyclerview.league.LeagueListAdapter
import com.alphastack.baserecyclerview.model.League
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val leagueListAdapter: LeagueListAdapter = LeagueListAdapter()
    private val layoutManager = LinearLayoutManager(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // RV Setup
        rvMain.layoutManager = layoutManager
        rvMain.adapter = leagueListAdapter
        leagueListAdapter.onItemClicked {
            val newItem = it.copy(name = it.name + " Modified")
            leagueListAdapter.updateItem(newItem)
        }
        leagueListAdapter.onItemLongClicked {
            leagueListAdapter.removeItem(it)
        }

        leagueListAdapter.submitList(getLeagueList())
    }

    private fun getLeagueList(): List<League> {
        val leagueList = mutableListOf<League>()
        for (i in 1..10000 step 1) {
            val league1 = League(i.toLong(), "$i League")
            leagueList.add(league1)
        }
        return leagueList
    }

}
