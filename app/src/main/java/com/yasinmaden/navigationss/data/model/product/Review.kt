package com.yasinmaden.navigationss.data.model.product

data class Review(
    val comment: String = "",
    val date: String = "",
    val rating: Int = 0,
    val reviewerEmail: String = "",
    val reviewerName: String = ""
)