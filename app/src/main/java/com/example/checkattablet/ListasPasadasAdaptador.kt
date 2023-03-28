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

class ListasPasadasAdaptador (private val context: Context,
                         private val listaGrupos: MutableList<ListaGrupo>):
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

    override fun getItemCount() = listaGrupos.size

    override fun onBindViewHolder(holder: GruposViewHolder, position: Int) {
        val paquete = listaGrupos[position]
        bindPaquete(holder, paquete)
    }

    fun bindPaquete(holder: GruposViewHolder, listaGrupos: ListaGrupo) {

        holder.horaClase?.text      = listaGrupos.horaInicio + "-" + listaGrupos.horaFinal
        holder.idModulo?.text       = listaGrupos.modulo + "-" + listaGrupos.uf
        holder.nombreProfe?.text    = listaGrupos.profe
        when(listaGrupos.estado) {
            true -> {
                holder.estadoLista?.text = "Validada"
                holder.estadoLista?.setBackgroundResource(R.drawable.fondo_verde)
            }
            false -> {
                holder.estadoLista?.text = "No Validada"
                holder.estadoLista?.setBackgroundResource(R.drawable.fondo_rojo)
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