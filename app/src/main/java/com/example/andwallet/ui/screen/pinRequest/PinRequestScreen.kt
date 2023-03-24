package com.example.andwallet.ui.screen.pinRequest

import android.content.Context
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.andwallet.R

@Composable
fun PinRequestScreen(
    pinRequestViewModel : PinRequestViewModel = viewModel(),
    navController: NavController
){

    val context = LocalContext.current

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()
    ){
        OutlinedTextField(
            value = pinRequestViewModel.newPin,
            label = { Text(text = context.getString(R.string.login_label)) },
            isError = pinRequestViewModel.inputError,
            onValueChange = {
                pinRequestViewModel.newPin = it
            },
            modifier = Modifier
                .padding(10.dp),
            trailingIcon = {
                if (pinRequestViewModel.inputError) {
                    Icon(
                        Icons.Filled.Warning, context.getString(R.string.error_desc),
                        tint = MaterialTheme.colorScheme.error
                    )
                    pinRequestViewModel.errorText = context.getString(R.string.incorrect_pin_text)
                }
            }
        )

        if (pinRequestViewModel.inputError) {
            Text(
                text = pinRequestViewModel.errorText,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.labelSmall,
                modifier = Modifier.padding(start = 16.dp)
            )
        }

        Button(
            modifier = Modifier.padding(10.dp),
            onClick = {
                pinRequestViewModel.checkLoginAccess()

                if(pinRequestViewModel.isAllowed){
                    navController.navigate(
                        context.getString(R.string.list_nav_route)
                    )
                }
                else {
                    pinRequestViewModel.inputError = true
                }
            }
        ){
            Text(text = context.getString(R.string.login_string))
        }
    }
}