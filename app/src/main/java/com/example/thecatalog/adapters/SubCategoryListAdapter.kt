package com.example.thecatalog.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.activity.result.ActivityResultLauncher
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.thecatalog.R
import com.example.thecatalog.models.CatalogSubCategory
import kotlinx.android.synthetic.main.item_catalog_second_level.view.*


class SubCategoryListAdapter(
    private val subcategories: MutableList<CatalogSubCategory>,
    private val context: Context?,
    private val resultLauncher: ActivityResultLauncher<Intent>?,
    private val category: String,
    private val onEditSubTitle: ((String, CatalogSubCategory) -> Unit)?,
    private val onDeleteSubCategory: ((String, CatalogSubCategory) -> Unit)?,
    private val isEditMode: Boolean
): RecyclerView.Adapter<SubCategoryListAdapter.ViewHolder>() {
    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder(
        LayoutInflater.from(parent.context).inflate(
            R.layout.item_catalog_second_level,
            parent,
            false
        )
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val subCategory = subcategories[position]
        holder.itemView.apply {
            tvTitle.setText(subCategory.title, TextView.BufferType.EDITABLE)

            val ladapter = ItemsListAdapter(
                subCategory.items,
                resultLauncher,
                category,
                subCategory.title,
                isEditMode
            )
            val lm = LinearLayoutManager(context)
            lm.orientation = LinearLayoutManager.VERTICAL

            rvItems.apply {
                adapter = ladapter
                layoutManager = lm
                visibility = View.GONE
            }

            setOnClickListener {
                rvItems.visibility =
                    when (rvItems.visibility) {
                        View.GONE -> View.VISIBLE
                        else -> View.GONE
                    }
                icExpand.rotation += 180F
            }

            icEdit.setOnClickListener {
                onEditSubTitle?.invoke(category, subCategory)
            }

            icDelete.setOnClickListener {
                onDeleteSubCategory?.invoke(category, subCategory)
            }

            if (!isEditMode) {
                icDelete.visibility = View.GONE
                icEdit.visibility = View.GONE
                icAddItem.visibility = View.GONE
            }
        }
    }

    override fun getItemCount(): Int {
        return subcategories.size
    }

    fun newItem () {
        subcategories.add(
            CatalogSubCategory(
                "",
                mutableListOf()
            )
        )
        notifyItemInserted(subcategories.size - 1)
    }

}