package com.appavengersfinal

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.lifecycle.ViewModelProvider
import com.example.appavengersfinal.R

private const val LOG_TAG = "FinalProject"
const val Transaction_KEY = "transaction"
const val TransAmount_KEY = "transAmount"
const val Budget_KEY = "budget"
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewModel.loadMoney()
//        Log.d(LOG_TAG, "What's left? ${viewModel.getBudget()}")

        val listFragment = this.supportFragmentManager.findFragmentById(R.id.List)
        if ( listFragment == null) {
            val fragment = ListFragment()
            this.supportFragmentManager
                .beginTransaction()
                .add(R.id.List, fragment)
                .commit()
        }

//        viewModel.loadTransaction()
//        viewModel.loadTransAmount()
//        viewModel.loadMoney()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        Log.d(LOG_TAG, "SaveState completed successfully")
//        outState.putString(Transaction_KEY, viewModel.getTransaction())
//        outState.putDouble(TransAmount_KEY, viewModel.getTransAmount())
        outState.putInt(Budget_KEY, viewModel.getBudget())
    }

    private val viewModel: MyViewModel by lazy {
        MyDataStore.initialize(this)
        ViewModelProvider(this)[MyViewModel::class.java]
    }
}