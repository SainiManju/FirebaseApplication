package com.example.firebase_application

data class User(
    val name: String?,
    val email: String?,
    val password: String?,
    val phoneNumber: String?,
    val description: String?, ){
    constructor():this("","","","","")
}
