package com.example.cartrack.data

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.cartrack.model.Items
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class ItemViewModel(application: Application) : AndroidViewModel(application) {
    val dao: DAO = AppDatabase.getInstance(application).dao()
    val executorService: ExecutorService = Executors.newSingleThreadExecutor()
    val allPosts: LiveData<List<Items>>
        get() = dao.loadAll()

    fun searchPost(query: String?): LiveData<List<Items>?>? {
        return dao.findSearchValue(query)
    }

    fun searchFree(query: String?): LiveData<List<Items>?>? {
        return dao.findFreeValue(query)
    }

    fun InsertItem(item: Items?) {
        executorService.execute { dao.insert(item) }
    }

    fun deleteItem(item: Items?) {
        executorService.execute { dao.delete(item) }
    }

    fun updateItem(item: Items?) {
        executorService.execute { dao.update(item) }
    }
}