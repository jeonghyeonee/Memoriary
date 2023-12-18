package com.example.memoriary.ui.todo.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import com.example.memoriary.ui.todo.model.Memo
import com.example.memoriary.ui.todo.repository.MemoRepository
import kotlinx.coroutines.Dispatchers

class MemoViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: MemoRepository = MemoRepository(application)


    // LiveData를 이용하여 데이터 변경 감지
    private val _memoList: MutableLiveData<List<Memo>> = MutableLiveData()
    val memoList: LiveData<List<Memo>> get() = _memoList

    private val _readDoneData: MutableLiveData<List<Memo>> = MutableLiveData()
    val readDoneData: LiveData<List<Memo>> get() = _readDoneData

    private var memoListObservers: MutableList<() -> Unit> = mutableListOf()

    // Observer 등록 메서드
    fun addMemoListObserver(observer: () -> Unit) {
        memoListObservers.add(observer)
    }

    // Observer 제거 메서드 (옵션)
    fun removeMemoListObserver(observer: () -> Unit) {
        memoListObservers.remove(observer)
    }

    // Observer에 변경 알리는 메서드
    private fun notifyMemoListObservers() {
        Log.d("MemoViewModel", "notifyMemoListObservers: Invoked")
        for (observer in memoListObservers) {
            observer.invoke()
        }
    }




    fun readDateData(year: Int, month: Int, day: Int) {
        Thread {
            val dateData = repository.readDateData(year, month, day)

            // 데이터 갱신
            _memoList.postValue(dateData)

            // Observer에 알림
            notifyMemoListObservers()
        }.start()
    }

    // 데이터 변경 시 호출될 메서드
    private suspend fun notifyObservers() {
        _memoList.postValue(repository.readAllData())
    }

    // 데이터 변경을 감지하고자 하는 UI에서 이 메서드를 호출하여 콜백 등록
    fun addObserver(callback: () -> Unit) {
        memoList.observeForever {
            callback.invoke()
        }
    }

    // 데이터 읽기
    suspend fun readAllData() {
        notifyObservers()
    }

    fun addMemo(memo: Memo, updateCallback: (() -> Unit)? = null) {
        // 백그라운드 스레드에서 메모 추가
        Thread {
            repository.addMemo(memo)
            updateCallback?.invoke()
        }.start()
    }

    fun updateMemo(memo: Memo, updateCallback: (() -> Unit)? = null) {
        // 메모 업데이트
        Thread {
            repository.updateMemo(memo)
            updateCallback?.invoke()
        }.start()
    }

    fun deleteMemo(memo: Memo, updateCallback: (() -> Unit)? = null) {
        // 메모 삭제
        Thread {
            repository.deleteMemo(memo)
            updateCallback?.invoke()
        }.start()
    }

    fun searchDatabase(searchQuery: String): List<Memo> {
        // 데이터베이스에서 검색 결과를 가져옴
        return repository.searchDatabase(searchQuery)
    }

//    fun readDateData(year: Int, month: Int, day: Int, updateCallback: (() -> Unit)? = null) {
//        Thread {
//            val dateData = repository.readDateData(year, month, day)
//
//            // LiveData에 값을 설정
//            _memoList.postValue(dateData)
//
//            // 콜백 호출
//            updateCallback?.invoke()
//        }.start()
//    }

    fun readDoneMemos() {
        Thread {
            val doneMemos = repository.readDoneData() // repository에서 완료된 메모를 읽어옴

            // LiveData에 값을 설정
            _readDoneData.postValue(doneMemos)
        }.start()
    }

    fun getTodoListForDate(year: Int, month: Int, day: Int): LiveData<List<Memo>> {
        return liveData(Dispatchers.IO) {
            emit(repository.getTodoListForDate(year, month, day))
        }
    }
}


