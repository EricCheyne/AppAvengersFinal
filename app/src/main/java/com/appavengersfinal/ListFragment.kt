package com.appavengersfinal

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.appavengersfinal.R
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

private const val LOG_TAG = "ListFragment"
class ListFragment: Fragment(R.layout.list_fragment) {

    private lateinit var data: ArrayList<Data_Transaction>
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val model = ViewModelProvider(requireActivity()).get(MyViewModel::class.java)

        val recyclerView = view.findViewById<RecyclerView>(R.id.recycleView)
        recyclerView.layoutManager = LinearLayoutManager(view.context)
        val addButtonMain = view.findViewById<Button>(R.id.addButton)
        val itemLayout = view.findViewById<TextInputLayout>(R.id.itemLayout)
        val amountLayout = view.findViewById<TextInputLayout>(R.id.amountLayout)

        val itemTextInput = view.findViewById<TextInputEditText>(R.id.itemTextInput)
        val amountTextInput = view.findViewById<TextInputEditText>(R.id.amountTextInput)
        var budgetEditText = view.findViewById<EditText>(R.id.budgetEditText)

        var previousEditText = ""
        // ArrayList of class Data_Transaction

        // This loop will create view containing item and amount

        data = arrayListOf(
            Data_Transaction("Groceries", -87.56),
            Data_Transaction("Fast Food", -7.99),
            Data_Transaction("Movie Tickets", -23.12),
            Data_Transaction("Gas", -45.34)
        )

        var adapter = Transactions_Adapter(data)

        recyclerView.adapter = adapter


        budgetEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // This method is called before the text is changed.
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // This method is called whenever the text is changed.
                // You can perform your actions here, such as updating a TextView or saving the text to a database.
            }

            override fun afterTextChanged(s: Editable?) {
                // This method is called after the text is changed.

                if (s.isNullOrEmpty()) {
                    budgetEditText.setText("0")
                }

                data.clear()
                val temp = budgetEditText.text.toString()
                model.setBudget(temp.toInt())
                updateBalanceText()
            }
        })

        addButtonMain.setOnClickListener {
            val item = itemTextInput.text.toString()
            val amount = amountTextInput.text.toString().toDoubleOrNull()
            if (item.isNotEmpty() && amount != null) {
                // Create a new Data_Transaction object and add it to the data list
//                model.setArray(item, amount)
                model.setTransaction(item)
                model.setTransAmount(amount)
                data.add(Data_Transaction(model.getTransaction(), -model.getTransAmount()))

                // Update the displayed list by notifying the adapter
                adapter.notifyDataSetChanged()

                // Clear the input fields
                itemTextInput.text?.clear()
                amountTextInput.text?.clear()

                // Update the balance text
                updateBalanceText()
            }
        }
        val temp2 = model.getBudget().toString()
        budgetEditText.setText(temp2)
        updateBalanceText()
    }

    private fun updateBalanceText() {

        val model = ViewModelProvider(requireActivity()).get(MyViewModel::class.java)

        val balance = view?.findViewById<TextView>(R.id.balance)
        val budget = view?.findViewById<EditText>(R.id.budgetEditText)
        val expense = view?.findViewById<TextView>(R.id.expense)
        val budgetAmount = budget?.text.toString()
        val budgetAmountDouble = budgetAmount.toDouble()

        val expenseAmount = data.sumOf { it.amount }
        val currentAmount = budgetAmountDouble + expenseAmount

        balance?.text = "$ %.2f".format(currentAmount)

        expense?.text = "$ %.2f".format(expenseAmount)
    }

}