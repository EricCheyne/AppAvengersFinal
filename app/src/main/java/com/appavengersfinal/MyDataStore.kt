package com.example.cpsc411finalproject

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStoreFile
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map

class MyDataStore private constructor(private val dataStore: DataStore<Preferences>){
    private val STRING_KEY = stringPreferencesKey("transaction")
    private val AMOUNT_KEY = doublePreferencesKey("AMOUNT")
    private val BUDGET_KEY = intPreferencesKey("BUDGET")
    private lateinit var storedData: ArrayList<Data_Transaction>

    val transaction: Flow<String> = this.dataStore.data.map { prefs ->
        prefs[STRING_KEY] ?: INITIAL_STRING_VALUE
    }.distinctUntilChanged()
    val amountD: Flow<Double> = this.dataStore.data.map { prefs ->
        prefs[AMOUNT_KEY] ?: INITIAL_COUNTER_VALUE
    }.distinctUntilChanged() as Flow<Double>
    val budgetD: Flow<Int> = this.dataStore.data.map { prefs ->
        prefs[BUDGET_KEY] ?: INITIAL_BUDGET_VALUE
    }.distinctUntilChanged()

    private suspend fun saveStringValue(key: Preferences.Key<String>, value: String) {
        this.dataStore.edit { prefs ->
            prefs[key] = value
        }
    }
    private suspend fun saveDoubleValue(key: Preferences.Key<Double>, value: Double) {
        this.dataStore.edit { prefs ->
            prefs[key] = value
        }
    }
    private suspend fun saveIntValue(key: Preferences.Key<Int>, value: Int) {
        this.dataStore.edit { prefs ->
            prefs[key] = value
        }
    }

    suspend fun saveTransaction(value: String) {
        saveStringValue(STRING_KEY, value)
    }
    suspend fun saveTransAmount(value: Double) {
        saveDoubleValue(AMOUNT_KEY, value)
    }
    suspend fun saveBudget(value: Int) {
        saveIntValue(BUDGET_KEY, value)
    }

//    private suspend fun saveArray(key: Preferences.Key<Int>, value: Int) {
//        this.dataStore.edit { prefs ->
//            prefs[key] = value
//        }
//    }

    companion object {
        private const val PREFERENCES_DATA_FILE_NAME = "settings"
        private var INSTANCE: MyDataStore? = null
        fun initialize(context: Context) {
            if (INSTANCE == null) {
                val dataStore = PreferenceDataStoreFactory.create {
                    context.preferencesDataStoreFile(PREFERENCES_DATA_FILE_NAME)
                }
                INSTANCE = MyDataStore(dataStore)
            }
        }
        fun getRepository(): MyDataStore {
            return INSTANCE ?: throw IllegalStateException("AppPreferencesRepository not initialized yet")
        }
    }
}