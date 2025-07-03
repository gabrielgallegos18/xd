package com.uam.navegacionobject.navigacion

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.uam.incrementovm.screen.ListUserScreen
import com.uam.incrementovm.screen.LoginScreen
import com.uam.navegacionobject.type.generarType
import kotlin.reflect.typeOf
import com.uam.incrementovm.model.User
import com.uam.incrementovm.screen.DetailUser


@Composable
fun Navigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Login) {
        composable<Login>{
            LoginScreen{navController.navigate(ListUser)}
        }
        composable<ListUser>{backStackEntry ->
           ListUserScreen{navController.navigate(Detail(it))}
        }
        /*composable<Detail>(typeMap = mapOf(typeOf<User>() to generarType<User>()))
        { backStackEntry ->
            val detail : Detail = backStackEntry.toRoute()
            DetailUser(detail.user)
        }*/
        composable<Detail>()
        { backStackEntry ->
            val detail : Detail = backStackEntry.toRoute()
            DetailUser(detail.id)
        }
    }
}