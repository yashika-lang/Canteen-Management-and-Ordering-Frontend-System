package com.example.smartcanteenapp.navigation

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.compose.*
import com.example.smartcanteenapp.ui.auth.LoginScreen
import com.example.smartcanteenapp.ui.auth.RegisterScreen
import com.example.smartcanteenapp.ui.screens.SplashScreen
import com.example.smartcanteenapp.ui.user.HomeScreen
import com.example.smartcanteenapp.ui.admin.AdminScreen
import com.example.smartcanteenapp.ui.admin.AddItemScreen
import com.example.smartcanteenapp.ui.admin.ManageItemsScreen
import com.example.smartcanteenapp.ui.admin.OrdersScreen

// ✅ ROUTES OBJECT
object Routes {
    const val SPLASH = "splash"
    const val LOGIN = "login"
    const val REGISTER = "register"
    const val HOME = "home"
    const val ADMIN = "admin"
    const val CART = "cart"
    const val PROFILE = "profile"

    // ADMIN FEATURES
    const val ADD_ITEM = "add_item"
    const val MANAGE_ITEMS = "manage_items"
    const val ORDERS = "orders"
}

@Composable
fun AppNavigation() {

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Routes.SPLASH
    ) {

        composable(Routes.SPLASH) {
            SplashScreen(navController)
        }

        composable(Routes.LOGIN) {
            LoginScreen(navController)
        }

        composable(Routes.REGISTER) {
            RegisterScreen(navController)
        }

        composable(Routes.HOME) {
            HomeScreen(navController)
        }

        composable(Routes.ADMIN) {
            AdminScreen(navController)
        }
        composable(Routes.ADD_ITEM) {
            AddItemScreen()
        }

        composable(Routes.MANAGE_ITEMS) {
            ManageItemsScreen()
        }

        composable(Routes.ORDERS) {
            OrdersScreen()
        }

        composable(Routes.CART) {
            Text("Cart Screen Coming Soon")
        }

        composable(Routes.PROFILE) {
            Text("Profile Screen Coming Soon")
        }
    }
}