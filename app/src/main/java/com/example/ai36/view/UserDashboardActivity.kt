package com.example.ai36.view
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import coil.compose.AsyncImage
import com.cloudinary.android.MediaManager
import com.example.ai36.R
import com.example.ai36.model.CartItemModel
import com.example.ai36.model.ProductModel
import com.example.ai36.model.WishlistItemModel
import com.example.ai36.repository.*
import com.example.ai36.viewmodel.*

class UserDashboardActivity : ComponentActivity() {
    private lateinit var cartViewModel: CartViewModel
    private lateinit var wishlistViewModel: WishlistViewModel
    private lateinit var userViewModel: UserViewModel
    private lateinit var orderViewModel: OrderViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        MediaManager.init(this)

        cartViewModel = ViewModelProvider(this, CartViewModelFactory(CartRepositoryImpl()))[CartViewModel::class.java]
        wishlistViewModel = ViewModelProvider(this, WishlistViewModelFactory(WishlistRepositoryImpl))[WishlistViewModel::class.java]
        userViewModel = ViewModelProvider(this, UserViewModelFactory(UserRepositoryImpl()))[UserViewModel::class.java]
        orderViewModel = ViewModelProvider(this, OrderViewModelFactory(OrderRepositoryImpl()))[OrderViewModel::class.java]

        setContent {
            UserDashboardBody(cartViewModel, wishlistViewModel, userViewModel, orderViewModel)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserDashboardBody(
    cartViewModel: CartViewModel,
    wishlistViewModel: WishlistViewModel,
    userViewModel: UserViewModel,
    orderViewModel: OrderViewModel
) {
    val context = LocalContext.current
    val productViewModel = remember { ProductViewModel(ProductRepositoryImpl()) }
    val user by userViewModel.user.observeAsState()
    val filteredProducts by productViewModel.filteredProducts.observeAsState(emptyList())
    val loading by productViewModel.loading.observeAsState(true)

    var menuExpanded by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        productViewModel.getAllProducts()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Image(
                            painter = painterResource(id = R.drawable.logo),
                            contentDescription = "Logo",
                            modifier = Modifier.size(55.dp).clip(CircleShape)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Helmets and Gears")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFFDB4444),
                    titleContentColor = Color.White
                ),
                actions = {
                    IconButton(onClick = {
                        context.startActivity(Intent(context, EditProfileActivity::class.java))
                    }) {
                        Icon(Icons.Default.Person, contentDescription = "Edit Profile", tint = Color.White)
                    }

                    // Dropdown menu icon and menu
                    Box {
                        IconButton(onClick = { menuExpanded = true }) {
                            Icon(Icons.Default.MoreVert, contentDescription = "Menu", tint = Color.White)
                        }
                        DropdownMenu(
                            expanded = menuExpanded,
                            onDismissRequest = { menuExpanded = false }
                        ) {
                            DropdownMenuItem(
                                text = { Text("Address Book") },
                                onClick = {
                                    menuExpanded = false
                                    context.startActivity(Intent(context, AddressActivity::class.java))
                                }
                            )
                            DropdownMenuItem(
                                text = { Text("Logout") },
                                onClick = {
                                    menuExpanded = false
                                    Toast.makeText(context, "Logged out", Toast.LENGTH_SHORT).show()
                                    val intent = Intent(context, LoginActivity::class.java)
                                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                                    context.startActivity(intent)
                                }
                            )
                        }
                    }
                }
            )
        },
        bottomBar = {
            NavigationBar(containerColor = Color(0xFFDB4444)) {
                NavigationBarItem(
                    selected = true,
                    onClick = {},
                    icon = { Icon(Icons.Default.Home, contentDescription = "Home", tint = Color.White) },
                    label = { Text("Home", color = Color.White) }
                )
                NavigationBarItem(
                    selected = false,
                    onClick = { context.startActivity(Intent(context, CartActivity::class.java)) },
                    icon = { Icon(Icons.Default.ShoppingCart, contentDescription = "Cart", tint = Color.White) },
                    label = { Text("Cart", color = Color.White) }
                )
                NavigationBarItem(
                    selected = false,
                    onClick = { context.startActivity(Intent(context, WishlistActivity::class.java)) },
                    icon = { Icon(Icons.Default.FavoriteBorder, contentDescription = "Wishlist", tint = Color.White) },
                    label = { Text("Wishlist", color = Color.White) }
                )
            }
        }
    ) { padding ->
        Column(
            Modifier
                .padding(padding)
                .fillMaxSize()
        ) {
            UserHeader(user = user)

            if (loading) {
                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            } else {
                LazyColumn(modifier = Modifier.padding(8.dp)) {
                    items(filteredProducts) { product ->
                        product?.let {
                            ProductCard(it, cartViewModel, wishlistViewModel, context)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun UserHeader(user: com.example.ai36.model.UserModel?) {
    Row(modifier = Modifier.padding(12.dp)) {
        Text(
            text = "Welcome, ${user?.firstName ?: "User"}!",
            style = MaterialTheme.typography.titleLarge
        )
    }
}

@Composable
fun ProductCard(
    product: ProductModel,
    cartViewModel: CartViewModel,
    wishlistViewModel: WishlistViewModel,
    context: android.content.Context
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFFDB4444)
        ),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            AsyncImage(
                model = product.image,
                contentDescription = product.productName,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp),
                contentScale = ContentScale.Crop,
                error = painterResource(id = R.drawable.logo)
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(text = product.productName ?: "Unnamed", color = Color.White, style = MaterialTheme.typography.titleMedium)
            Text(text = "Rs. ${product.productPrice ?: 0}", color = Color.White, style = MaterialTheme.typography.bodyLarge)

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Button(
                    onClick = {
                        val cartItem = CartItemModel(
                            productId = product.productId ?: "",
                            productName = product.productName ?: "",
                            productPrice = product.productPrice ?: 0.0,
                            image = product.image ?: "",
                            quantity = 1
                        )
                        cartViewModel.addToCart(cartItem)
                        Toast.makeText(context, "Added to cart", Toast.LENGTH_SHORT).show()
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.White,
                        contentColor = Color(0xFFDB4444)
                    )
                ) {
                    Text("Add to Cart")
                }

                IconButton(onClick = {
                    val wishlistItem = WishlistItemModel(
                        productName = product.productName ?: "",
                        productPrice = product.productPrice ?: 0.0,
                        image = product.image ?: ""
                    )
                    wishlistViewModel.addToWishlist(wishlistItem)
                    Toast.makeText(context, "Added to wishlist", Toast.LENGTH_SHORT).show()
                }) {
                    Icon(Icons.Default.FavoriteBorder, contentDescription = "Wishlist", tint = Color.White)
                }
            }
        }
    }
}


