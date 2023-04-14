package com.example.checkattablet

import Alumno
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView

class ListaAlumnosAdaptador (private val context: Context,
                                          private val listaAlumno: MutableList<Alumno>):
    RecyclerView.Adapter<ListaAlumnosAdaptador.AlumnosViewHolder>(),
    View.OnClickListener, View.OnLongClickListener{

    var selectedRadioButtonText = ""
    internal var selectedItem: Alumno? = null
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

    override fun getItemCount() = listaAlumno.size

    override fun onBindViewHolder(holder: AlumnosViewHolder, position: Int) {
        val paquete = listaAlumno[position]
        val backgroundDrawable = if (paquete == selectedItem) {
            ContextCompat.getDrawable(context, R.drawable.btn_seleccionado)
        } else {
            ContextCompat.getDrawable(context, R.drawable.bordes_redondos)
        }
        holder.itemView.background = backgroundDrawable




        bindPaquete(holder, paquete)
    }

    fun bindPaquete(holder: AlumnosViewHolder, listaAlumnos: Alumno) {

        holder.nombreAlumno?.text = listaAlumnos.nombreAlumno

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