package com.example.emergetestapplication.emerge.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.emergetestapplication.emerge.data.model.movies.Movie
import com.example.emergetestapplication.emerge.presentation.view.compose.CreateListScreen
import com.example.emergetestapplication.emerge.presentation.view.compose.HomeScreen
import com.example.emergetestapplication.emerge.presentation.view.compose.LoginScreen
import com.example.emergetestapplication.emerge.presentation.view.compose.SearchMoviesScreen
import com.example.emergetestapplication.emerge.presentation.view.compose.SignUpScreen
import com.example.emergetestapplication.emerge.presentation.view.compose.StartUpScreen
import com.example.emergetestapplication.emerge.presentation.viewmodel.AuthViewModel
import com.example.emergetestapplication.emerge.presentation.viewmodel.MoviesViewModel

sealed class Screen(
    val route: String,
) {
    object Startup : Screen("startup")
    object Login : Screen("login")
    object Signup : Screen("signup")
    object Home : Screen("home")
    object CreateList : Screen("create_list")
    object SearchMovie : Screen("search_movie")
}

@Composable
fun MoviesNavHost(
    navController: NavHostController,
    authViewModel: AuthViewModel,
    moviesViewModel: MoviesViewModel,
) {
    val authState by authViewModel.authState.collectAsStateWithLifecycle()
    val moviesState by moviesViewModel.moviesState.collectAsStateWithLifecycle()
    var selectedMovies by remember { mutableStateOf<List<Movie>>(emptyList()) }

    NavHost(navController = navController, startDestination = Screen.Startup.route) {
        composable(Screen.Startup.route) {
            StartUpScreen(
                onNavigateToLogin = { navController.navigate(Screen.Login.route) },
                onNavigateToSignUp = { navController.navigate(Screen.Signup.route) },
            )
        }

        composable(Screen.Signup.route) {
            SignUpScreen(
                authState = authState,
                onSignUp = { username, password -> authViewModel.signUp(username, password) },
                onSignUpSuccess = {
                    navController.navigate(Screen.Home.route) {
                        popUpTo(Screen.Signup.route) { inclusive = true }
                    }
                },
            )
        }

        composable(Screen.Login.route) {
            LoginScreen(
                authState = authState,
                onLogin = { username, password -> authViewModel.login(username, password) },
                onLoginSuccess = {
                    navController.navigate(Screen.Home.route) {
                        popUpTo(Screen.Login.route) { inclusive = true }
                    }
                },
            )
        }

        composable(Screen.Home.route) {
            HomeScreen(
                authState = authState,
                moviesState = moviesState,
                onLogout = { authViewModel.logout() },
                onLogoutSuccess = {
                    navController.navigate(Screen.Startup.route) {
                        popUpTo(Screen.Home.route) { inclusive = true }
                    }
                },
                onCreateListClick = { navController.navigate(Screen.CreateList.route) },
                onSearchUsersClick = { },
            )
        }

        composable(Screen.CreateList.route) {
            CreateListScreen(
                onAddMoviesClick = { navController.navigate(Screen.SearchMovie.route) },
                onSaveListClick = { },
                selectedMovies = selectedMovies,
            )
        }

        composable(Screen.SearchMovie.route) {
            SearchMoviesScreen(
                moviesState = moviesState,
                searchMovies = { query -> moviesViewModel.searchMovies(query) },
                onMovieSelected = { movie ->
                    selectedMovies = selectedMovies + movie
                    navController.popBackStack()
                },
                clearMoviesSearch = { moviesViewModel.clearMoviesSearch() },
            )
        }
    }
}