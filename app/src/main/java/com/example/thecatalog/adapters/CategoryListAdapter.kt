package com.example.thecatalog.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.thecatalog.R
import com.example.thecatalog.models.CatalogCategory
import com.example.thecatalog.models.CatalogSubCategory
import kotlinx.android.synthetic.main.item_catalog_first_level.view.*
import kotlin.reflect.KFunction2
import kotlin.reflect.KProperty0


class CategoryListAdapter(
    private val catalog: MutableList<CatalogCategory>,
    private val context: Context?,
    private val resultLauncher: ActivityResultLauncher<Intent>?,
    private val onAddSubCategory: ((c: CatalogCategory) -> Unit)?,
    private val onEditTitle: ((CatalogCategory) -> Unit)?,
    private val onDeleteCategory: ((CatalogCategory) -> Unit)?,
    private val onEditSubCategoryTitle: ((String, CatalogSubCategory) -> Unit)?,
    private val onDeleteSubcategory: ((String, CatalogSubCategory) -> Unit)?,
    private val isEditMode: Boolean
): RecyclerView.Adapter<CategoryListAdapter.ViewHolder>() {

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_catalog_first_level,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val category = catalog[position]

        holder.itemView.apply {
            tvTitle.text = category.title
            val adapter = SubCategoryListAdapter(
                category.items,
                context,
                resultLauncher,
                category.title,
                onEditSubCategoryTitle,
                onDeleteSubcategory,
                isEditMode
            )
            val lm = LinearLayoutManager(context)
            lm.orientation = LinearLayoutManager.VERTICAL
            rvSecondLevelItems.adapter = adapter
            rvSecondLevelItems.layoutManager = lm
            rvSecondLevelItems.visibility = View.GONE

            setOnClickListener {
                rvSecondLevelItems.visibility =
                    when (rvSecondLevelItems.visibility) {
                        View.GONE -> View.VISIBLE
                        else -> View.GONE
                    }
                icExpand.rotation += 180F
            }

            icAddSubCategory.setOnClickListener {
                onAddSubCategory?.invoke(category)
            }

            icEdit.setOnClickListener {
                onEditTitle?.invoke(category)
            }

            icDelete.setOnClickListener {
                onDeleteCategory?.invoke(category)
            }

            if (!isEditMode) {
                icAddSubCategory.visibility = View.GONE
                icEdit.visibility = View.GONE
                icDelete.visibility = View.GONE
            }

        }
    }

    override fun getItemCount(): Int {
        return catalog.size
    }

    fun addItem (category: CatalogCategory) {
        catalog.add(category)
        notifyItemInserted(catalog.size - 1)
    }
}