package com.example.thecatalog.models

import kotlinx.serialization.Serializable

@Serializable
data class CatalogItem(
    var title: String,
    var description: String,
    var manufactureDate: String,
    var price: Int
)
