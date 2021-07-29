package com.example.nasaapp.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Response constructor(
    @PrimaryKey var id: Int = -1,
    val copyright: String? = null,
    var date: String? = null,
    val explanation: String? = null,
    var hdurl: String? = null,
    val media_type: String? = null,
    val service_version: String? = null,
    val title: String? = null) {
}



