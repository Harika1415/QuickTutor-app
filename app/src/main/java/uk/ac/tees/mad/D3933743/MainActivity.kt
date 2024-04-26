package uk.ac.tees.mad.D3933743

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import uk.ac.tees.mad.D3933743.ui.theme.Login.compose.LoginScreen
import uk.ac.tees.mad.D3933743.ui.theme.Login.compose.RegisterScreen
import uk.ac.tees.mad.D3933743.ui.theme.QuickTutorTheme
import uk.ac.tees.mad.D3933743.ui.theme.all_tutors.compose.AllTutorsHome
import uk.ac.tees.mad.D3933743.ui.theme.all_tutors.compose.SelectedTutor
import uk.ac.tees.mad.D3933743.ui.theme.home.compose.HomeScreen
import uk.ac.tees.mad.D3933743.ui.theme.home.compose.MyCourses
import uk.ac.tees.mad.D3933743.ui.theme.profile.compose.ProfileScreen
import uk.ac.tees.mad.D3933743.ui.theme.splash.SplashScreen
import uk.ac.tees.mad.D3933743.ui.theme.users.AllMyCourses

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {

            QuickTutorTheme(darkTheme = false) {
                MainScreen()
            }
        }
    }
}


@Composable
@Preview
fun MainScreen() {
    val navController = rememberNavController()

    LaunchedEffect(navController) {

    }

    Scaffold(
        content = { innerPadding ->
            Box(
                modifier = Modifier
                    .padding(top = innerPadding.calculateTopPadding())
            ) {

                NavHost(
                    modifier = Modifier
                        .fillMaxSize(),
                    navController = navController,
                    startDestination = "SplashScreen"
                ) {

                    navigation(
                        route = "Login",
                        startDestination = "LoginScreen"
                    ) {
                        composable(route = "LoginScreen") {
                            LoginScreen(navController)
                        }
                        composable(route = "RegisterScreen") {
                            RegisterScreen(navController)
                        }
                    }

                    composable(route = "HomeScreen") {
                        HomeScreen(navController)
                    }

                    composable(route = "SplashScreen") {
                        SplashScreen(navController)
                    }

                    composable(route = "ProfileScreen") {
                        ProfileScreen(navController)
                    }

                    composable(route = "MyCourses") {
                        AllMyCourses(navController)
                    }

                    navigation(
                        route = "AllTutors",
                        startDestination = "AllTutorsHome"
                    ) {
                        composable(route = "AllTutorsHome") {
                            AllTutorsHome(navController)
                        }
                        composable(route = "TutorDetails/{tutorId}",
                            arguments = listOf(navArgument("tutorId") { type = NavType.StringType })
                        )
                        { backStackEntry ->
                            SelectedTutor(
                                navController,
                                backStackEntry.arguments?.getString("tutorId") ?: ""
                            )
                        }
                    }
                }
            }
        },
    )
}

