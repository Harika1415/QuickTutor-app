package uk.ac.tees.mad.D3933743.ui.theme.profile.compose

import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.request.ImageResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.auth.auth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import uk.ac.tees.mad.D3933743.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Preview(showBackground = true)
fun ProfileScreen(navController: NavHostController? = null) {

    var userName by remember {
        mutableStateOf("")
    }

    val context = LocalContext.current

    var oldUserName by remember {
        mutableStateOf<String?>(null)
    }
    var oldProfileUrl by remember {
        mutableStateOf<String?>(null)
    }

    var profileImageUrl by remember {
        mutableStateOf<String?>(null)
    }

    var firebaseAuth by remember {
        mutableStateOf<FirebaseAuth?>(null)
    }

    var shouldShowLoader by remember {
        mutableStateOf(false)
    }

    var isSaveButtonEnabled by remember {
        mutableStateOf(false)
    }

    var firebaseStorage by remember {
        mutableStateOf<StorageReference?>(null)
    }

    val launcher =
        rememberLauncherForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
            profileImageUrl = uri?.toString()
        }

    LaunchedEffect(userName, profileImageUrl, oldProfileUrl, oldUserName) {
        isSaveButtonEnabled = (userName != oldUserName || profileImageUrl != oldProfileUrl) &&
                userName.isNotEmpty() && profileImageUrl != null
    }


    LaunchedEffect(Unit) {
        firebaseAuth = Firebase.auth
        if (firebaseAuth?.currentUser == null) {
            navController?.navigate("Login") {
                popUpTo("ProfileScreen") {
                    inclusive = true
                }
            }
        }
        firebaseStorage =
            FirebaseStorage.getInstance().reference.child(firebaseAuth?.currentUser?.uid ?: "")
        userName = firebaseAuth?.currentUser?.displayName ?: ""
        oldUserName = userName
        profileImageUrl = firebaseAuth?.currentUser?.photoUrl?.toString()
        oldProfileUrl = profileImageUrl
    }

    if (shouldShowLoader) {
        CircularProgressIndicator()
    } else {


        Column() {

            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = colorResource(id = R.color.teal_200),
                    titleContentColor = colorResource(id = R.color.white),
                    actionIconContentColor = colorResource(id = R.color.white),
                    navigationIconContentColor = colorResource(id = R.color.white)
                ),
                windowInsets = WindowInsets(top = 0.dp),
                title = {
                    Text(text = "Profile", modifier = Modifier.padding(start = 16.dp))
                },
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

            Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 32.dp)) {
                Image(
                    modifier = Modifier
                        .clip(CircleShape)
                        .size(125.dp, 125.dp)
                        .align(Alignment.CenterHorizontally)
                        .clickable {
                            launcher.launch(
                                PickVisualMediaRequest(
                                    mediaType = ActivityResultContracts.PickVisualMedia.ImageOnly
                                )
                            )
                        },
                    painter =
                    if (profileImageUrl != null)
                        rememberAsyncImagePainter(profileImageUrl)
                    else
                        painterResource(id = R.drawable.ic_launcher_background),
                    contentDescription = "Profile Image"
                )

                TextField(
                    modifier = Modifier
                        .padding(top = 16.dp)
                        .fillMaxWidth(),
                    value = userName, onValueChange = { value ->
                        userName = value
                    },
                    placeholder = {
                        Text(text = "User Name")
                    })

                Button(
                    enabled = isSaveButtonEnabled,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 24.dp),
                    onClick = {

                        shouldShowLoader = true

                        firebaseStorage?.putFile(Uri.parse(profileImageUrl))
                            ?.addOnCompleteListener { task ->
                                if (task.isSuccessful) {
                                    firebaseStorage?.downloadUrl?.addOnCompleteListener { task1 ->
                                        if (task1.isSuccessful) {
                                            firebaseAuth?.currentUser?.updateProfile(
                                                UserProfileChangeRequest.Builder()
                                                    .setDisplayName(userName)
                                                    .setPhotoUri(task1.result).build()
                                            )?.addOnCompleteListener { task ->
                                                if (task.isSuccessful) {
                                                    shouldShowLoader = false
                                                    Toast.makeText(
                                                        context,
                                                        "Profile Updated Successfully",
                                                        Toast.LENGTH_SHORT
                                                    ).show()
                                                } else {
                                                    shouldShowLoader = false
                                                    Toast.makeText(
                                                        context,
                                                        "Profile Update Failed",
                                                        Toast.LENGTH_SHORT
                                                    )
                                                        .show()
                                                }
                                            }
                                        } else {
                                            shouldShowLoader = false
                                            Toast.makeText(
                                                context,
                                                "Profile Update Failed",
                                                Toast.LENGTH_SHORT
                                            )
                                                .show()
                                        }
                                    }
                                } else {
                                    shouldShowLoader = false
                                    Toast.makeText(
                                        context,
                                        "Profile Update Failed",
                                        Toast.LENGTH_SHORT
                                    )
                                        .show()
                                }
                            }

                    }) {
                    Text(text = "Update Profile")
                }


                Button(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 12.dp),
                    onClick = { }) {
                    Text(text = "Change Password")
                }

                Button(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 12.dp),
                    onClick = {
                        firebaseAuth?.signOut()
                        navController?.navigate("Login")
                    }) {
                    Text(text = "Logout")
                }
            }
        }
    }
}