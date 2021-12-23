package com.example.thecatalog.models
import kotlinx.serialization.Serializable

@Serializable
data class CatalogItemDTO(
    val catalogItem: CatalogItem,
    val catalogItemIndex: Int,
    val categoryTitle: String,
    val subCategoryTitle: String,
    val isNew: Boolean = false
)
