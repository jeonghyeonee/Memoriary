package com.example.memoriary.ui.todo.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.memoriary.ui.todo.model.Memo

@Dao
interface MemoDao {

    @Insert
    fun addMemo(memo: Memo)

    @Update
    fun updateMemo(memo: Memo)

    @Delete
    fun deleteMemo(memo: Memo)

    @Query("SELECT * FROM Memo ORDER BY year DESC, month DESC, day DESC, id DESC")
    fun readAllData(): List<Memo>

    @Query("SELECT * FROM Memo WHERE year = :year AND month = :month AND day = :day ORDER BY id DESC")
    fun readDateData(year: Int, month: Int, day: Int): List<Memo>

    @Query("SELECT * FROM Memo WHERE `check` = 1 ORDER BY year DESC, month DESC, day DESC, id DESC")
    fun readDoneData(): List<Memo>

    @Query("SELECT * FROM Memo WHERE content LIKE :searchQuery")
    fun searchDatabase(searchQuery: String): List<Memo>
}
