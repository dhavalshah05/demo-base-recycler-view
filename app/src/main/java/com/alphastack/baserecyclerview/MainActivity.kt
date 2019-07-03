package com.alphastack.baserecyclerview

import android.os.Bundle
import android.os.Handler
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

        getLeagueList()
    }

    private fun setUpRecyclerViewLeagueList() {
        recyclerViewLeagueList.layoutManager = layoutManager
        recyclerViewLeagueList.adapter = adapter
    }

    private fun getLeagueList() {
        val list = mutableListOf<League>()
        val league = League()
        league.id = 1
        league.name = "First League"

        list.add(league)

        adapter.replaceData(list)

        Handler().postDelayed({

            val league2 = League()
            league2.id = 2
            league2.name = "Second League"

           adapter.addItem(league2)

            /*adapter.replaceData(list)

            Handler().postDelayed({
                adapter.removeItem(league)
            }, 3000)*/

        }, 2000)


    }


}
