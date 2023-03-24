package com.example.andwallet.ui.screen.pinRequest

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class PinRequestViewModel : ViewModel() {
    var newPin by mutableStateOf("")
    var isAllowed by mutableStateOf(false)
    var inputError by mutableStateOf(false)
    var errorText by mutableStateOf("")

    fun checkLoginAccess(){
        if (newPin == "5738")
            isAllowed = true
    }
}