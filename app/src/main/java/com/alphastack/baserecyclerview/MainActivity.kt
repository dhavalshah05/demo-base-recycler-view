package com.alphastack.baserecyclerview

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.alphastack.baserecyclerview.league.LeagueListAdapter
import com.alphastack.baserecyclerview.league.LeagueListRecyclerView
import com.alphastack.baserecyclerview.model.League
import com.alphastack.superadapter.scrolllistener.LinearLayoutScrollListener
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {


    private lateinit var leagueListRecyclerView: LeagueListRecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        leagueListRecyclerView = LeagueListRecyclerView(
                LayoutInflater.from(this),
                recyclerContainer
        )
        recyclerContainer.addView(leagueListRecyclerView.getRootView())

        buttonShowLoading.setOnClickListener {
            leagueListRecyclerView.showLoading()
        }

        buttonShowData.setOnClickListener {
            val leagueList = getLeagueList()
            leagueListRecyclerView.replaceItems(leagueList)
        }

        buttonShowDataNotFound.setOnClickListener {
            //leagueListRecyclerView.showMessage("Data not found.")

            val leagueList = mutableListOf<League>()

            for (i in 1..10000 step 3) {
                val league1 = League()
                league1.id = i.toLong()
                league1.name = "$i League"

                leagueList.add(league1)
            }

            val league1 = League()
            league1.id = 120L
            league1.name = "120 League"

            leagueListRecyclerView.removeItem(league1)

            //leagueListRecyclerView.appendItemsAtStart(leagueList)
        }

    }

    private fun getLeagueList(): MutableList<League> {
        val leagueList = mutableListOf<League>()

        for (i in 1..10000 step 1) {
            val league1 = League()
            league1.id = i.toLong()
            league1.name = "$i League"

            leagueList.add(league1)
        }

        return leagueList
    }

}
