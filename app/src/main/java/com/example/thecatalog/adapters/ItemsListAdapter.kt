package com.example.thecatalog.adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.activity.result.ActivityResultLauncher
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.thecatalog.EditItemActivity
import com.example.thecatalog.R
import com.example.thecatalog.models.CatalogItem
import com.example.thecatalog.models.CatalogItemDTO
import kotlinx.android.synthetic.main.activity_main.view.*
import kotlinx.android.synthetic.main.item_layout.view.*
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class ItemsListAdapter(
    private val items: MutableList<CatalogItem>,
    private val resultLauncher: ActivityResultLauncher<Intent>?,
    private val category: String,
    private val subCategory: String,
    private val isEditMode: Boolean
): RecyclerView.Adapter<ItemsListAdapter.ViewHolder>() {


    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_layout,
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.itemView.apply {
            tvTitle.setText(item.title, TextView.BufferType.EDITABLE)

            val lm = LinearLayoutManager(context)
            lm.orientation = LinearLayoutManager.VERTICAL

            setOnClickListener {
                val intent = Intent(
                    context,
                    if (isEditMode)
                        EditItemActivity::class.java
                    else
                        ViewItemActivity::class.java
                )
                intent.putExtra(
                    "CATALOG_ITEM_DTO",
                    Json.encodeToString(CatalogItemDTO(item, position, category, subCategory))
                )
                if (isEditMode)
                    resultLauncher?.launch(intent)
                else
                    context.startActivity(intent)
            }
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

}