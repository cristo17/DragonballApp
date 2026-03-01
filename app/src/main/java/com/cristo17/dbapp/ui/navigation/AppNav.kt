package com.cristo17.dbapp.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.cristo17.dbapp.ui.screens.character_list.CharacterListScreen
import com.cristo17.dbapp.ui.screens.character_detail.CharacterDetailScreen
import com.cristo17.dbapp.ui.screens.splash.SplashScreen

@Composable
fun AppNav() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "splash") {
        // Pantalla de Introducción
        composable("splash") {
            SplashScreen(onAnimationFinished = {
                // Al terminar la animación, vamos a la lista y quitamos splash del historial
                navController.navigate("list") {
                    popUpTo("splash") { inclusive = true }
                }
            })
        }

        // Pantalla de Lista
        composable("list") {
            CharacterListScreen(onCharacterClick = { id ->
                navController.navigate("detail/$id")
            })
        }

        // Pantalla de Detalle
        composable(
            route = "detail/{id}",
            arguments = listOf(navArgument("id") { type = NavType.IntType })
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getInt("id") ?: 1
            CharacterDetailScreen(characterId = id, onBack = { navController.popBackStack() })
        }
    }
}