package com.example.cartrack.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "items")
class Items : Serializable {
    @PrimaryKey(autoGenerate = true)
    var id = 0

    @ColumnInfo(name = "nama")
    var name: String? = null

    @ColumnInfo(name = "mobile")
    var mobile: String? = null

    @ColumnInfo(name = "vehical")
    var vehical_no: String? = null

    @ColumnInfo(name = "test_date")
    var test_date: String? = null

    @ColumnInfo(name = "unit")
    var unit: String? = null

    @ColumnInfo(name = "result")
    var result: String? = null

    @ColumnInfo(name = "re_test_date")
    var re_test_date: String? = null

    @ColumnInfo(name = "paid_free")
    var paid_free: String? = null

    @ColumnInfo(name = "fee")
    var fee: String? = null
}