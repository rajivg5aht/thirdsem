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
import com.example.ai36.R

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

            // ✅ logo.png from drawable
            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = null,
                modifier = Modifier
                    .height(160.dp)
                    .padding(16.dp)
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
                                context.startActivity(Intent(context, DashboardActivity::class.java))
                            } else {
                                context.startActivity(Intent(context, UserDashboardActivity::class.java))
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
                        context.startActivity(Intent(context, RegistrationActivity::class.java))
                        activity.finish()
                    }
            )
        }
    }
}

