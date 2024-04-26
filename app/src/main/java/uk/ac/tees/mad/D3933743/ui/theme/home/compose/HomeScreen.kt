package uk.ac.tees.mad.D3933743.ui.theme.home.compose

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import uk.ac.tees.mad.D3933743.R
import uk.ac.tees.mad.D3933743.ui.theme.home.data.Tutor
import uk.ac.tees.mad.D3933743.ui.theme.users.User

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavController) {

    var shouldShowLoader by remember {
        mutableStateOf(false)
    }

    var firebaseAuth by remember {
        mutableStateOf<FirebaseAuth?>(null)
    }
    var currentUser by remember {
        mutableStateOf<FirebaseUser?>(null)
    }

    var firebaseFireStore by remember {
        mutableStateOf<FirebaseFirestore?>(null)
    }

    val tutorListState = remember {
        mutableStateListOf<Tutor>()
    }

    var userCourses by remember {
        mutableStateOf<User?>(null)
    }

    val context = LocalContext.current

    LaunchedEffect(Unit) {
        shouldShowLoader = true
        firebaseAuth = Firebase.auth
        currentUser = firebaseAuth?.currentUser
        firebaseFireStore = Firebase.firestore
        if (currentUser != null) {


            if (currentUser?.displayName.isNullOrEmpty() || currentUser?.photoUrl?.toString()
                    .isNullOrEmpty()
            ) {
                shouldShowLoader = false
                Toast.makeText(context, "Add profile Information", Toast.LENGTH_SHORT).show()
                navController.navigate("ProfileScreen")
            }

            firebaseFireStore?.collection("tutors")?.get()?.addOnCompleteListener { task ->
                if (task.isSuccessful && task.result.documents.isNotEmpty()) {

                    for (document in task.result.documents) {
                        if (document.exists()) {
                            val tutor = document.toObject(Tutor::class.java)
                            if (tutor != null) {
                                tutorListState.add(tutor)
                            }
                        }
                    }
                    shouldShowLoader = false

                } else {
                    shouldShowLoader = false
                }
            }

            firebaseFireStore?.collection("users")
                ?.document(currentUser?.uid ?: "")?.get()?.addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        if (task.result.exists()) {
                            userCourses = task.result.toObject(User::class.java)
                        }
                    }
                }


        } else {
            shouldShowLoader = false
            navController.navigate("Login")
        }
    }

    if (shouldShowLoader) {

        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CircularProgressIndicator(modifier = Modifier.size(30.dp))
        }
    } else {
        Column {
            TopAppBar(
                actions = {
                    Icon(
                        modifier = Modifier.clickable {
                            navController.navigate("ProfileScreen")
                        },
                        tint = colorResource(id = R.color.white),
                        painter = painterResource(id = R.drawable.profile),
                        contentDescription = "Profile"
                    )
                },
                modifier = Modifier.padding(start = 0.dp),
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = colorResource(id = R.color.teal_200),
                    titleContentColor = colorResource(id = R.color.white)
                ),
                windowInsets = WindowInsets(top = 0.dp),
                title = {
                    Text(text = "Home", modifier = Modifier.padding(start = 8.dp))
                },
            )

            Text(
                text = "My Courses", modifier = Modifier.padding(
                    top = 8.dp,
                    start = 16.dp
                ),
                fontSize = 16.sp
            )
            MyCourses(userCourses)
            Text(
                text = "Recommended Tutors", modifier = Modifier.padding(
                    top = 16.dp,
                    start = 16.dp
                ),
                fontSize = 16.sp
            )
            RecommendedTutors(navController,
                tutorListState.toList().filterIndexed { index, _ ->
                    index % 2 == 0
                }
            )
            AllTutors(navController, tutorListState.toList())
        }
    }
}


