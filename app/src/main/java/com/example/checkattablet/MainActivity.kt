package com.example.checkattablet

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.checkattablet.PasarLista.FragmentListasPasadas

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var profeNombreLogIn = findViewById<TextView>(R.id.profeNombreLogIn)
        profeNombreLogIn.text = Login.userProfe.nombreProfe

        val constLayutListasPasadas = findViewById<ConstraintLayout>(R.id.ConstListasPasadas)
        val constLayutAjustes       = findViewById<ConstraintLayout>(R.id.ConstAjustes)


        val linearListasPasadas     = findViewById<LinearLayout>(R.id.lnrListasPasadas)
        val linearAjustes           = findViewById<LinearLayout>(R.id.lnrAjustes)

        linearListasPasadas.visibility  = View.INVISIBLE
        linearAjustes.visibility        = View.INVISIBLE


        constLayutListasPasadas.setOnClickListener { onConstraintClick(constLayutListasPasadas, linearListasPasadas, linearAjustes) }
        constLayutAjustes.setOnClickListener { onConstraintClick(constLayutAjustes, linearListasPasadas, linearAjustes) }
    }

    private fun onConstraintClick(constLayut: ConstraintLayout,lnr2: LinearLayout, lnr3: LinearLayout) {
        when (constLayut.id) {
            R.id.ConstListasPasadas -> {
                val fragmentoListasPasadas = FragmentListasPasadas()
                val fragmentManager = supportFragmentManager
                val fragmentTransaction = fragmentManager.beginTransaction()
                fragmentTransaction.replace(R.id.fragmentooo, fragmentoListasPasadas)
                fragmentTransaction.commit()
                lnr2.visibility  = View.VISIBLE
                lnr3.visibility  = View.INVISIBLE
            }
            R.id.ConstAjustes -> {
                val fragmentoListasPasadas = FragmentAjustes()
                val fragmentManager = supportFragmentManager
                val fragmentTransaction = fragmentManager.beginTransaction()
                fragmentTransaction.replace(R.id.fragmentooo, fragmentoListasPasadas)
                fragmentTransaction.commit()
                lnr2.visibility  = View.INVISIBLE
                lnr3.visibility  = View.VISIBLE
            }
        }
    }


}