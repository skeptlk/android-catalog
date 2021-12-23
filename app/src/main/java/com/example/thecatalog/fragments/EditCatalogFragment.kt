package com.example.thecatalog.fragments

import android.app.Activity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.thecatalog.models.CatalogCategory
import com.example.thecatalog.R
import com.example.thecatalog.adapters.CategoryListAdapter
import com.example.thecatalog.helpers.Prompt
import com.example.thecatalog.models.CatalogItemDTO
import com.example.thecatalog.models.CatalogSubCategory
import kotlinx.android.synthetic.main.fragment_edit_catalog.*
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

class EditCatalogFragment(val catalog: MutableList<CatalogCategory>): Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_edit_catalog, container, false)

    private var resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult())
    { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            // Get edited record
            val extras: Bundle? = result.data?.extras
            val item = Json.decodeFromString<CatalogItemDTO>(extras?.get("UPDATED_CATALOG_ITEM") as String)

            val category = catalog.find { cat -> cat.title == item.categoryTitle }
            if (category != null) {
                val subCategory = category.items.find { sub -> sub.title == item.subCategoryTitle }
                if (subCategory != null) {
                    if (item.isNew)
                        subCategory.items.add(item.catalogItem)
                    else
                        subCategory.items[item.catalogItemIndex] = item.catalogItem
                }
            }

            rvTopLevelItems2.apply {
                val pos = catalog.indexOf(category)
                adapter?.notifyItemChanged(pos)

                Handler(Looper.getMainLooper()).postDelayed({
                    findViewHolderForAdapterPosition(pos)?.itemView?.performClick()
                }, 200) }

        }
    }

    var prompt: Prompt? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val lm = LinearLayoutManager(activity)
        lm.orientation = LinearLayoutManager.VERTICAL
        rvTopLevelItems2.adapter = CategoryListAdapter(
            catalog,
            context,
            resultLauncher,
            ::onAddSubCategory,
            ::onEditCategoryTitle,
            ::onDeleteCategory,
            ::onEditSubCategoryTitle,
            ::onDeleteSubCategory,
            true
        )
        rvTopLevelItems2.layoutManager = lm

        btnAddCategory.setOnClickListener(listener)

        prompt = Prompt(resources.getString(R.string.add_category_prompt), context)
    }


    private val listener = View.OnClickListener { view ->
        when (view.id) {
            R.id.btnAddCategory -> {
                prompt?.show { title ->
                    if (title.isNotBlank()) {
                        catalog.add(CatalogCategory(title, mutableListOf()))
                        rvTopLevelItems2.adapter?.notifyItemInserted(catalog.size)
                    }
                }
            }
        }
    }

    private fun onAddSubCategory (c: CatalogCategory) {
        prompt?.setTitle(resources.getString(R.string.add_new_subcategory))
        prompt?.show { title ->
            if (title.isNotBlank()) {
                c.items.add(CatalogSubCategory(title, mutableListOf()))
                rvTopLevelItems2.adapter?.notifyItemChanged(catalog.indexOf(c))
            }
        }
    }

    private fun onDeleteCategory (c: CatalogCategory) {
        val i = catalog.indexOf(c)
        catalog.removeAt(i)
        rvTopLevelItems2.adapter?.notifyItemRemoved(i)
    }

    private fun onDeleteSubCategory (c: String, cc: CatalogSubCategory) {
        val catItem = catalog.find { cat -> cat.title == c }
        val i = catItem?.items?.indexOf(cc)
        if (i != null) {
            catItem.items.removeAt(i)
        };
        rvTopLevelItems2.adapter?.notifyItemChanged(catalog.indexOf(catItem));
    }

    private fun onEditCategoryTitle (c: CatalogCategory) {
        prompt?.setTitle(resources.getString(R.string.edit_category_prompt))
        prompt?.setValue(c.title)
        prompt?.show { title ->
            if (title.isNotBlank()) {
                c.title = title
                rvTopLevelItems2.adapter?.notifyItemChanged(catalog.indexOf(c))
            }
        }
    }

    private fun onEditSubCategoryTitle (c: String, cc: CatalogSubCategory) {
        prompt?.setTitle(resources.getString(R.string.edit_subcategory_name))
        prompt?.setValue(cc.title)
        prompt?.show { title ->
            if (title.isNotBlank()) {
                cc.title = title
                rvTopLevelItems2.adapter?.notifyItemChanged(catalog.indexOfFirst { i -> i.title == c })
            }
        }
    }
}

