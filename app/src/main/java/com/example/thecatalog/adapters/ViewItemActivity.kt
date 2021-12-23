package com.example.thecatalog.adapters

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.thecatalog.R
import com.example.thecatalog.models.CatalogItemDTO
import kotlinx.android.synthetic.main.activity_catalog_single_view.*
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

class ViewItemActivity : AppCompatActivity() {
    var itemDTO: CatalogItemDTO? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_catalog_single_view)

        itemDTO = Json.decodeFromString<CatalogItemDTO>(intent.extras?.get("CATALOG_ITEM_DTO") as String)
        val item = itemDTO?.catalogItem

        if (item != null) {
            tvTitle.text = item.title + " (" + itemDTO?.categoryTitle + ")";
            tvDescription.text = item.description
            tvPrice.text = item.price.toString()
        }

    }

}