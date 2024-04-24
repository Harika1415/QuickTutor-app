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
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import uk.ac.tees.mad.D3933743.ui.theme.Login.compose.LoginScreen
import uk.ac.tees.mad.D3933743.ui.theme.Login.compose.RegisterScreen
import uk.ac.tees.mad.D3933743.ui.theme.QuickTutorTheme
import uk.ac.tees.mad.D3933743.ui.theme.all_tutors.compose.AllTutorsHome
import uk.ac.tees.mad.D3933743.ui.theme.all_tutors.compose.SelectedTutor
import uk.ac.tees.mad.D3933743.ui.theme.all_tutors.data.TutorDetails
import uk.ac.tees.mad.D3933743.ui.theme.home.compose.HomeScreen
import uk.ac.tees.mad.D3933743.ui.theme.profile.compose.ProfileScreen
import uk.ac.tees.mad.D3933743.ui.theme.splash.SplashScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {

            QuickTutorTheme {
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

                    navigation(
                        route = "AllTutors",
                        startDestination = "AllTutorsHome"
                    ) {
                        composable(route = "AllTutorsHome") {
                            AllTutorsHome(
                                navController, listOf(
                                    TutorDetails(
                                        "Lady Margaret's",
                                        "https://upload.wikimedia.org/wikipedia/commons/thumb/b/b3/Lady_Margaret_Beaufort_from_NPG.jpg/956px-Lady_Margaret_Beaufort_from_NPG.jpg",
                                        "Portrait of Margaret Beaufort (1443-1509) dressed as a widow, mother of Henry VII of England. The portrait features the coat of arms of the House of Beaufort, and the words \"souvent me souvient\", Medieval French for \"think of me often\", now used as the motto of Lady Margaret Hall, Oxford, Christ's College, Cambridge and St John's College, Cambridge.",
                                        "Divinity"

                                    ),
                                    TutorDetails(
                                        "Lady Margaret's",
                                        "https://upload.wikimedia.org/wikipedia/commons/thumb/b/b3/Lady_Margaret_Beaufort_from_NPG.jpg/956px-Lady_Margaret_Beaufort_from_NPG.jpg",
                                        "Portrait of Margaret Beaufort (1443-1509) dressed as a widow, mother of Henry VII of England. The portrait features the coat of arms of the House of Beaufort, and the words \"souvent me souvient\", Medieval French for \"think of me often\", now used as the motto of Lady Margaret Hall, Oxford, Christ's College, Cambridge and St John's College, Cambridge.",
                                        "Divinity"

                                    ),
                                    TutorDetails(
                                        "Lady Margaret's",
                                        "https://upload.wikimedia.org/wikipedia/commons/thumb/b/b3/Lady_Margaret_Beaufort_from_NPG.jpg/956px-Lady_Margaret_Beaufort_from_NPG.jpg",
                                        "Portrait of Margaret Beaufort (1443-1509) dressed as a widow, mother of Henry VII of England. The portrait features the coat of arms of the House of Beaufort, and the words \"souvent me souvient\", Medieval French for \"think of me often\", now used as the motto of Lady Margaret Hall, Oxford, Christ's College, Cambridge and St John's College, Cambridge.",
                                        "Divinity"

                                    ),
                                    TutorDetails(
                                        "Lady Margaret's",
                                        "https://upload.wikimedia.org/wikipedia/commons/thumb/b/b3/Lady_Margaret_Beaufort_from_NPG.jpg/956px-Lady_Margaret_Beaufort_from_NPG.jpg",
                                        "Portrait of Margaret Beaufort (1443-1509) dressed as a widow, mother of Henry VII of England. The portrait features the coat of arms of the House of Beaufort, and the words \"souvent me souvient\", Medieval French for \"think of me often\", now used as the motto of Lady Margaret Hall, Oxford, Christ's College, Cambridge and St John's College, Cambridge.",
                                        "Divinity"

                                    )
                                )
                            )
                        }
                        composable(route = "TutorDetails") {
                            SelectedTutor(navController)
                        }
                    }
                }
            }
        },
    )
}

