package com.uam.navegacionobject.navigacion

import com.uam.incrementovm.model.User
import kotlinx.serialization.Serializable

@Serializable
object Login

@Serializable
object ListUser

@Serializable
data class Detail(val id : Int)