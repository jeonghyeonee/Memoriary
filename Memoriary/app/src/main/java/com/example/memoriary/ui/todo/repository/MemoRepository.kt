package com.example.memoriary.ui.todo.repository

import android.app.Application
import com.example.memoriary.ui.todo.data.MemoDao
import com.example.memoriary.ui.todo.data.MemoDatabase
import com.example.memoriary.ui.todo.model.Memo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MemoRepository(application: Application) {

    private val memoDao: MemoDao

    init {
        val database = MemoDatabase.getDatabase(application)
        memoDao = database!!.memoDao()
    }

    suspend fun readAllData(): List<Memo> {
        return withContext(Dispatchers.IO) {
            memoDao.readAllData()
        }
    }

    fun readDoneData(): List<Memo> {
        return memoDao.readDoneData()
    }

    fun readDateData(year: Int, month: Int, day: Int): List<Memo> {
        return memoDao.readDateData(year, month, day)
    }


    fun addMemo(memo: Memo) {
        memoDao.addMemo(memo)
    }

    fun updateMemo(memo: Memo) {
        memoDao.updateMemo(memo)
    }

    fun deleteMemo(memo: Memo) {
        memoDao.deleteMemo(memo)
    }

    fun searchDatabase(searchQuery: String): List<Memo> {
        return memoDao.searchDatabase(searchQuery)
    }

    suspend fun getTodoListForDate(year: Int, month: Int, day: Int): List<Memo> {
        return withContext(Dispatchers.IO) {
            memoDao.getTodoListForDate(year, month, day)
        }
    }
}