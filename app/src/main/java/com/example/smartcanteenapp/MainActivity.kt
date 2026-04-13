package com.example.smartcanteenapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.smartcanteenapp.navigation.AppNavigation
import com.example.smartcanteenapp.ui.theme.SmartcanteenappTheme
import android.widget.Toast
import com.example.smartcanteenapp.viewmodel.PaymentState
import com.razorpay.PaymentResultListener

class MainActivity : ComponentActivity(), com.razorpay.PaymentResultListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            SmartcanteenappTheme {
                AppNavigation()
            }
        }
    }

    override fun onPaymentSuccess(p0: String?) {
        Toast.makeText(this, "Payment Successful 🎉", Toast.LENGTH_SHORT).show()

        PaymentState.paymentSuccess = true
    }

    override fun onPaymentError(p0: Int, p1: String?) {
        Toast.makeText(this, "Payment Failed ❌", Toast.LENGTH_SHORT).show()
    }
}