package com.tanvir.dogbreedapp.service

import com.squareup.moshi.Json


data class ResultsResponse<Any>(
    @field:Json(name= "message")
    val message: Any,
    @field:Json(name="status")
    val status: String
)