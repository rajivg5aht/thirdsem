//package com.example.ai36.view
//
//import android.app.Activity
//import android.content.Intent
//import android.os.Bundle
//import android.widget.Toast
//import androidx.activity.ComponentActivity
//import androidx.activity.compose.setContent
//import androidx.activity.enableEdgeToEdge
//import androidx.compose.foundation.Image
//import androidx.compose.foundation.background
//import androidx.compose.foundation.clickable
//import androidx.compose.foundation.layout.Arrangement
//import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.Row
//import androidx.compose.foundation.layout.Spacer
//import androidx.compose.foundation.layout.fillMaxSize
//import androidx.compose.foundation.layout.fillMaxWidth
//import androidx.compose.foundation.layout.height
//import androidx.compose.foundation.layout.padding
//import androidx.compose.foundation.layout.width
//import androidx.compose.foundation.shape.RoundedCornerShape
//import androidx.compose.foundation.text.KeyboardOptions
//import androidx.compose.material.icons.Icons
//import androidx.compose.material.icons.filled.Lock
//import androidx.compose.material.icons.filled.Person
//import androidx.compose.material3.Button
//import androidx.compose.material3.ButtonDefaults
//import androidx.compose.material3.Checkbox
//import androidx.compose.material3.CheckboxDefaults
//import androidx.compose.material3.Icon
//import androidx.compose.material3.OutlinedTextField
//import androidx.compose.material3.Scaffold
//import androidx.compose.material3.SnackbarHost
//import androidx.compose.material3.SnackbarHostState
//import androidx.compose.material3.Text
//import androidx.compose.material3.TextFieldDefaults
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.getValue
//import androidx.compose.runtime.mutableStateOf
//import androidx.compose.runtime.remember
//import androidx.compose.runtime.rememberCoroutineScope
//import androidx.compose.runtime.setValue
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.platform.LocalContext
//import androidx.compose.ui.res.painterResource
//import androidx.compose.ui.text.input.KeyboardType
//import androidx.compose.ui.text.input.PasswordVisualTransformation
//import androidx.compose.ui.text.input.VisualTransformation
//import androidx.compose.ui.tooling.preview.Preview
//import androidx.compose.ui.unit.dp
//import com.example.ai36.R
//
//class LoginActivity : ComponentActivity() {
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()
//        setContent {
//            LoginBody()
//        }
//    }
//}
//
//@Composable
//fun LoginBody() {
//    var email by remember { mutableStateOf("") }
//    var password by remember { mutableStateOf("") }
//    var passwordVisibility by remember { mutableStateOf(false) }
//    var rememberMe by remember { mutableStateOf(false) }
//
//    val context = LocalContext.current
//    val activity = context as? Activity
//    val coroutineScope = rememberCoroutineScope()
//    val snackBarHostScope = remember { SnackbarHostState() }
//
//    Scaffold(
//        snackbarHost = { SnackbarHost(hostState = snackBarHostScope) }
//    ) { innerPadding ->
//        Column(
//            modifier = Modifier
//                .padding(innerPadding)
//                .fillMaxSize()
//                .background(color = Color.White),
//            horizontalAlignment = Alignment.CenterHorizontally
//        ) {
//            Spacer(modifier = Modifier.height(40.dp))
//
//            Image(
//                painter = painterResource(R.drawable.moto),
//                contentDescription = null,
//                modifier = Modifier
//                    .height(250.dp)
//                    .width(250.dp)
//            )
//
//            Spacer(modifier = Modifier.height(30.dp))
//
//            // Email field
//            OutlinedTextField(
//                value = email,
//                onValueChange = { email = it },
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(horizontal = 10.dp),
//                shape = RoundedCornerShape(12.dp),
//                prefix = {
//                    Icon(imageVector = Icons.Default.Person, contentDescription = null)
//                },
//                placeholder = {
//                    Text("abc@gmail.com")
//                },
//                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
//                colors = TextFieldDefaults.colors(
//                    focusedContainerColor = Color.Gray.copy(0.2f),
//                    unfocusedContainerColor = Color.Gray.copy(0.2f)
//                )
//            )
//
//            Spacer(modifier = Modifier.height(20.dp))
//
//            // Password field
//            OutlinedTextField(
//                value = password,
//                onValueChange = { password = it },
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(horizontal = 10.dp),
//                shape = RoundedCornerShape(12.dp),
//                prefix = {
//                    Icon(imageVector = Icons.Default.Lock, contentDescription = null)
//                },
//                suffix = {
//                    Icon(
//                        painter = painterResource(
//                            if (passwordVisibility) R.drawable.baseline_visibility_off_24 else R.drawable.baseline_visibility_24
//                        ),
//                        contentDescription = null,
//                        modifier = Modifier.clickable {
//                            passwordVisibility = !passwordVisibility
//                        }
//                    )
//                },
//                visualTransformation = if (passwordVisibility) VisualTransformation.None else PasswordVisualTransformation(),
//                placeholder = {
//                    Text("*******")
//                },
//                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
//                colors = TextFieldDefaults.colors(
//                    focusedContainerColor = Color.Gray.copy(0.2f),
//                    unfocusedContainerColor = Color.Gray.copy(0.2f)
//                )
//            )
//
//            // Remember me & forget password row
//            Row(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(horizontal = 10.dp),
//                verticalAlignment = Alignment.CenterVertically,
//                horizontalArrangement = Arrangement.SpaceBetween
//            ) {
//                Row(verticalAlignment = Alignment.CenterVertically) {
//                    Checkbox(
//                        checked = rememberMe,
//                        onCheckedChange = { rememberMe = it },
//                        colors = CheckboxDefaults.colors(
//                            checkedColor = Color.Green,
//                            checkmarkColor = Color.White
//                        )
//                    )
//                    Text("Remember me")
//                }
//
//                Text(
//                    "Forget Password?",
//                    modifier = Modifier.clickable {
//                        // TODO: Handle forget password
//                    }
//                )
//            }
//
//            Spacer(modifier = Modifier.height(20.dp))
//
//            // Login Button
//            Button(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(horizontal = 20.dp),
//                shape = RoundedCornerShape(12.dp),
//                colors = ButtonDefaults.buttonColors(containerColor = Color.Gray),
//                onClick = {
//                    val intent = Intent(context, DashboardActivity::class.java)
//                    intent.putExtra("email", email)
//                    intent.putExtra("password", password)
//                    context.startActivity(intent)
//                    activity?.finish()
//
//                    Toast.makeText(context, "Login Success", Toast.LENGTH_SHORT).show()
//                }
//            ) {
//                Text("Login")
//            }
//
//            Text(
//                "Don't have an account? Signup",
//                modifier = Modifier.clickable {
//                    val intent = Intent(context, RegistrationActivity::class.java)
//                    context.startActivity(intent)
//                    activity?.finish()
//                }
//            )
//        }
//    }
//}
//
//@Preview
//@Composable
//fun PreviewLogin() {
//    LoginBody()
//}

package com.example.ai36.view

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ai36.repository.UserRepositoryImpl
import com.example.ai36.viewmodel.UserViewModel
import kotlinx.coroutines.launch

class LoginActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LoginBody()
        }
    }
}

@Composable
fun LoginBody() {
    val repo = remember { UserRepositoryImpl() }
    val userViewModel = remember { UserViewModel(repo) }

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisibility by remember { mutableStateOf(false) }
    var rememberMe by remember { mutableStateOf(false) }

    val context = LocalContext.current
    val activity = context as Activity

    val sharedPreferences = context.getSharedPreferences("User", Context.MODE_PRIVATE)
    val editor = sharedPreferences.edit()

    val coroutineScope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(color = Color.White),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(50.dp))
            Text(
                text = "Helmets and Gears",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 10.dp),
                textAlign = TextAlign.Center
            )

            // Replace this with R.drawable.moto when added to your drawable folder
            Image(
                painter = painterResource(android.R.drawable.ic_menu_camera),
                contentDescription = null
            )

            Spacer(modifier = Modifier.height(40.dp))

            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 15.dp),
                placeholder = { Text(text = "Enter email") },
                shape = RoundedCornerShape(12.dp),
                prefix = { Icon(Icons.Default.Email, contentDescription = null) },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
            )

            Spacer(modifier = Modifier.height(20.dp))

            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 15.dp),
                placeholder = { Text(text = "Enter password") },
                shape = RoundedCornerShape(12.dp),
                prefix = { Icon(Icons.Default.Lock, contentDescription = null) },
                suffix = {
                    Icon(
                        painter = painterResource(
                            if (passwordVisibility)
                                android.R.drawable.presence_online
                            else
                                android.R.drawable.presence_offline
                        ),
                        contentDescription = null,
                        modifier = Modifier.clickable { passwordVisibility = !passwordVisibility }
                    )
                },
                visualTransformation = if (passwordVisibility) VisualTransformation.None else PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
            )

            Spacer(modifier = Modifier.height(10.dp))

            Row(
                modifier = Modifier
                    .padding(horizontal = 12.dp)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Checkbox(
                        checked = rememberMe,
                        onCheckedChange = { rememberMe = it }
                    )
                    Text("Remember me")
                }

                Text(
                    text = "Forget Password?",
                    color = Color.Blue,
                    modifier = Modifier.clickable {
                        val intent = Intent(context, ForgetPasswordActivity::class.java)
                        context.startActivity(intent)
                        activity.finish()
                    }
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            Button(
                onClick = {
                    userViewModel.login(email, password) { success, message ->
                        if (success) {
                            Toast.makeText(context, message, Toast.LENGTH_LONG).show()

                            if (email == "admin@gmail.com") {
                                val intent = Intent(context, DashboardActivity::class.java)
                                context.startActivity(intent)
                            } else {
                                val intent = Intent(context, UserDashboardActivity::class.java)
                                context.startActivity(intent)
                            }

                            if (rememberMe) {
                                editor.putString("email", email)
                                editor.putString("password", password)
                                editor.apply()
                            }

                            activity.finish()
                        } else {
                            coroutineScope.launch {
                                snackbarHostState.showSnackbar("Invalid login: $message")
                            }
                        }
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp),
                shape = RoundedCornerShape(10.dp)
            ) {
                Text("Login")
            }

            Text(
                "Don't have an account, Signup",
                modifier = Modifier
                    .padding(top = 16.dp)
                    .clickable {
                        val intent = Intent(context, RegistrationActivity::class.java)
                        context.startActivity(intent)
                        activity.finish()
                    }
            )
        }
    }
}
