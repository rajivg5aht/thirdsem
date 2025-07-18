package com.example.ai36.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.ai36.model.UserModel
import com.example.ai36.repository.UserRepository
import com.google.firebase.auth.FirebaseUser

class UserViewModel(val repo: UserRepository) : ViewModel(){

    fun login(
        email: String,
        password: String, callback: (Boolean, String) -> Unit
    ){
        repo.login(email,password,callback)
    }

    //authentication ko function
    fun register(
        email: String, password: String,
        callback: (Boolean, String, String) -> Unit
    ){
        repo.register(email,password,callback)
    }

    //database ko function
    fun addUserToDatabase(
        userId: String, model: UserModel,
        callback: (Boolean, String) -> Unit
    ){
       repo.addUserToDatabase(userId,model,callback)
    }

    fun forgetPassword(
        email: String,
        callback: (Boolean, String) -> Unit
    ){
        repo.forgetPassword(email,callback)
    }

    fun deleteAccount(
        userId: String,
        callback: (Boolean, String) -> Unit
    ){
        repo.deleteAccount(userId,callback)
    }

    fun editProfile(
        userId: String, data: MutableMap<String, Any>,
        callback: (Boolean, String) -> Unit
    ){
        repo.editProfile(userId,data,callback)
    }

    fun getCurrentUser(): FirebaseUser?{
        return repo.getCurrentUser()
    }

    private val _users = MutableLiveData<UserModel?>()
    val users : LiveData<UserModel?> get() = _users
    fun getUserById(
        userId: String,
    ){
        repo.getUserById(userId){
            success,message,data->
            if(success){
                _users.postValue(data)
            }else{
                _users.postValue(null)

            }
        }
    }

    fun logout(callback: (Boolean, String) -> Unit){
        repo.logout(callback)
    }

}