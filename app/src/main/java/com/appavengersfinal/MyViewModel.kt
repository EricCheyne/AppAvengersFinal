package com.appavengersfinal

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

private const val LOG_TAG = "MyViewModel"
const val INITIAL_STRING_VALUE = ""
const val INITIAL_COUNTER_VALUE = 0.0
const val INITIAL_BUDGET_VALUE = 0

class MyViewModel : ViewModel() {
    private var transaction: String = INITIAL_STRING_VALUE
    private var transAmount: Double = INITIAL_COUNTER_VALUE
    private var budget: Int = INITIAL_BUDGET_VALUE
//    private lateinit var dataD: ArrayList<Data_Transaction>


    override fun onCleared() {
        super.onCleared()
        Log.d("MainActivity", "OnCleared of CounterViewActivity called")
    }

    private val prefs = MyDataStore.getRepository()

    private fun saveArray(b: String, a: Double) {
        viewModelScope.launch {
            prefs.saveTransaction(b)
            prefs.saveTransAmount(a)
//            dataD.add(Data_Transaction(b, a))
            Log.d(LOG_TAG, "Saving array to DataStore: $b, $a")
        }
    }
    private fun saveBudget() {
        viewModelScope.launch {
            prefs.saveBudget(budget)
            Log.v(LOG_TAG, "Saved Budget: $budget")
        }
    }

    fun loadMoney() {
        GlobalScope.launch {
            prefs.budgetD.collectLatest {
                budget = it
                Log.d(LOG_TAG, "Loaded budget from DataStore: $budget")
            }
        }
    }

    fun setBudget(r: Int){
        this.budget = r
        saveBudget()
    }
    fun setArray(b: String, a: Double){
        saveArray(b,a)
    }
    fun setTransaction(r: String){
        this.transaction = r
        saveBudget()
    }
    fun setTransAmount(r: Double){
        this.transAmount = r
        saveBudget()
    }

    fun getBudget(): Int {
        return this.budget
    }
//    fun getArray(): Int {
//        return this.budget
//    }
    fun getTransaction(): String {
        return this.transaction
    }
    fun getTransAmount(): Double {
        return this.transAmount
    }

}