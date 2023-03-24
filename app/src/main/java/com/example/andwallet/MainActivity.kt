package com.example.andwallet

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.andwallet.ui.screen.mainList.MoneyListScreen
import com.example.andwallet.ui.screen.summary.MoneySummaryScreen
import com.example.andwallet.ui.screen.pinRequest.PinRequestScreen
import com.example.andwallet.ui.theme.AndWalletTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AndWalletTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    TodoAppNavHost()
                }
            }
        }
    }

    @Composable
    fun TodoAppNavHost(
        modifier: Modifier = Modifier,
        navController: NavHostController = rememberNavController(),
        context: Context = LocalContext.current,
        startDestination: String = context.getString(R.string.login_nav_text)
    ) {

        NavHost(
            modifier = modifier,
            navController = navController,
            startDestination = startDestination
        ) {
            composable(context.getString(R.string.list_nav_route)) { MoneyListScreen(navController = navController) }
            composable(
                context.getString(R.string.summary_nav_text),
                arguments = listOf(
                    navArgument(context.getString(R.string.expense_nav_string)){type = NavType.IntType},
                    navArgument(context.getString(R.string.income_nav_string)){type = NavType.IntType}
                )
            ) {
                val expense = it.arguments?.getInt(context.getString(R.string.expense_nav_string))
                val income = it.arguments?.getInt(context.getString(R.string.income_nav_string))

                if (expense != null && income != null) {
                    MoneySummaryScreen(numExpense = expense, numIncome = income)
                }
            }
            composable(context.getString(R.string.login_nav_text)) { PinRequestScreen(navController = navController) }
        }
    }
}

