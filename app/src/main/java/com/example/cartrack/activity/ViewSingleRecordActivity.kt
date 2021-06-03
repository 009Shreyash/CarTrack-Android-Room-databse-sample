package com.example.cartrack.activity

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModelProviders
import com.example.cartrack.MainActivity
import com.example.cartrack.R
import com.example.cartrack.data.ItemViewModel
import com.example.cartrack.model.Items

class ViewSingleRecordActivity : AppCompatActivity() {

    lateinit var parentRelative: RelativeLayout
    lateinit var tvName: TextView
    lateinit var tvMobile: TextView
    lateinit var tvVehicalNo: TextView
    lateinit var tvTestDate: TextView
    lateinit var tvUnit: TextView
    lateinit var tvResult: TextView
    lateinit var tvReTestDate: TextView
    lateinit var tvIsPaid: TextView
    lateinit var tvFee: TextView
    private var _id: String = ""

    private var itemViewModel: ItemViewModel? = null
    var recordListData = Items()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_single_record)
        val toolbar: Toolbar = findViewById<View>(R.id.toolbar) as Toolbar
        toolbar.title = "Customer Data"
        toolbar.setTitleTextColor(resources.getColor(R.color.white))
        toolbar.setNavigationOnClickListener {
            onBackPressed()
        }
        setSupportActionBar(toolbar)

        //get Viewmodel
        itemViewModel = ViewModelProviders.of(this).get(ItemViewModel::class.java)
        init()
    }

    private fun init() {

        var bundle: Bundle
        bundle = intent.extras!!
        recordListData = (bundle.getSerializable("recordList") as Items?)!!

        parentRelative = findViewById(R.id.parentRelative)
        tvName = findViewById(R.id.tvName)
        tvMobile = findViewById(R.id.tvMobile)
        tvVehicalNo = findViewById(R.id.tvVehicalNo)
        tvTestDate = findViewById(R.id.tvTestDate)
        tvUnit = findViewById(R.id.tvUnit)
        tvResult = findViewById(R.id.tvResult)
        tvReTestDate = findViewById(R.id.tvReTestDate)
        tvIsPaid = findViewById(R.id.tvIsPaid)
        tvFee = findViewById(R.id.tvFee)


        _id = recordListData.id.toString()
        tvName.text = recordListData.name.toString()
        tvMobile.text = recordListData.mobile.toString()
        tvVehicalNo.text = recordListData.vehical_no.toString()
        tvTestDate.text = recordListData.test_date.toString()
        tvUnit.text = recordListData.unit.toString()
        tvResult.text = recordListData.result.toString()
        tvReTestDate.text = recordListData.re_test_date.toString()
        tvIsPaid.text = recordListData.paid_free.toString()
        tvFee.text = recordListData.fee.toString()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        return when (id) {
            R.id.item_edit -> {
                val intent = Intent(this, UpdateRecordActivity::class.java)
                intent.putExtra("update", recordListData)
                startActivity(intent)
                true
            }
            R.id.item_delete -> {
                itemViewModel!!.deleteItem(recordListData)
                Toast.makeText(this, "Record Deleted SuccessFully!!", Toast.LENGTH_SHORT).show()
                Handler().postDelayed({
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                }, 700)
                true
            }
            else -> super.onOptionsItemSelected(item)

        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }
}