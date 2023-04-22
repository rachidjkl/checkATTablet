package com.example.checkattablet.PasarLista

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.example.checkattablet.DataModel.ClasePers

class AdapterClases(context: Context, resource: Int, objects: List<ClasePers>) :
    ArrayAdapter<ClasePers>(context, resource, objects) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = super.getView(position, convertView, parent)
        val textView = view.findViewById<TextView>(android.R.id.text1)
        textView.text = getItem(position)?.nombreClase
        return view
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = super.getDropDownView(position, convertView, parent)
        val textView = view.findViewById<TextView>(android.R.id.text1)
        textView.text = getItem(position)?.nombreClase
        return view
    }


}