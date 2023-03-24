package com.example.andwallet.ui.screen.mainList

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.andwallet.data.MoneyItem
import com.example.andwallet.data.MoneyType

class MoneyListViewModel : ViewModel() {

    private var _moneyList = mutableStateListOf<MoneyItem>()

    var newExpenseTitle by mutableStateOf("")
    var newExpenseAmount by mutableStateOf("")
    var newExpenseIncome by mutableStateOf(false)
    var titleErrorState by mutableStateOf(false)
    var amountErrorState by mutableStateOf(false)
    var defaultErrorState by mutableStateOf(false)
    var errorText by mutableStateOf("")

    fun getAllItems(): List<MoneyItem> {
        return _moneyList
    }

    fun getExpense(): Int {
        var expense = 0
        _moneyList.forEach {
            if (it.type == MoneyType.EXPENSE) {
                expense += it.amount
            }
        }
        return expense
    }

    fun getIncome(): Int {
        var income = 0
        _moneyList.forEach {
            if (it.type == MoneyType.INCOME) {
                income += it.amount
            }
        }
        return income
    }

    fun addToMoneyList(moneyItem: MoneyItem) {
        _moneyList.add(moneyItem)
    }

    fun removeItem(moneyItem: MoneyItem) {
        _moneyList.remove(moneyItem)
    }

    fun clearAllItems() {
        _moneyList.clear()
    }
}