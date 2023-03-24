package com.example.andwallet.ui.screen.mainList

import android.content.Context
import android.text.TextUtils.isEmpty
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.andwallet.R
import com.example.andwallet.data.MoneyItem
import com.example.andwallet.data.MoneyType

@Composable
fun MoneyListScreen(
    moneyListViewModel: MoneyListViewModel = viewModel(),
    navController: NavController
) {

    val context = LocalContext.current

    Column(
        modifier = Modifier.fillMaxSize()
    ) {

        ListManagementHeader(moneyListViewModel, context, navController)

        LazyColumn {
            items(moneyListViewModel.getAllItems()) {
                MoneyCard(
                    context,
                    moneyItem = it,
                    onRemoveItem = {
                        moneyListViewModel.removeItem(it)
                    }
                )
            }
        }
    }
}

@Composable
fun ListManagementHeader(
    moneyListViewModel: MoneyListViewModel = viewModel(),
    context: Context = LocalContext.current,
    navController: NavController
) {

    fun validate() {
        moneyListViewModel.titleErrorState = isEmpty(moneyListViewModel.newExpenseTitle)
        moneyListViewModel.amountErrorState = isEmpty(moneyListViewModel.newExpenseAmount)
        moneyListViewModel.defaultErrorState = false

        if(moneyListViewModel.amountErrorState || moneyListViewModel.titleErrorState)
            moneyListViewModel.errorText = context.getString(R.string.empty_field_error_text)
    }

    Column {
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            OutlinedTextField(
                value = moneyListViewModel.newExpenseTitle,
                label = { Text(text = context.getString(R.string.label_expense_string)) },
                isError = moneyListViewModel.titleErrorState,
                onValueChange = {
                    moneyListViewModel.newExpenseTitle = it
                    validate()
                },
                modifier = Modifier
                    .weight(1f)
                    .padding(10.dp),
                singleLine = true,
                trailingIcon = {
                    if (moneyListViewModel.titleErrorState) {
                        Icon(
                            Icons.Filled.Warning, context.getString(R.string.error_desc),
                            tint = MaterialTheme.colorScheme.error
                        )
                    }
                }
            )

            OutlinedTextField(
                value = moneyListViewModel.newExpenseAmount,
                label = { Text(text = context.getString(R.string.label_amount_string)) },
                isError = moneyListViewModel.amountErrorState,
                onValueChange = {
                    moneyListViewModel.newExpenseAmount = it
                    validate()
                },
                modifier = Modifier
                    .padding(10.dp)
                    .weight(1f),
                singleLine = true,
                trailingIcon = {
                    if (moneyListViewModel.amountErrorState) {
                        Icon(
                            Icons.Filled.Warning, context.getString(R.string.error_desc),
                            tint = MaterialTheme.colorScheme.error
                        )
                    }
                }
            )
        }

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(
                checked = moneyListViewModel.newExpenseIncome,
                onCheckedChange = {
                    moneyListViewModel.newExpenseIncome = it
                }
            )
            Text(text = context.getString(R.string.income_text))
        }

        if (moneyListViewModel.amountErrorState ||
            moneyListViewModel.titleErrorState ||
            moneyListViewModel.defaultErrorState) {
            Text(
                text = moneyListViewModel.errorText,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.labelSmall,
                modifier = Modifier.padding(start = 16.dp)
            )
        }
    }
    ButtonInterfaceLayout(moneyListViewModel, context, navController)
}

@Composable
private fun ButtonInterfaceLayout(
    moneyListViewModel: MoneyListViewModel,
    context: Context,
    navController: NavController
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxWidth()
    ) {
        Button(
            onClick = {
                try {

                    if (!moneyListViewModel.amountErrorState && !moneyListViewModel.titleErrorState) {
                        moneyListViewModel.addToMoneyList(
                            MoneyItem(
                                moneyListViewModel.newExpenseTitle,
                                moneyListViewModel.newExpenseAmount.toInt(),
                                if (moneyListViewModel.newExpenseIncome) MoneyType.INCOME else MoneyType.EXPENSE
                            )
                        )
                    }
                }
                catch(e: Exception){
                    moneyListViewModel.defaultErrorState = true
                    moneyListViewModel.errorText = e.localizedMessage as String
                }
            },
            modifier = Modifier.padding(5.dp)
        ) {
            Text(text = context.getString(R.string.save_text))
        }

        Button(
            onClick = {
                moneyListViewModel.clearAllItems()
            },
            modifier = Modifier.padding(5.dp)
        ) {
            Text(text = context.getString(R.string.delete_all_text))
        }

        Button(
            onClick = {
                navController.navigate(
                    context.getString(R.string.summary_slash_string) +
                            moneyListViewModel.getExpense() +
                            context.getString(R.string.slash_string) + moneyListViewModel.getIncome()
                )
            },
            modifier = Modifier.padding(5.dp)
        ) {
            Text(text = context.getString(R.string.summary_text))
        }
    }
}

@Composable
fun MoneyCard(
    context: Context,
    moneyItem: MoneyItem,
    onRemoveItem: () -> Unit = {}
) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant,
        ),
        shape = RoundedCornerShape(20.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 10.dp
        ),
        modifier = Modifier.padding(5.dp)
    ) {
        Row(
            modifier = Modifier.padding(20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = moneyItem.type.getIcon()),
                contentDescription = context.getString(R.string.card_icon_desc_text),
                modifier = Modifier
                    .size(40.dp)
                    .padding(end = 10.dp)
            )
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = moneyItem.title
                )
                Text(
                    text = moneyItem.amount.toString() + context.getString(R.string.dollar_sign)
                )
            }
            Icon(
                imageVector = Icons.Filled.Delete,
                contentDescription = context.getString(R.string.delete_icon_desc),
                modifier = Modifier.clickable {
                    onRemoveItem()
                },
                tint = Color.Red
            )
        }
    }
}