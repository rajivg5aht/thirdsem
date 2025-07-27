//package com.example.ai36.view
//
//import android.app.Activity
//import android.content.Intent
//import android.os.Bundle
//import android.widget.Toast
//import androidx.activity.ComponentActivity
//import androidx.activity.compose.setContent
//import androidx.compose.foundation.layout.Arrangement
//import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.Row
//import androidx.compose.foundation.layout.fillMaxWidth
//import androidx.compose.foundation.layout.padding
//import androidx.compose.foundation.lazy.LazyColumn
//import androidx.compose.material.icons.Icons
//import androidx.compose.material.icons.filled.Add
//import androidx.compose.material.icons.filled.Delete
//import androidx.compose.material.icons.filled.Edit
//import androidx.compose.material3.Card
//import androidx.compose.material3.FloatingActionButton
//import androidx.compose.material3.Icon
//import androidx.compose.material3.IconButton
//import androidx.compose.material3.IconButtonDefaults
//import androidx.compose.material3.Scaffold
//import androidx.compose.material3.Text
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.LaunchedEffect
//import androidx.compose.runtime.livedata.observeAsState
//import androidx.compose.runtime.remember
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.platform.LocalContext
//import androidx.compose.ui.tooling.preview.Preview
//import androidx.compose.ui.unit.dp
//import androidx.core.view.WindowCompat
//import com.example.ai36.repository.ProductRepositoryImpl
//import com.example.ai36.viewmodel.ProductViewModel
//
//class DashboardActivity : ComponentActivity() {
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//
//        // Proper edge-to-edge enabling
//        WindowCompat.setDecorFitsSystemWindows(window, false)
//
//        setContent {
//            DashboardBody()
//        }
//    }
//}
//
//@Composable
//fun DashboardBody() {
//    val context = LocalContext.current
//    val activity = context as? Activity
//
//    val repo = remember { ProductRepositoryImpl() }
//    val viewModel = remember { ProductViewModel(repo) }
//
//    val products = viewModel.allProducts
//        .observeAsState(initial = emptyList())
//
//    LaunchedEffect(Unit) {
//        viewModel.getAllProducts()
//    }
//
//    Scaffold(
//        floatingActionButton = {
//            FloatingActionButton(onClick = {
//                val intent = Intent(context, AddProductActivity::class.java)
//                context.startActivity(intent)
//            }) {
//                Icon(Icons.Default.Add, contentDescription = null)
//            }
//        }
//    ) { innerPadding ->
//        LazyColumn(modifier = Modifier.padding(innerPadding)) {
//            items(products.value.size) { index ->
//                val eachProduct = products.value[index]
//                Card(modifier = Modifier.fillMaxWidth().padding(15.dp)) {
//                    Column(modifier = Modifier.padding(15.dp)) {
//                        Text("${eachProduct?.productName}")
//                        Text("${eachProduct?.productPrice}")
//                        Text("${eachProduct?.productDescription}")
//                        Row(
//                            modifier = Modifier.fillMaxWidth(),
//                            horizontalArrangement = Arrangement.End
//                        ) {
//                            IconButton(
//                                onClick = {
//                                    val intent = Intent(context, UpdateProductActivity::class.java)
//                                    intent.putExtra("productId", "${eachProduct?.productId}")
//                                    context.startActivity(intent)
//                                },
//                                colors = IconButtonDefaults.iconButtonColors(
//                                    contentColor = Color.Gray
//                                )
//                            ) {
//                                Icon(Icons.Default.Edit, contentDescription = null)
//                            }
//
//                            IconButton(
//                                onClick = {
//                                    viewModel.deleteProduct(eachProduct?.productId.toString()) { success, message ->
//                                        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
//                                    }
//                                },
//                                colors = IconButtonDefaults.iconButtonColors(
//                                    contentColor = Color.Red
//                                )
//                            ) {
//                                Icon(Icons.Default.Delete, contentDescription = "DeleteProduct")
//                            }
//                        }
//                    }
//                }
//            }
//        }
//    }
//}
//
//@Preview
//@Composable
//fun PreDash() {
//    DashboardBody()
//}


//
//package com.example.ai36.view
//
//import android.app.Activity
//import android.content.Intent
//import android.os.Bundle
//import android.widget.Toast
//import androidx.activity.ComponentActivity
//import androidx.activity.compose.setContent
//import androidx.activity.enableEdgeToEdge
//import androidx.compose.foundation.background
//import androidx.compose.foundation.layout.*
//import androidx.compose.foundation.lazy.LazyColumn
//import androidx.compose.foundation.lazy.items
//import androidx.compose.material.icons.Icons
//import androidx.compose.material.icons.filled.*
//import androidx.compose.material3.*
//import androidx.compose.runtime.*
//import androidx.compose.runtime.livedata.observeAsState
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.graphics.Color.Companion.Green
//import androidx.compose.ui.platform.LocalContext
//import androidx.compose.ui.unit.dp
//import androidx.lifecycle.ViewModelProvider
////import com.example.ai36.LoginActivity
//import com.example.ai36.repository.CartRepositoryImpl
//import com.example.ai36.repository.ProductRepositoryImpl
//import com.example.ai36.viewmodel.CartViewModel
//import com.example.ai36.viewmodel.CartViewModelFactory
//import com.example.ai36.viewmodel.ProductViewModel
//
//class DashboardActivity : ComponentActivity() {
//    private lateinit var cartViewModel: CartViewModel
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()
//
//        val cartRepo = CartRepositoryImpl()
//        val cartFactory = CartViewModelFactory(cartRepo)
//        cartViewModel = ViewModelProvider(this, cartFactory)[CartViewModel::class.java]
//
//        setContent {
//            DashboardBody(cartViewModel = cartViewModel)
//        }
//    }
//}
//
//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun DashboardBody(cartViewModel: CartViewModel) {
//    val context = LocalContext.current
//    val activity = context as? Activity
//
//    val repo = remember { ProductRepositoryImpl() }
//    val viewModel = remember { ProductViewModel(repo) }
//
//    val products = viewModel.allProducts.observeAsState(initial = emptyList())
//    val loading = viewModel.loading.observeAsState(initial = true)
//
//    LaunchedEffect(Unit) {
//        viewModel.getAllProducts()
//    }
//
//    var selectedTab by remember { mutableStateOf(0) }
//
//    Scaffold(
//        topBar = {
//            TopAppBar(
//                title = { Text("Admin Dashboard", color = Color.White) },
//                actions = {
//                    IconButton(onClick = {
//                        // If using Firebase: FirebaseAuth.getInstance().signOut()
//                        val intent = Intent(context, LoginActivity::class.java)
//                        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
//                        context.startActivity(intent)
//                        Toast.makeText(context, "Logged out successfully", Toast.LENGTH_SHORT).show()
//                    }) {
//                        Icon(Icons.Default.ExitToApp, contentDescription = "Logout", tint = Color.White)
//                    }
//                },
//                colors = TopAppBarDefaults.topAppBarColors(containerColor = Green)
//            )
//        },
//        floatingActionButton = {
//            FloatingActionButton(onClick = {
//                val intent = Intent(context, AddProductActivity::class.java)
//                context.startActivity(intent)
//            }) {
//                Icon(Icons.Default.Add, contentDescription = "Add Product")
//            }
//        },
//        bottomBar = {
//            NavigationBar(containerColor = Green) {
//                NavigationBarItem(
//                    selected = selectedTab == 0,
//                    onClick = { selectedTab = 0 },
//                    icon = { Icon(Icons.Default.Home, contentDescription = "Home") },
//                    label = { Text("Home") }
//                )
//                NavigationBarItem(
//                    selected = selectedTab == 1,
//                    onClick = {
//                        selectedTab = 1
//                        context.startActivity(Intent(context, OrderActivity::class.java))
//                    },
//                    icon = { Icon(Icons.Default.List, contentDescription = "Orders") },
//                    label = { Text("Orders") }
//                )
//            }
//        }
//    ) { innerPadding ->
//        if (selectedTab == 0) {
//            LazyColumn(
//                modifier = Modifier
//                    .padding(innerPadding)
//                    .background(color = Green)
//            ) {
//                if (loading.value) {
//                    item {
//                        Text(
//                            text = "Loading products...",
//                            color = Color.White,
//                            modifier = Modifier.padding(16.dp)
//                        )
//                    }
//                } else {
//                    items(products.value) { eachProduct ->
//                        Card(
//                            modifier = Modifier
//                                .fillMaxWidth()
//                                .padding(15.dp)
//                        ) {
//                            Column(modifier = Modifier.padding(15.dp)) {
//                                Text(text = eachProduct?.productName ?: "No Name")
//                                Text(text = "Rs. ${eachProduct?.productPrice ?: 0}")
//                                Text(text = eachProduct?.productDescription ?: "")
//
//                                Row(
//                                    modifier = Modifier.fillMaxWidth(),
//                                    horizontalArrangement = Arrangement.End
//                                ) {
//                                    IconButton(
//                                        onClick = {
//                                            val intent = Intent(context, UpdateProductActivity::class.java)
//                                            intent.putExtra("productId", eachProduct?.productId ?: "")
//                                            context.startActivity(intent)
//                                        },
//                                        colors = IconButtonDefaults.iconButtonColors(
//                                            contentColor = Color.Black
//                                        )
//                                    ) {
//                                        Icon(Icons.Default.Edit, contentDescription = "Edit Product")
//                                    }
//
//                                    IconButton(
//                                        onClick = {
//                                            viewModel.deleteProduct(eachProduct?.productId.toString()) { success, message ->
//                                                Toast.makeText(context, message, Toast.LENGTH_LONG).show()
//                                            }
//                                        },
//                                        colors = IconButtonDefaults.iconButtonColors(
//                                            contentColor = Color.Red
//                                        )
//                                    ) {
//                                        Icon(Icons.Default.Delete, contentDescription = "Delete Product")
//                                    }
//                                }
//                            }
//                        }
//                    }
//                }
//            }
//        }
//    }
//}

package com.example.ai36.view

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
//import com.example.ai36.LoginActivity
import com.example.ai36.repository.CartRepositoryImpl
import com.example.ai36.repository.ProductRepositoryImpl
import com.example.ai36.viewmodel.CartViewModel
import com.example.ai36.viewmodel.CartViewModelFactory
import com.example.ai36.viewmodel.ProductViewModel

class DashboardActivity : ComponentActivity() {
    private lateinit var cartViewModel: CartViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val cartRepo = CartRepositoryImpl()
        val cartFactory = CartViewModelFactory(cartRepo)
        cartViewModel = ViewModelProvider(this, cartFactory)[CartViewModel::class.java]

        setContent {
            DashboardBody(cartViewModel = cartViewModel)
        }
    }
}

private val DB4444 = Color(0xFFDB4444)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardBody(cartViewModel: CartViewModel) {
    val context = LocalContext.current
    val activity = context as? Activity

    val repo = remember { ProductRepositoryImpl() }
    val viewModel = remember { ProductViewModel(repo) }

    val products = viewModel.allProducts.observeAsState(initial = emptyList())
    val loading = viewModel.loading.observeAsState(initial = true)

    LaunchedEffect(Unit) {
        viewModel.getAllProducts()
    }

    var selectedTab by remember { mutableStateOf(0) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Admin Dashboard", color = Color.White) },
                actions = {
                    IconButton(onClick = {
                        // If using Firebase: FirebaseAuth.getInstance().signOut()
                        val intent = Intent(context, LoginActivity::class.java)
                        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                        context.startActivity(intent)
                        Toast.makeText(context, "Logged out successfully", Toast.LENGTH_SHORT).show()
                    }) {
                        Icon(Icons.Default.ExitToApp, contentDescription = "Logout", tint = Color.White)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = DB4444)
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = {
                val intent = Intent(context, AddProductActivity::class.java)
                context.startActivity(intent)
            }) {
                Icon(Icons.Default.Add, contentDescription = "Add Product")
            }
        },
        bottomBar = {
            NavigationBar(containerColor = DB4444) {
                NavigationBarItem(
                    selected = selectedTab == 0,
                    onClick = { selectedTab = 0 },
                    icon = { Icon(Icons.Default.Home, contentDescription = "Home") },
                    label = { Text("Home") }
                )
                NavigationBarItem(
                    selected = selectedTab == 1,
                    onClick = {
                        selectedTab = 1
                        context.startActivity(Intent(context, OrderActivity::class.java))
                    },
                    icon = { Icon(Icons.Default.List, contentDescription = "Orders") },
                    label = { Text("Orders") }
                )
            }
        }
    ) { innerPadding ->
        if (selectedTab == 0) {
            LazyColumn(
                modifier = Modifier
                    .padding(innerPadding)
                    .background(color = DB4444)
            ) {
                if (loading.value) {
                    item {
                        Text(
                            text = "Loading products...",
                            color = Color.White,
                            modifier = Modifier.padding(16.dp)
                        )
                    }
                } else {
                    items(products.value) { eachProduct ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(15.dp)
                        ) {
                            Column(modifier = Modifier.padding(15.dp)) {
                                Text(text = eachProduct?.productName ?: "No Name")
                                Text(text = "Rs. ${eachProduct?.productPrice ?: 0}")
                                Text(text = eachProduct?.productDescription ?: "")

                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.End
                                ) {
                                    IconButton(
                                        onClick = {
                                            val intent = Intent(context, UpdateProductActivity::class.java)
                                            intent.putExtra("productId", eachProduct?.productId ?: "")
                                            context.startActivity(intent)
                                        },
                                        colors = IconButtonDefaults.iconButtonColors(
                                            contentColor = Color.Black
                                        )
                                    ) {
                                        Icon(Icons.Default.Edit, contentDescription = "Edit Product")
                                    }

                                    IconButton(
                                        onClick = {
                                            viewModel.deleteProduct(eachProduct?.productId.toString()) { success, message ->
                                                Toast.makeText(context, message, Toast.LENGTH_LONG).show()
                                            }
                                        },
                                        colors = IconButtonDefaults.iconButtonColors(
                                            contentColor = Color.Red
                                        )
                                    ) {
                                        Icon(Icons.Default.Delete, contentDescription = "Delete Product")
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
