package com.alphastack.baserecyclerview

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.alphastack.baserecyclerview.league.LeagueListAdapter
import com.alphastack.baserecyclerview.model.League
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

        val leagueList = getLeagueList()
        adapter.replaceData(leagueList)
        //adapter.showDataNotFoundMessage("Data not found.")
    }

    private fun setUpRecyclerViewLeagueList() {
        recyclerViewLeagueList.layoutManager = layoutManager
        recyclerViewLeagueList.adapter = adapter
    }

    private fun getLeagueList(): MutableList<League> {
        val leagueList = mutableListOf<League>()

        val league1 = League()
        league1.id = 1
        league1.name = "First League"

        val league2 = League()
        league2.id = 2
        league2.name = "Second League"

        val league3 = League()
        league3.id = 3
        league3.name = "Third League"

        val league4 = League()
        league4.id = 4
        league4.name = "Forth League"

        val league5 = League()
        league5.id = 5
        league5.name = "Fifth League"

        leagueList.add(league1)
        leagueList.add(league2)
        leagueList.add(league3)
        leagueList.add(league4)
        leagueList.add(league5)

        leagueList.add(league1)
        leagueList.add(league2)
        leagueList.add(league3)
        leagueList.add(league4)
        leagueList.add(league5)

        leagueList.add(league1)
        leagueList.add(league2)
        leagueList.add(league3)
        leagueList.add(league4)
        leagueList.add(league5)

        leagueList.add(league1)
        leagueList.add(league2)
        leagueList.add(league3)
        leagueList.add(league4)
        leagueList.add(league5)

        return leagueList
    }

}
