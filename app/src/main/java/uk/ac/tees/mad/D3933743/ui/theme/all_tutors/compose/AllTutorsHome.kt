package uk.ac.tees.mad.D3933743.ui.theme.all_tutors.compose

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import uk.ac.tees.mad.D3933743.R
import uk.ac.tees.mad.D3933743.ui.theme.home.data.Tutor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AllTutorsHome(navController: NavController) {

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



    LaunchedEffect(Unit) {
        shouldShowLoader = true
        firebaseAuth = Firebase.auth
        currentUser = firebaseAuth?.currentUser
        firebaseFireStore = Firebase.firestore


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
    }

    Column(modifier = Modifier.background(color = colorResource(id = R.color.white))) {

        TopAppBar(title = {
            Text(text = "Tutors", modifier = Modifier.padding(start = 16.dp))
        },
            windowInsets = WindowInsets(top = 0.dp),
            navigationIcon = {
                Icon(
                    modifier = Modifier
                        .padding(start = 8.dp)
                        .clickable {
                            navController.popBackStack()
                        },
                    painter = painterResource(id = R.drawable.back_arrow),
                    contentDescription = "Back Arrow"
                )
            })

        LazyColumn {
            items(tutorListState.size) { index ->
                ElevatedCard(
                    modifier = Modifier
                        .clickable {
                            navController.navigate("TutorDetails/${tutorListState[index].id}")
                        }
                        .padding(8.dp)
                        .fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(6.dp)) {
                        Row {
                            Image(
                                modifier = Modifier
                                    .size(50.dp, 50.dp)
                                    .align(Alignment.CenterVertically),
                                painter = rememberAsyncImagePainter(tutorListState[index].profileUrl),
                                contentDescription = "Tutor Image"
                            )
                            Column(modifier = Modifier.padding(start = 16.dp)) {
                                Text(
                                    text = tutorListState[index].name!!,
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.Bold
                                )
                                Text(text = tutorListState[index].bio!!, fontSize = 8.sp)
                                Text(text = "Expert in : ${tutorListState[index].subjects!!}", fontSize = 8.sp)
                            }
                        }
                    }
                }
            }
        }
    }
}

