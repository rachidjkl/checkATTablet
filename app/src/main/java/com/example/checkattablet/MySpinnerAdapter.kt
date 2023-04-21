package com.example.checkattablet

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.core.content.ContextCompat

class MySpinnerAdapter(context: Context, items: List<String>) : ArrayAdapter<String>(context, R.layout.spinner_item_layout, items) {

    private val inflater: LayoutInflater = LayoutInflater.from(context)

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = convertView ?: inflater.inflate(R.layout.spinner_item_layout, parent, false)
        val item = getItem(position)
        view.findViewById<TextView>(R.id.text1).text = item
        return view
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = convertView ?: inflater.inflate(R.layout.spinner_item_layout, parent, false)
        val item = getItem(position)
        view.findViewById<TextView>(R.id.text1).text = item
        return view
    }
}

