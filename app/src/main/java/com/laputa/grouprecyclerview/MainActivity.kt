package com.laputa.grouprecyclerview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.laputa.decoration.group.GroupDecoration
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val itemAdapter = ItemAdapter(createData())
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = itemAdapter
        recyclerView.addItemDecoration(GroupDecoration(this.applicationContext))
        itemAdapter.notifyDataSetChanged()
    }


}