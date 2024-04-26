package uk.ac.tees.mad.D3933743.ui.theme.profile.compose

import android.content.Context
import android.content.pm.PackageManager
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
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
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.navigation.NavHostController
import androidx.viewbinding.BuildConfig
import coil.compose.rememberAsyncImagePainter
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import uk.ac.tees.mad.D3933743.R
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Objects
import java.util.jar.Manifest


@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Preview(showBackground = true)
fun ProfileScreen(navController: NavHostController? = null) {

    var userName by remember {
        mutableStateOf("")
    }

    var oldPassword by remember {
        mutableStateOf("")
    }


    var password by remember {
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

    var shouldShowResetPasswordEditText by remember {
        mutableStateOf(false)
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

    var showDialog by remember {
        mutableStateOf(false)
    }

    val file = context.createImageFile()
    val uri = FileProvider.getUriForFile(
        Objects.requireNonNull(context),
        "uk.ac.tees.mad.D3933743" + ".provider", file
    )

    var capturedImageUri by remember {
        mutableStateOf<Uri>(Uri.EMPTY)
    }


    val cameraLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.TakePicture()) {
            capturedImageUri = uri
            profileImageUrl = uri?.toString()
        }

    val permissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) {
        if (it) {
            Toast.makeText(context, "Permission Granted", Toast.LENGTH_SHORT).show()
            cameraLauncher.launch(uri)
        } else {
            Toast.makeText(context, "Permission Denied", Toast.LENGTH_SHORT).show()
        }
    }

    val launcher =
        rememberLauncherForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri1 ->
            profileImageUrl = uri1?.toString()
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
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CircularProgressIndicator(modifier = Modifier.size(30.dp))
        }
    } else {


        Column {

            if (showDialog) {
                Dialog(
                    onDismissRequest = {
                        showDialog = false
                    }) {
                    Card(modifier = Modifier.padding(32.dp)) {
                        Row(
                            modifier = Modifier.padding(32.dp),
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Image(

                                modifier = Modifier.clickable {
                                    showDialog = false
                                    val permissionCheckResult =
                                        ContextCompat.checkSelfPermission(context,android.Manifest.permission.CAMERA)
                                    if (permissionCheckResult == PackageManager.PERMISSION_GRANTED) {
                                        cameraLauncher.launch(uri)
                                    } else {
                                        permissionLauncher.launch(android.Manifest.permission.CAMERA)
                                    }
                                }.padding(16.dp),
                                painter = painterResource(id = R.drawable.baseline_camera_alt_24),
                                contentDescription = "Camera"
                            )
                            Image(
                                modifier = Modifier.clickable {
                                    showDialog = false
                                    launcher.launch(
                                        PickVisualMediaRequest(
                                            mediaType = ActivityResultContracts.PickVisualMedia.ImageOnly
                                        )
                                    )
                                }.padding(16.dp),
                                painter = painterResource(id = R.drawable.baseline_add_a_photo_24),
                                contentDescription = "Gallery"
                            )
                        }
                    }
                }
            }

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
                            showDialog = true
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
                    onClick = {
                        navController?.navigate("MyCourses")
                    }) {
                    Text(text = "My Courses")
                }

                if (shouldShowResetPasswordEditText) {


                    TextField(
                        modifier = Modifier
                            .padding(top = 16.dp)
                            .fillMaxWidth(),
                        value = oldPassword, onValueChange = { value ->
                            oldPassword = value
                        },
                        placeholder = {
                            Text(text = "Enter Old Password")
                        })

                    TextField(
                        modifier = Modifier
                            .padding(top = 16.dp)
                            .fillMaxWidth(),
                        value = password, onValueChange = { value ->
                            password = value
                        },
                        placeholder = {
                            Text(text = "Enter new Password")
                        })


                }

                Button(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 12.dp),
                    enabled = !shouldShowResetPasswordEditText || (password.length >= 6 && oldPassword.length >= 6),
                    onClick = {

                        if (shouldShowResetPasswordEditText) {
                            shouldShowLoader = true
                            val credential = EmailAuthProvider
                                .getCredential(firebaseAuth?.currentUser?.email ?: "", oldPassword)
                            firebaseAuth?.currentUser?.reauthenticate(credential)
                                ?.addOnCompleteListener { tas ->
                                    if (tas.isSuccessful) {
                                        firebaseAuth?.currentUser?.updatePassword(password)
                                            ?.addOnCompleteListener { task ->
                                                shouldShowLoader = false
                                                if (task.isSuccessful) {
                                                    firebaseAuth?.signOut()
                                                    navController?.navigate("Login")
                                                    password = ""
                                                    Toast.makeText(
                                                        context,
                                                        "Password updated Successfully",
                                                        Toast.LENGTH_SHORT
                                                    ).show()
                                                } else {
                                                    password = ""
                                                    Toast.makeText(
                                                        context,
                                                        "Password update got failed" + task.exception?.message,
                                                        Toast.LENGTH_SHORT
                                                    ).show()
                                                }
                                            }
                                    } else {
                                        shouldShowLoader = false
                                        Toast.makeText(
                                            context,
                                            "Invalid Old password",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    }
                                }

                        } else {
                            shouldShowResetPasswordEditText = true
                        }

                    }) {
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

fun Context.createImageFile(): File {
    val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
    val imageFileName = "JPEG_" + timeStamp + "_"
    val image = File.createTempFile(
        imageFileName,
        ".jpg",
        externalCacheDir
    )
    return image
}