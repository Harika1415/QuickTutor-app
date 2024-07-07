package uk.ac.tees.mad.D3933743.ui.theme.Login.compose

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import uk.ac.tees.mad.D3933743.R
import uk.ac.tees.mad.D3933743.ui.theme.Util.Utils


@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Preview
fun RegisterScreen(navController: NavController? = null) {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var shouldShowLoader by remember {
        mutableStateOf(false)
    }

    var enableSaveButton by remember {
        mutableStateOf(false)
    }

    LaunchedEffect(username, password) {
        enableSaveButton = Utils.emailValidator(username) && password.length >= 6
    }


    val context = LocalContext.current

    var firebaseAuth by remember {
        mutableStateOf<FirebaseAuth?>(null)
    }

    LaunchedEffect(Unit) {
        firebaseAuth = Firebase.auth
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

        Surface(
            color = Color.White,
            modifier = Modifier.fillMaxSize()
        ) {

//                TopAppBar(
//                    colors = TopAppBarDefaults.topAppBarColors(
//                        containerColor = colorResource(id = R.color.teal_200),
//                        titleContentColor = colorResource(id = R.color.white),
//                        actionIconContentColor = colorResource(id = R.color.white),
//                        navigationIconContentColor = colorResource(id = R.color.white)
//                    ),
//                    windowInsets = WindowInsets(top = 0.dp),
//                    title = {
//                        Text(text = "Register", modifier = Modifier.padding(start = 16.dp))
//                    })
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(id = R.drawable.logo),
                    contentDescription = "Login Image",
                    modifier = Modifier.size(120.dp)
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "Register",
                    fontSize = 30.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(16.dp))
                OutlinedTextField(
                    value = username,
                    onValueChange = { username = it },
                    label = { Text("Username") },
                    modifier = Modifier.fillMaxWidth(),
                    leadingIcon = {
                        Icon(
                            painter = painterResource(id = R.drawable.profile),
                            contentDescription = "Username Icon"
                        )
                    }
                )
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = password,
                    onValueChange = { password = it },
                    label = { Text("Password") },
                    visualTransformation = PasswordVisualTransformation(),
                    modifier = Modifier.fillMaxWidth(),
                    leadingIcon = {
                        Icon(
                            painter = painterResource(id = R.drawable.profile),
                            contentDescription = "Password Icon"
                        )
                    }
                )
                Spacer(modifier = Modifier.height(16.dp))
                Button(
                    onClick = {
                        shouldShowLoader = true
                        firebaseAuth?.createUserWithEmailAndPassword(username, password)
                            ?.addOnCompleteListener { task ->
                                if (task.isSuccessful) {
                                    shouldShowLoader = false
                                    navController?.navigate("HomeScreen")
                                } else {
                                    shouldShowLoader = false
                                    Toast.makeText(
                                        context, "Login failed  ${task.exception?.message}",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                ) {
                    Text("Register")
                }
                Spacer(modifier = Modifier.height(16.dp))

                Text(text = "Already Registered?",
                    modifier = Modifier
                        .clickable {
                            navController?.popBackStack()
                        }
                )
            }
        }
    }
}
