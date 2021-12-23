package com.example.thecatalog.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.thecatalog.models.CatalogCategory
import com.example.thecatalog.adapters.CategoryListAdapter
import com.example.thecatalog.R
import kotlinx.android.synthetic.main.fragment_view_catalog.*

class ViewCatalogFragment(val catalog: MutableList<CatalogCategory>): Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_view_catalog, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val lm = LinearLayoutManager(activity)
        lm.orientation = LinearLayoutManager.VERTICAL
        rvTopLevelItems.adapter = CategoryListAdapter(
            catalog,
            context,
            null,
            null,
            null,
            null,
            null,
            null,
            false
        )
        rvTopLevelItems.layoutManager = lm
    }
}