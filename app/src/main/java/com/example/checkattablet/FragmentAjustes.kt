package com.example.checkattablet

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout


class FragmentAjustes : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflar el layout del fragmento
        return inflater.inflate(R.layout.fragment_ajustes, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val btnEspanol = view.findViewById<Button>(R.id.btnEspanol)
        val btnCatalan = view.findViewById<Button>(R.id.btnCatalan)
        val btnIngles = view.findViewById<Button>(R.id.btnIngles)

        btnEspanol.setOnClickListener {
            btnEspanol.setBackgroundResource(R.drawable.btn_seleccionado)
            btnCatalan.setBackgroundResource(R.drawable.btn_adicional)
            btnIngles.setBackgroundResource(R.drawable.btn_adicional)
        }
        btnCatalan.setOnClickListener {
            btnEspanol.setBackgroundResource(R.drawable.btn_adicional)
            btnCatalan.setBackgroundResource(R.drawable.btn_seleccionado)
            btnIngles.setBackgroundResource(R.drawable.btn_adicional)
        }
        btnIngles.setOnClickListener {
            btnEspanol.setBackgroundResource(R.drawable.btn_adicional)
            btnCatalan.setBackgroundResource(R.drawable.btn_adicional)
            btnIngles.setBackgroundResource(R.drawable.btn_seleccionado)
        }



    }


}