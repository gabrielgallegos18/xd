package com.uam.incrementovm.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize
import kotlinx.serialization.Serializable


@Serializable
@Parcelize
@Entity(tableName = "users")
data class User(@PrimaryKey val id: Int,
                val nombre: String,
                val apellido: String,
                val email: String
                ) : Parcelable
