package com.example.cartrack.activity

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.RecyclerView
import com.example.cartrack.MainActivity
import com.example.cartrack.R
import com.example.cartrack.adapter.AdapterRecordList
import com.example.cartrack.data.AppDatabase
import com.example.cartrack.data.ItemViewModel
import com.example.cartrack.model.Items


class ViewRecordActivity : AppCompatActivity(), AdapterRecordList.OnItemClick {

    lateinit var rvRecordList: RecyclerView
    lateinit var tv_empty: TextView
    var itemViewModel: ItemViewModel? = null
    var adapterRecordList: AdapterRecordList? = null


    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_record)
        val toolbar: Toolbar = findViewById<View>(R.id.toolbar) as Toolbar
        toolbar.title = "Customers Record List"
        toolbar.setTitleTextColor(resources.getColor(R.color.white))
        toolbar.setNavigationOnClickListener {
            onBackPressed()
        }


        tv_empty = findViewById(R.id.tv_empty)
        rvRecordList = findViewById(R.id.rvRecordList)

        //get Viewmodel
        itemViewModel = ViewModelProviders.of(this).get(ItemViewModel::class.java)
        itemViewModel!!.allPosts.observe(this, Observer { items ->
            if (items.isNotEmpty()) {
                adapterRecordList = AdapterRecordList(items,this)
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


    override fun onItemClick(arrList: Items) {
        val intent = Intent(this, ViewSingleRecordActivity::class.java)
        intent.putExtra("recordList", arrList)
        startActivity(intent)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

}