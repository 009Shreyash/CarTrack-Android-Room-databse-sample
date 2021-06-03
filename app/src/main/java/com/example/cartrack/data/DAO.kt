package com.example.cartrack.data

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.cartrack.model.Items

@Dao
interface DAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(itemModel: Items?): Long

    @Update
    fun update(itemModel: Items?): Int

    @Delete
    fun delete(itemModel: Items?): Int

    @Query("SELECT * FROM items")
    fun loadAll(): LiveData<List<Items>>

    @Query("SELECT * FROM items WHERE nama LIKE '%' || :query || '%' OR vehical LIKE '%' || :query || '%' OR test_date LIKE '%' || :query || '%' OR re_test_date LIKE '%' || :query || '%' OR paid_free LIKE '%' || :query || '%' OR result LIKE '%' || :query || '%' OR unit LIKE '%' || :query || '%'")
    fun findSearchValue(query: String?): LiveData<List<Items>?>?

    @Query("SELECT * FROM items WHERE  paid_free LIKE '%' || :query")
    fun findFreeValue(query: String?): LiveData<List<Items>?>?
}