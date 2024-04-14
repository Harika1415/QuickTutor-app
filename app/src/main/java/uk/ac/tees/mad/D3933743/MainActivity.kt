package uk.ac.tees.mad.D3933743

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import uk.ac.tees.mad.D3933743.ui.theme.QuickTutorTheme
import uk.ac.tees.mad.D3933743.ui.theme.all_tutors.compose.AllTutorsHome
import uk.ac.tees.mad.D3933743.ui.theme.all_tutors.compose.SelectedTutor
import uk.ac.tees.mad.D3933743.ui.theme.all_tutors.data.TutorDetails
import uk.ac.tees.mad.D3933743.ui.theme.home.compose.HomeCompose

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


@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Preview
fun MainScreen() {
    val navController = rememberNavController()
    var appBarTitle by remember {
        mutableStateOf("Quick Tutor")
    }

    LaunchedEffect(navController) {
        navController.currentBackStackEntryFlow.collect { backStackEntry ->
            appBarTitle = backStackEntry.destination.route.toString()
        }
    }


    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
                    Text(text = appBarTitle )
                },
                navigationIcon = {
                    IconButton(onClick = {}) {
                        Icon(Icons.Filled.ArrowBack, "backIcon")
                    }
                },

                )
        },
        content = { innerPadding ->

            NavHost(
                modifier = Modifier.padding(innerPadding),
                navController = navController,
                startDestination = "HomeScreen"
            ) {

                composable(route = "HomeScreen") {
                    HomeCompose(navController)
                }

                navigation(
                    route = "AllTutors",
                    startDestination = "AllTutorsHome"
                ) {
                    composable(route = "AllTutorsHome") {
                        AllTutorsHome(navController,listOf(
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

                            )))
                    }
                    composable(route = "TutorDetails") {
                        SelectedTutor()
                    }
                }
            }
        }
    )
}

