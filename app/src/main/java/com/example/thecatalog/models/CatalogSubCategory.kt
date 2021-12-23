package com.example.thecatalog.models

data class CatalogSubCategory(
    var title: String,
    var items: MutableList<CatalogItem>
)
