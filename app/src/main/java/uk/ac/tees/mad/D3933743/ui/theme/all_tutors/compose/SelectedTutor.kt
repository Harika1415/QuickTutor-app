package uk.ac.tees.mad.D3933743.ui.theme.all_tutors.compose

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.common.collect.Maps
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import uk.ac.tees.mad.D3933743.R
import uk.ac.tees.mad.D3933743.ui.theme.home.data.Tutor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SelectedTutor(navController: NavController? = null, tutorId: String) {

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

    val tutorState = remember {
        mutableStateOf(Tutor())
    }

    var shouldShowMap by remember {
        mutableStateOf(false)
    }

    LaunchedEffect(Unit) {
        shouldShowLoader = true
        firebaseAuth = Firebase.auth
        currentUser = firebaseAuth?.currentUser
        firebaseFireStore = Firebase.firestore


        firebaseFireStore?.collection("tutors")?.document(tutorId)?.get()
            ?.addOnCompleteListener { task ->
                if (task.isSuccessful && task.result.exists()) {


                    val tutor = task.result.toObject(Tutor::class.java)
                    if (tutor != null) {
                        tutorState.value = tutor
                    }

                    shouldShowLoader = false

                } else {
                    shouldShowLoader = false
                }
            }
    }

    if (!shouldShowMap) {


        Column(modifier = Modifier.fillMaxSize()) {

            TopAppBar(title = {
                Text(
                    text = tutorState.value.name ?: "",
                    modifier = Modifier.padding(start = 16.dp)
                )
            },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = colorResource(id = R.color.teal_200),
                    titleContentColor = colorResource(id = R.color.white),
                    actionIconContentColor = colorResource(id = R.color.white),
                    navigationIconContentColor = colorResource(id = R.color.white)
                ),
                windowInsets = WindowInsets(top = 0.dp),
                navigationIcon = {
                    Icon(
                        modifier = Modifier
                            .padding(start = 8.dp)
                            .clickable {
                                navController?.popBackStack()
                            },
                        painter = painterResource(id = R.drawable.back_arrow),
                        contentDescription = "Back Arrow"
                    )
                })

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
                    .clip(shape = RoundedCornerShape(0.dp, 0.dp, 35.dp, 35.dp))
            ) {
                Card(shape = RectangleShape, modifier = Modifier.fillMaxWidth()) {

                    Column(Modifier.padding(horizontal = 16.dp)) {
                        Image(
                            modifier = Modifier
                                .padding(top = 32.dp)
                                .size(125.dp, 125.dp)
                                .align(Alignment.CenterHorizontally),
                            painter =
                            if (tutorState.value.profileUrl != null)
                                rememberAsyncImagePainter(model = tutorState.value.profileUrl)
                            else
                                painterResource(
                                    id = R.drawable.ic_launcher_background
                                ),
                            contentDescription = "Tutor Image"
                        )
                        Text(
                            text = tutorState.value.name ?: "",
                            modifier = Modifier
                                .padding(top = 16.dp)
                                .align(Alignment.CenterHorizontally),
                            fontSize = 22.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = tutorState.value.bio ?: "",
                            fontSize = 16.sp,
                            modifier = Modifier.padding(top = 8.dp)
                        )
                        Text(

                            text = "Rating: ${tutorState.value.rating} ★",
                            fontSize = 16.sp,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.padding(top = 16.dp)
                        )
                        Text(
                            text = "Expertise in : ${tutorState.value.subjects}",
                            fontSize = 16.sp,
                            modifier = Modifier.padding(top = 16.dp, bottom = 24.dp)
                        )

                        Row(Modifier.padding(bottom = 16.dp)) {
                            Button(
                                modifier = Modifier
                                    .padding(horizontal = 8.dp)
                                    .weight(1f)
                                    .padding(top = 8.dp),
                                onClick = { /*TODO*/ }) {
                                Text(text = "Check Availability", fontSize = 14.sp)
                            }

                            Button(
                                modifier = Modifier
                                    .padding(horizontal = 8.dp)
                                    .weight(1f)
                                    .padding(top = 8.dp),
                                onClick = {
                                    shouldShowMap = true
                                }) {
                                Text(text = "View Location", fontSize = 14.sp)
                            }
                        }
                    }
                }
            }





            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .align(alignment = Alignment.CenterHorizontally)
                    .padding(top = 8.dp),
                onClick = {

//                    firebaseFireStore.collection("bookedSession")




                }) {
                Text(
                    text = "Book Session",
                    fontSize = 20.sp,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
            }

            Button(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .fillMaxWidth()
                    .align(alignment = Alignment.CenterHorizontally)
                    .padding(top = 8.dp),
                onClick = { /*TODO*/ }) {
                Text(
                    text = "Add to Favourites",
                    fontSize = 20.sp,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
            }
        }
    } else {

        Column(modifier = Modifier.fillMaxSize()) {

            TopAppBar(title = {
                Text(
                    text = "Location",
                    modifier = Modifier.padding(start = 16.dp)
                )
            },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = colorResource(id = R.color.teal_200),
                    titleContentColor = colorResource(id = R.color.white),
                    actionIconContentColor = colorResource(id = R.color.white),
                    navigationIconContentColor = colorResource(id = R.color.white)
                ),
                windowInsets = WindowInsets(top = 0.dp),
                navigationIcon = {
                    Icon(
                        modifier = Modifier
                            .padding(start = 8.dp)
                            .clickable {
                                shouldShowMap = false
                            },
                        painter = painterResource(id = R.drawable.back_arrow),
                        contentDescription = "Back Arrow"
                    )
                })


            MapScreen(
                latLng = LatLng(
                    tutorState.value.latitude?.toDouble() ?: 0.0,
                    tutorState.value.longitude?.toDouble() ?: 0.0
                )
            )
        }
    }

}

@Composable
fun MapScreen(latLng: LatLng) {

    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(latLng, 15f)
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        GoogleMap(
            cameraPositionState = cameraPositionState
        ) {
            Marker(
                state = MarkerState(
                    cameraPositionState.position.target
                )
            )
        }
    }
}

