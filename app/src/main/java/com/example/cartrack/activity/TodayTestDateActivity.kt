package com.example.cartrack.activity

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Switch
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SwitchCompat
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.RecyclerView
import com.example.cartrack.R
import com.example.cartrack.adapter.AdapterSearchList
import com.example.cartrack.adapter.AdapterTodaySearchList
import com.example.cartrack.data.ItemViewModel
import com.example.cartrack.model.Items
import net.ozaydin.serkan.easy_csv.EasyCsv
import net.ozaydin.serkan.easy_csv.FileCallback
import java.io.File
import java.text.SimpleDateFormat
import java.util.*


class TodayTestDateActivity : AppCompatActivity(), AdapterSearchList.OnItemClick,
    AdapterTodaySearchList.OnItemClick {
    val WRITE_PERMISSON_REQUEST_CODE = 1
    var formatter: SimpleDateFormat? = null
    var date: Date? = null
    var dataList: MutableList<String> = ArrayList()
    var textString: String? = null
    var simpleSwitch: SwitchCompat? = null

    var itemViewModel: ItemViewModel? = null
    lateinit var rvTodayRecordList: RecyclerView
    lateinit var tv_empty: TextView
    lateinit var btnCreate: TextView
    lateinit var tvDate: TextView
    var adapterRecordList: AdapterTodaySearchList? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_today_test_date)
        val toolbar: Toolbar = findViewById<View>(R.id.toolbar) as Toolbar
        toolbar.title = "Today's Customer Test Date"
        toolbar.setTitleTextColor(resources.getColor(R.color.white))
        setSupportActionBar(toolbar)

        itemViewModel = ViewModelProviders.of(this).get(ItemViewModel::class.java)
        init()
    }

    private fun init() {

        tv_empty = findViewById(R.id.tv_empty)
        btnCreate = findViewById(R.id.btnCreate)
        rvTodayRecordList = findViewById(R.id.rvTodayRecordList)

        formatter = SimpleDateFormat("dd/MM/yyyy")
        date = Date()

        val easyCsv = EasyCsv(this)
        val headerList: MutableList<String> = ArrayList()
        headerList.add("Name#Mobile#Vehical Number#Test Date#TWO/FOUR#FAIL/PASS#Re Test Date#PAID/FREE#Fee-")

        tvDate = findViewById(R.id.tvDate)
        tvDate.setText(formatter!!.format(date))

        simpleSwitch = findViewById(R.id.simpleSwitch)


        // its for Today's data
        itemViewModel!!.searchPost(formatter!!.format(date))!!.observe(this,
            androidx.lifecycle.Observer { items ->
                if (items!!.isNotEmpty()) {

                    itemSearch(formatter!!.format(date))

                    simpleSwitch!!.setOnCheckedChangeListener { compoundButton, b ->
                        if (b){
                            itemSearch("Free")
                        }else{
                            itemSearch("Paid")
                        }
                    }



                } else {
                    tv_empty.visibility = View.VISIBLE
                    rvTodayRecordList.visibility = View.GONE
                }

            })



        easyCsv.setSeparatorColumn("#")
        easyCsv.setSeperatorLine("-")

        val fileName = "TrackReport"


        btnCreate.setOnClickListener {

            easyCsv.createCsvFile(
                fileName,
                headerList,
                dataList,
                WRITE_PERMISSON_REQUEST_CODE,
                object : FileCallback {
                    override fun onSuccess(file: File) {
                        Toast.makeText(
                            this@TodayTestDateActivity,
                            "Saved file in Download Folder",
                            Toast.LENGTH_SHORT
                        )
                            .show()
                    }

                    override fun onFail(err: String) {
                        Toast.makeText(this@TodayTestDateActivity, err, Toast.LENGTH_SHORT).show()
                    }
                })
        }

    }

    private fun itemSearch(s: String) {
        // its for Today's data seprate in free and paid
        itemViewModel!!.searchPost(s)!!.observe(this,
            androidx.lifecycle.Observer { items ->
                if (items!!.isNotEmpty()) {

                    for (i in items.indices) {
                        dataList.add(
                            "${items[i].name}#${items[i].mobile}#${items[i].vehical_no}#${items[i].test_date}#${items[i].unit}#${items[i].result}#${items[i].re_test_date}#${items[i].paid_free}#${items[i].fee}-"
                        )
                    }

                    adapterRecordList = AdapterTodaySearchList(items, this)
                    rvTodayRecordList.adapter = adapterRecordList
                    adapterRecordList!!.notifyDataSetChanged()
                    adapterRecordList?.itemClick = this
                    rvTodayRecordList.visibility = View.VISIBLE
                    tv_empty.visibility = View.GONE
                } else {
                    tv_empty.visibility = View.VISIBLE
                    rvTodayRecordList.visibility = View.GONE
                }

            })
    }

//
//    override fun onCreateOptionsMenu(menu: Menu): Boolean {
//        menuInflater.inflate(R.menu.menu_find_free, menu)
//        return true
//    }
//
//
//    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        val id = item.itemId
//        return when (id) {
//            R.id.item_paid -> {
//                itemSearch("Paid")
//                true
//            }
//            R.id.item_free -> {
//                itemSearch("Free")
//                true
//            }
//            R.id.item_all -> {
//                itemSearch(formatter!!.format(date))
//                true
//            }
//            else -> super.onOptionsItemSelected(item)
//
//        }
//    }


    override fun onItemClick(arrList: Items) {
        val receiver_number = arrList.mobile

        val whatsappIntent = Intent(Intent.ACTION_SEND)
        whatsappIntent.setPackage("com.whatsapp")
        whatsappIntent.type = "text/plain"
        whatsappIntent.putExtra(
            Intent.EXTRA_TEXT,
            "Hello Mr ${arrList.name} Today is your test date so please approched on time."
        )
        whatsappIntent.putExtra("jid", "$receiver_number@s.whatsapp.net")
        if (whatsappIntent.resolveActivity(packageManager) == null) {
            Toast.makeText(this, "Whatsap not installed on your phone", Toast.LENGTH_SHORT).show()
        } else {
            startActivity(whatsappIntent)
        }
    }

}