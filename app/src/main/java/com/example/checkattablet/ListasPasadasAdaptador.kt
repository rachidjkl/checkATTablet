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
import androidx.recyclerview.widget.RecyclerView
import com.example.checkattablet.DataModel.Horario

class ListasPasadasAdaptador (private val context: Context,
                         private val listaHorariosDiaClase: List<Horario>):
    RecyclerView.Adapter<ListasPasadasAdaptador.GruposViewHolder>(),
    View.OnClickListener, View.OnLongClickListener{

    private val layout = R.layout.listaspasadas_adaptador
    private var clickListener: View.OnClickListener? = null
    private var clickLongListener: View.OnLongClickListener? = null


    class GruposViewHolder(val view: View):
        RecyclerView.ViewHolder(view){

        var horaClase      : TextView
        var idModulo       : TextView
        var nombreProfe    : TextView
        var estadoLista    : TextView

        init{
            horaClase      = view.findViewById(R.id.idHora)
            idModulo       = view.findViewById(R.id.idModulo)
            nombreProfe    = view.findViewById(R.id.idProfesor)
            estadoLista    = view.findViewById(R.id.idEstado)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GruposViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(layout, parent, false)
        view.setOnClickListener(this)
        view.setOnLongClickListener(this)
        return GruposViewHolder(view)
    }

    override fun getItemCount() = listaHorariosDiaClase.size

    override fun onBindViewHolder(holder: GruposViewHolder, position: Int) {
        val paquete = listaHorariosDiaClase[position]
        bindPaquete(holder, paquete)
    }

    fun bindPaquete(holder: GruposViewHolder, horario: Horario) {

        val horaInicio = if (horario.horaInicio.length > 5) horario.horaInicio.substring(0, 5) else horario.horaInicio
        val horaFin = if (horario.horaFin.length > 5) horario.horaFin.substring(0, 5) else horario.horaFin

        holder.horaClase?.text      = horaInicio + "-" + horaFin
        holder.idModulo?.text       = horario.siglasUf // en realidad son siglas modulo
        holder.nombreProfe?.text

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