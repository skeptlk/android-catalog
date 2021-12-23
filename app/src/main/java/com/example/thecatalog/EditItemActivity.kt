package com.example.thecatalog

import com.example.thecatalog.models.CatalogItem
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import com.example.thecatalog.models.CatalogItemDTO
import kotlinx.android.synthetic.main.activity_catalog_single_edit.*
import kotlinx.serialization.builtins.serializer
import kotlinx.serialization.json.Json
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString


class EditItemActivity: AppCompatActivity() {
    var itemDTO: CatalogItemDTO? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_catalog_single_edit)

        itemDTO = Json.decodeFromString<CatalogItemDTO>(intent.extras?.get("CATALOG_ITEM_DTO") as String)
        val item = itemDTO?.catalogItem

        etTitle.setText(item!!.title, TextView.BufferType.EDITABLE)
        etDescription.setText(item.description, TextView.BufferType.EDITABLE)
        etManufactureDate.setText(item.manufactureDate, TextView.BufferType.EDITABLE)
        etPrice.setText(item.price.toString(), TextView.BufferType.EDITABLE)

        btnSave.setOnClickListener { _ ->
            if (
                etTitle.text.toString().isNotEmpty() &&
                etDescription.text.toString().isNotEmpty() &&
                etManufactureDate.text.toString().isNotEmpty() &&
                etPrice.text.toString().isNotEmpty()
            ) {
                val intent = Intent()

                itemDTO?.catalogItem?.apply {
                    title = etTitle.text.toString()
                    description = etDescription.text.toString()
                    manufactureDate = etManufactureDate.text.toString()
                    price = etPrice.text.toString().toInt()
                };

                intent.putExtra("UPDATED_CATALOG_ITEM", Json.encodeToString(itemDTO))
                setResult(RESULT_OK, intent)
                finish()

            } else {
                Toast.makeText(this, R.string.error_fill_all_fields, Toast.LENGTH_SHORT).show()
            }
        }
    }
}