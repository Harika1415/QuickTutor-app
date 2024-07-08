package uk.ac.tees.mad.D3933743.ui.theme.all_tutors.compose

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DisplayMode
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.SelectableDates
import androidx.compose.material3.Text
import androidx.compose.material3.TimePicker
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.google.android.gms.maps.GoogleMapOptions
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
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import uk.ac.tees.mad.D3933743.R
import uk.ac.tees.mad.D3933743.ui.theme.home.data.Tutor
import uk.ac.tees.mad.D3933743.ui.theme.users.Course
import uk.ac.tees.mad.D3933743.ui.theme.users.User
import java.text.SimpleDateFormat
import java.util.Date

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

    var shouldShowPicker by remember {
        mutableStateOf(false)
    }

    var isBookingCheck by remember {
        mutableStateOf(false)
    }

    var selectedDateTime by remember {
        mutableStateOf("")
    }

    var alreadyBooked by remember {
        mutableStateOf(false)
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

        firebaseFireStore?.collection("users")
            ?.document(currentUser?.uid ?: "")?.get()?.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    if (task.result.exists()) {
                        userCourses = task.result.toObject(User::class.java)
                    }
                }
            }


    }
    LaunchedEffect(userCourses) {

        alreadyBooked = userCourses?.course?.any {
            it.tutorId == tutorId
        } ?: false
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

        if (!shouldShowMap && !shouldShowPicker) {


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
                                    .clip(CircleShape)
                                    .align(Alignment.CenterHorizontally),
                                painter =
                                if (tutorState.value.profileUrl != null)
                                    rememberAsyncImagePainter(model = tutorState.value.profileUrl)
                                else
                                    painterResource(
                                        id = R.drawable.ic_launcher_background
                                    ),
                                contentDescription = "Tutor Image",
                                contentScale = ContentScale.Crop
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
                                    onClick = {
                                        shouldShowPicker = true
                                        isBookingCheck = false
                                    }) {
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
                    enabled = !alreadyBooked,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                        .align(alignment = Alignment.CenterHorizontally)
                        .padding(top = 8.dp),
                    onClick = {

                        isBookingCheck = true
                        shouldShowPicker = true

                    }) {
                    Text(
                        text = if (alreadyBooked) "Already Booked" else "Book Session",
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
                    onClick = {

                        firebaseFireStore?.collection("users")
                            ?.document(currentUser?.uid ?: "")?.get()
                            ?.addOnCompleteListener { task ->
                                if (task.isSuccessful) {
                                    if (task.result.exists()) {
                                        val bookmarks = task.result.toObject(User::class.java)
                                        val bookmarkedCourses =
                                            bookmarks?.bookmarks?.toMutableList()
                                                ?: mutableListOf()
                                        bookmarkedCourses.add(
                                            tutorId
                                        )
                                        bookmarks?.bookmarks = bookmarkedCourses


                                        firebaseFireStore?.collection("users")
                                            ?.document(currentUser?.uid ?: "")
                                            ?.set(bookmarks!!)?.addOnCompleteListener { task1 ->
                                                if (task1.isSuccessful) {

                                                    Toast.makeText(
                                                        context,
                                                        "Bookmarked",
                                                        Toast.LENGTH_SHORT
                                                    ).show()
                                                } else {
                                                    Toast.makeText(
                                                        context,
                                                        "Failed to Add Bookmark list",
                                                        Toast.LENGTH_SHORT
                                                    ).show()
                                                }

                                            }

                                    } else {

                                        val bookmarks = mutableListOf<String>()
                                        bookmarks.add(
                                            tutorId
                                        )
                                        val user = User(bookmarks = bookmarks)

                                        firebaseFireStore?.collection("users")
                                            ?.document(currentUser?.uid ?: "")?.set(user)
                                            ?.addOnCompleteListener { task1 ->
                                                if (task1.isSuccessful) {
                                                    Toast.makeText(
                                                        context,
                                                        "Booked",
                                                        Toast.LENGTH_SHORT
                                                    ).show()
                                                } else {
                                                    Toast.makeText(
                                                        context,
                                                        "Failed to Book",
                                                        Toast.LENGTH_SHORT
                                                    ).show()
                                                }
                                            }
                                    }
                                } else {
                                    Toast.makeText(
                                        context,
                                        "Failed to Book",
                                        Toast.LENGTH_SHORT
                                    ).show()

                                }
                            }


                    }) {
                    Text(
                        text = "Add to Favourites",
                        fontSize = 20.sp,
                        modifier = Modifier.padding(horizontal = 16.dp)
                    )
                }
            }
        } else if (shouldShowPicker) {

            val stateD = rememberDatePickerState(selectableDates = object : SelectableDates {
                override fun isSelectableDate(utcTimeMillis: Long): Boolean {
                    return utcTimeMillis > System.currentTimeMillis() && utcTimeMillis <= (System.currentTimeMillis() + 4320000000L)
                }
            })

            val stateT = rememberTimePickerState(is24Hour = false)


            Column(
                modifier = Modifier
                    .fillMaxSize()

            ) {

                TopAppBar(title = {
                    Text(
                        text = if (isBookingCheck) "Book Session" else "Check Availability",
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
                                    shouldShowPicker = false
                                },
                            painter = painterResource(id = R.drawable.back_arrow),
                            contentDescription = "Back Arrow"
                        )
                    })

                Column(modifier = Modifier.verticalScroll(rememberScrollState())) {


                    DatePicker(
                        modifier = Modifier
                            .fillMaxWidth()
                            .align(Alignment.CenterHorizontally),
                        state = stateD
                    )
                    TimePicker(
                        modifier = Modifier
                            .fillMaxWidth()
                            .align(Alignment.CenterHorizontally),
                        state = stateT
                    )

                    if (isBookingCheck) {

                        Button(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 32.dp)
                                .padding(horizontal = 16.dp)
                                .align(Alignment.CenterHorizontally),
                            onClick = {


                                val selectedDate: String? =
                                    stateD.selectedDateMillis?.let { millis ->
                                        val formatter = SimpleDateFormat("dd/MM/yyyy")
                                        formatter.format(Date(millis))
                                    }


                                var selectedTime = ""
                                selectedTime += if (stateT.hour <= 9) {
                                    "0${stateT.hour}"
                                } else {
                                    "${stateT.hour}"
                                }
                                selectedTime += if (stateT.minute <= 9) {
                                    " : 0${stateT.minute} Hrs"
                                } else {
                                    "${stateT.minute} Hrs"
                                }


                                shouldShowPicker = false
                                shouldShowLoader = true
                                firebaseFireStore?.collection("users")
                                    ?.document(currentUser?.uid ?: "")?.get()
                                    ?.addOnCompleteListener { task ->
                                        if (task.isSuccessful) {
                                            if (task.result.exists()) {
                                                val course = task.result.toObject(User::class.java)
                                                val mutableCourse =
                                                    course?.course?.toMutableList()
                                                        ?: mutableListOf()
                                                mutableCourse.add(
                                                    Course(
                                                        tutorId,
                                                        tutorState.value.profileUrl,
                                                        tutorState.value.name,
                                                        "$selectedDate, $selectedTime"
                                                    )
                                                )
                                                course?.course = mutableCourse

                                                firebaseFireStore?.collection("users")
                                                    ?.document(currentUser?.uid ?: "")
                                                    ?.set(course!!)
                                                    ?.addOnCompleteListener { task1 ->
                                                        shouldShowLoader = false
                                                        if (task1.isSuccessful) {
                                                            alreadyBooked = true
                                                            Toast.makeText(
                                                                context,
                                                                "Booked",
                                                                Toast.LENGTH_SHORT
                                                            ).show()
                                                        } else {
                                                            Toast.makeText(
                                                                context,
                                                                "Failed to Book",
                                                                Toast.LENGTH_SHORT
                                                            ).show()
                                                        }

                                                    }

                                            } else {

                                                val mutableCourse = mutableListOf<Course>()
                                                mutableCourse.add(
                                                    Course(
                                                        tutorId,
                                                        tutorState.value.profileUrl,
                                                        tutorState.value.name,
                                                        "$selectedDate, $selectedTime"
                                                    )
                                                )
                                                val user = User(course = mutableCourse)

                                                firebaseFireStore?.collection("users")
                                                    ?.document(currentUser?.uid ?: "")?.set(user)
                                                    ?.addOnCompleteListener { task1 ->
                                                        shouldShowLoader = false
                                                        alreadyBooked = true
                                                        if (task1.isSuccessful) {
                                                            Toast.makeText(
                                                                context,
                                                                "Booked",
                                                                Toast.LENGTH_SHORT
                                                            ).show()
                                                        } else {
                                                            Toast.makeText(
                                                                context,
                                                                "Failed to Book",
                                                                Toast.LENGTH_SHORT
                                                            ).show()
                                                        }

                                                    }
                                            }
                                        } else {
                                            shouldShowLoader = false
                                            Toast.makeText(
                                                context,
                                                "Failed to Book",
                                                Toast.LENGTH_SHORT
                                            ).show()

                                        }
                                    }
                            }) {
                            Text(text = "Book Session")
                        }

                    } else {
                        Button(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 32.dp)
                                .padding(horizontal = 16.dp)
                                .align(Alignment.CenterHorizontally),
                            onClick = {
                                if (System.currentTimeMillis() % 2 == 0L) {
                                    Toast.makeText(
                                        context,
                                        "TimeSlot Available",
                                        Toast.LENGTH_SHORT
                                    )
                                        .show()
                                } else {
                                    Toast.makeText(
                                        context,
                                        "TimeSlot Not Available",
                                        Toast.LENGTH_SHORT
                                    )
                                        .show()
                                }
                            }) {
                            Text(text = "Check")
                        }
                    }


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
            uiSettings = MapUiSettings(
                scrollGesturesEnabled = false,
                compassEnabled = false,
                scrollGesturesEnabledDuringRotateOrZoom = false,
                tiltGesturesEnabled = false
            ),
            cameraPositionState = cameraPositionState,
        ) {
            Marker(
                state = MarkerState(
                    cameraPositionState.position.target
                )
            )
        }
    }
}

