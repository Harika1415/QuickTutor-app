package uk.ac.tees.mad.D3933743.ui.theme.users

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ElevatedCard
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
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
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.auth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import uk.ac.tees.mad.D3933743.R
import uk.ac.tees.mad.D3933743.ui.theme.Util.UserRatingBar
import uk.ac.tees.mad.D3933743.ui.theme.home.data.Tutor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AllMyCourses(navController: NavController) {

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

    var context = LocalContext.current


    var userCourses by remember {
        mutableStateOf<User?>(null)
    }


    LaunchedEffect(Unit) {
        currentUser = Firebase.auth.currentUser
        firebaseFireStore = Firebase.firestore
        firebaseFireStore?.collection("users")
            ?.document(currentUser?.uid ?: "")?.get()?.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    if (task.result.exists()) {
                        userCourses = task.result.toObject(User::class.java)
                    }
                }
            }
    }


    if (userCourses?.course?.isNotEmpty() == true) {


        Column {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = colorResource(id = R.color.teal_200),
                    titleContentColor = colorResource(id = R.color.white),
                    actionIconContentColor = colorResource(id = R.color.white),
                    navigationIconContentColor = colorResource(id = R.color.white)
                ),
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
                },
                modifier = Modifier.padding(start = 0.dp),
                windowInsets = WindowInsets(top = 0.dp),
                title = {
                    Text(text = "My Courses", modifier = Modifier.padding(start = 8.dp))
                },
            )




            LazyColumn(
                modifier = Modifier.padding(top = 12.dp),
                userScrollEnabled = false) {
                items(userCourses?.course?.size ?: 0) { index ->
                    ElevatedCard(
                        modifier = Modifier
                            .padding(8.dp)
                            .fillMaxWidth()
                    ) {
                        Column(modifier = Modifier.padding(6.dp)) {


                            Row {
                                Image(
                                    modifier = Modifier
                                        .size(75.dp, 75.dp)
                                        .align(Alignment.CenterVertically),
                                    painter =
                                    if (userCourses!!.course[index].tutorImage != null) {
                                        rememberAsyncImagePainter(userCourses!!.course[index].tutorImage)
                                    } else
                                        painterResource(id = R.drawable.ic_launcher_background),
                                    contentDescription = "Tutor Image"
                                )

                                Column(Modifier.padding(start = 12.dp)) {
                                    Text(
                                        text = userCourses!!.course[index].tutorName!!,
                                        fontSize = 20.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                    Text(
                                        text = userCourses!!.course[index].dateTime!!,
                                        fontSize = 14.sp,
                                        lineHeight = 10.sp
                                    )

                                    UserRatingBar()

                                    Button(
                                        modifier = Modifier.fillMaxWidth().padding(horizontal = 24.dp).padding(top = 12.dp),
                                        onClick = {
                                        Toast.makeText(context,"Rating Submitted",Toast.LENGTH_SHORT).show()
                                    }) {
                                        Text(text = "Submit")
                                    }

                                }

                            }
                        }
                    }
                }
            }
        }
    }
}
