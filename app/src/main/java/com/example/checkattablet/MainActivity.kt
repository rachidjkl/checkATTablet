package com.example.checkattablet

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.checkattablet.PasarLista.FragmentListasPasadas

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val constLayutPasarLista    = findViewById<ConstraintLayout>(R.id.ConstPasarLista)
        val constLayutListasPasadas = findViewById<ConstraintLayout>(R.id.ConstListasPasadas)
        val constLayutAjustes       = findViewById<ConstraintLayout>(R.id.ConstAjustes)

        val linearPasarLista        = findViewById<LinearLayout>(R.id.lnrPasarLista)
        val linearListasPasadas     = findViewById<LinearLayout>(R.id.lnrListasPasadas)
        val linearAjustes           = findViewById<LinearLayout>(R.id.lnrAjustes)

        linearPasarLista.visibility     = View.INVISIBLE
        linearListasPasadas.visibility  = View.INVISIBLE
        linearAjustes.visibility        = View.INVISIBLE

        constLayutPasarLista.setOnClickListener { onConstraintClick(constLayutPasarLista, linearPasarLista, linearListasPasadas, linearAjustes) }
        constLayutListasPasadas.setOnClickListener { onConstraintClick(constLayutListasPasadas, linearPasarLista, linearListasPasadas, linearAjustes) }
        constLayutAjustes.setOnClickListener { onConstraintClick(constLayutAjustes, linearPasarLista, linearListasPasadas, linearAjustes) }
    }

    private fun onConstraintClick(constLayut: ConstraintLayout, lnr1: LinearLayout, lnr2: LinearLayout, lnr3: LinearLayout) {
        when (constLayut.id) {
            R.id.ConstPasarLista -> {
                val fragmentoPasarLista = FragmentPasarLista()
                val fragmentManager = supportFragmentManager
                val fragmentTransaction = fragmentManager.beginTransaction()
                fragmentTransaction.replace(R.id.fragmentooo, fragmentoPasarLista)
                fragmentTransaction.commit()
                lnr1.visibility  = View.VISIBLE
                lnr2.visibility  = View.INVISIBLE
                lnr3.visibility  = View.INVISIBLE
            }
            R.id.ConstListasPasadas -> {
                val fragmentoListasPasadas = FragmentListasPasadas()
                val fragmentManager = supportFragmentManager
                val fragmentTransaction = fragmentManager.beginTransaction()
                fragmentTransaction.replace(R.id.fragmentooo, fragmentoListasPasadas)
                fragmentTransaction.commit()
                lnr1.visibility  = View.INVISIBLE
                lnr2.visibility  = View.VISIBLE
                lnr3.visibility  = View.INVISIBLE
            }
            R.id.ConstAjustes -> {
                val fragmentoListasPasadas = FragmentAjustes()
                val fragmentManager = supportFragmentManager
                val fragmentTransaction = fragmentManager.beginTransaction()
                fragmentTransaction.replace(R.id.fragmentooo, fragmentoListasPasadas)
                fragmentTransaction.commit()
                lnr1.visibility  = View.INVISIBLE
                lnr2.visibility  = View.INVISIBLE
                lnr3.visibility  = View.VISIBLE
            }
        }
    }


}