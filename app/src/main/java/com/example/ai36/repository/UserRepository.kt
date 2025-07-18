package com.example.ai36.repository

import com.example.ai36.model.UserModel
import com.google.firebase.auth.FirebaseUser

interface UserRepository {
//    {
//     "success":false,
//    "message":"Login failed",
//    "userId":"10000045412ew"
//    }

    fun login(
        email: String,
        password: String, callback: (Boolean, String) -> Unit
    )

    //authentication ko function
    fun register(
        email: String, password: String,
        callback: (Boolean, String, String) -> Unit
    )

    //database ko function
    fun addUserToDatabase(
        userId: String, model: UserModel,
        callback: (Boolean, String) -> Unit
    )

    fun forgetPassword(
        email: String,
        callback: (Boolean, String) -> Unit
    )

    fun deleteAccount(
        userId: String,
        callback: (Boolean, String) -> Unit
    )

    fun editProfile(
        userId: String, data: MutableMap<String, Any>,
        callback: (Boolean, String) -> Unit
    )

    fun getCurrentUser(): FirebaseUser?

    fun getUserById(
        userId: String,
        callback: (Boolean, String, UserModel?) -> Unit
    )

    fun logout(callback: (Boolean, String) -> Unit)

}