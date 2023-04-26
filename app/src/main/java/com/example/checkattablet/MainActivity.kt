package com.example.checkattablet

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.checkattablet.DataModel.FechaSeleccionada
import com.example.checkattablet.PasarLista.FragmentListasPasadas
import java.util.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var profeNombreLogIn = findViewById<TextView>(R.id.profeNombreLogIn)
        profeNombreLogIn.text = Login.userProfe.nombreProfe


        //FECHA ACTUAL
        val cal = Calendar.getInstance()
        // Obtiene la fecha actual
        val year = cal.get(Calendar.YEAR)
        val month = cal.get(Calendar.MONTH)
        val dayOfMonth = cal.get(Calendar.DAY_OF_MONTH)
        // Crea un objeto Calendar para la fecha actual
        val currentCalendar = Calendar.getInstance()
        currentCalendar.set(year, month, dayOfMonth)
        // Obtiene el nombre del día de la semana correspondiente a la fecha actual en español
        var currentDayOfWeek = currentCalendar.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale("es", "ES"))
        //asignamos una fecha para la consulta de la api
        var fecha = String.format("%04d-%02d-%02d", year, month + 1,dayOfMonth)

        var fechaActual= FechaSeleccionada(fecha,year,month,dayOfMonth,currentDayOfWeek)
        FragmentListasPasadas.fecha = fechaActual



        var cargandoScreen = findViewById<LinearLayout>(R.id.cargandoLayout)
        cargandoScreen.visibility = View.GONE

        val constLayutListasPasadas = findViewById<ConstraintLayout>(R.id.ConstListasPasadas)
        val constLayutAjustes       = findViewById<ConstraintLayout>(R.id.ConstAjustes)


        val linearListasPasadas     = findViewById<LinearLayout>(R.id.lnrListasPasadas)
        val linearAjustes           = findViewById<LinearLayout>(R.id.lnrAjustes)

        linearListasPasadas.visibility  = View.INVISIBLE
        linearAjustes.visibility        = View.INVISIBLE


        constLayutListasPasadas.setOnClickListener { onConstraintClick(constLayutListasPasadas, linearListasPasadas, linearAjustes) }
        constLayutAjustes.setOnClickListener { onConstraintClick(constLayutAjustes, linearListasPasadas, linearAjustes) }
    }


    fun ocultarCargandoScreen() {
        var cargandoScreen = findViewById<LinearLayout>(R.id.cargandoLayout)
        cargandoScreen.visibility = View.VISIBLE

        Handler().postDelayed({
            cargandoScreen.visibility = View.GONE
        }, 2000) // 2000 milisegundos = 2 segundos
    }

    private fun onConstraintClick(constLayut: ConstraintLayout,lnr2: LinearLayout, lnr3: LinearLayout) {
        when (constLayut.id) {
            R.id.ConstListasPasadas -> {
                ocultarCargandoScreen()
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