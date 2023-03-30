package com.example.checkattablet

import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView

class ListaAlumnosAdaptador (private val context: Context,
                              private val listaAlumnos: MutableList<ListaAlumnos>):
    RecyclerView.Adapter<ListaAlumnosAdaptador.AlumnosViewHolder>(),
    View.OnClickListener, View.OnLongClickListener{

    var selectedRadioButtonText = ""
    internal var selectedItem: ListaAlumnos? = null
    private val layout = R.layout.listasalumnos_adaptador
    private var clickListener: View.OnClickListener? = null
    private var clickLongListener: View.OnLongClickListener? = null


    class AlumnosViewHolder(val view: View):
        RecyclerView.ViewHolder(view){

        var nombreAlumno        : TextView
        var asistenca           : TextView
        var layoutAlumnos       : ConstraintLayout

        init{
            nombreAlumno        = view.findViewById(R.id.idNombreAlumno)
            asistenca           = view.findViewById(R.id.idAsistencia)
            layoutAlumnos       = view.findViewById(R.id.layoutAlumnos)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlumnosViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(layout, parent, false)
        view.setOnClickListener(this)
        view.setOnLongClickListener(this)
        return AlumnosViewHolder(view)
    }

    override fun getItemCount() = listaAlumnos.size

    override fun onBindViewHolder(holder: AlumnosViewHolder, position: Int) {
        val paquete = listaAlumnos[position]
        val backgroundDrawable = if (paquete == selectedItem) {
            ContextCompat.getDrawable(context, R.drawable.btn_seleccionado)
        } else {
            ContextCompat.getDrawable(context, R.drawable.bordes_redondos)
        }
        holder.itemView.background = backgroundDrawable

        if (paquete == selectedItem){
            holder.asistenca.text = selectedRadioButtonText
        }


        bindPaquete(holder, paquete)
    }

    fun bindPaquete(holder: AlumnosViewHolder, listaAlumnos: ListaAlumnos) {

        holder.nombreAlumno?.text = listaAlumnos.nombreAlumno

        when(listaAlumnos.asistencia) {
            "P" -> {
                holder.asistenca?.setBackgroundResource(R.drawable.fondo_verde)
            }
            "R" -> {
                holder.asistenca?.setBackgroundResource(R.drawable.fondo_amarillo)
            }
            "FJ" -> {
                holder.asistenca?.setBackgroundResource(R.drawable.fondo_naranja)
            }
            "FI" -> {
                holder.asistenca?.setBackgroundResource(R.drawable.fondo_rojo)
            }
            "Irse antes de acabar" -> {
                holder.asistenca?.setBackgroundResource(R.drawable.fondo_amarillo)
            }
        }
    }


    override fun onClick(view: View?) {
        clickListener?.onClick(view)
    }

    fun setOnClickListener(listener: View.OnClickListener){
        clickListener = listener
    }

    override fun onLongClick(view: View?): Boolean {
        clickLongListener?.onLongClick(view)
        return true
    }

    fun setOnLongClickListener(listener: View.OnLongClickListener){
        clickLongListener = listener
    }

}