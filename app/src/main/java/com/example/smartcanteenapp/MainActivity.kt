package com.example.smartcanteenapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.smartcanteenapp.navigation.AppNavigation
import com.example.smartcanteenapp.ui.theme.SmartcanteenappTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            SmartcanteenappTheme {
                AppNavigation()
            }
        }
    }
}