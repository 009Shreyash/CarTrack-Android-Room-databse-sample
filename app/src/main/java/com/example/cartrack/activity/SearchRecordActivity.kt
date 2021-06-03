package com.example.cartrack.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.RecyclerView
import com.example.cartrack.R
import com.example.cartrack.adapter.AdapterSearchList
import com.example.cartrack.data.ItemViewModel
import com.example.cartrack.model.Items


class SearchRecordActivity : AppCompatActivity(), AdapterSearchList.OnItemClick {

    lateinit var editSearch: EditText
    lateinit var btnSearch: TextView
    var itemViewModel: ItemViewModel? = null
    lateinit var rvRecordList: RecyclerView
    lateinit var tv_empty: TextView
    var adapterRecordList: AdapterSearchList? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_record)
        val toolbar: Toolbar = findViewById<View>(R.id.toolbar) as Toolbar
        toolbar.title = "Find Customers Record"
        toolbar.setTitleTextColor(resources.getColor(R.color.white))
        toolbar.setNavigationOnClickListener {
            onBackPressed()
        }

        itemViewModel = ViewModelProviders.of(this).get(ItemViewModel::class.java)
        init()
    }

    private fun init() {
        editSearch = findViewById(R.id.editSearch)
        btnSearch = findViewById(R.id.btnSearch)
        tv_empty = findViewById(R.id.tv_empty)


        rvRecordList = findViewById(R.id.rvRecordList)

        btnSearch.setOnClickListener {

            itemViewModel!!.searchPost(editSearch.text.toString())!!.observe(this,
                androidx.lifecycle.Observer { items ->
                    if (items!!.isNotEmpty()) {
                        adapterRecordList = AdapterSearchList(items, this)
                        rvRecordList.adapter = adapterRecordList
                        adapterRecordList!!.notifyDataSetChanged()
                        adapterRecordList?.itemClick = this
                        rvRecordList.visibility = View.VISIBLE
                        tv_empty.visibility = View.GONE
                    } else {
                        tv_empty.visibility = View.VISIBLE
                        rvRecordList.visibility = View.GONE
                    }

                })
        }


    }

    override fun onItemClick(arrList: Items) {
        val intent = Intent(this, ViewSingleRecordActivity::class.java)
        intent.putExtra("recordList", arrList)
        startActivity(intent)
    }

}