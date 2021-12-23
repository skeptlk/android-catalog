package com.example.thecatalog.models

data class CatalogCategory(
    var title: String,
    var items: MutableList<CatalogSubCategory>
)
