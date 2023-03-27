package com.example.checkattablet

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class ListasPasadasAdaptador (private val listaGrupo: MutableList<ListaGrupo>): View.OnClickListener {

    private val layout = R.layout.listaspasadas_adaptador
    private var clickListener: View.OnClickListener? = null

    class listasPasadasHolder(view: View) : RecyclerView.ViewHolder(view) {
        var moduloName  : TextView
        var profeName   : TextView
        var hora        : TextView
        var estado      : TextView


        init {
            moduloName  = view.findViewById(R.id.idModulo)
            profeName   = view.findViewById(R.id.idProfesor)
            hora        = view.findViewById(R.id.idHora)
            estado      = view.findViewById(R.id.idEstado)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): listasPasadasHolder {
        val view = LayoutInflater.from(parent.context).inflate(layout, parent, false)
        view.setOnClickListener(this)
        return listasPasadasHolder(view)
    }

    override fun onBindViewHolder(holder: listasPasadasHolder, position: Int) {
        val grupo = listaGrupo[position]
        bindPackage(holder, grupo)
    }

    fun bindPackage(holder: listasPasadasHolder, grupo: ListaGrupo) {
        holder.hora?.text = grupo.horaInicio + "-" + grupo.horaFinal
        holder.moduloName?.text = grupo.modulo
        holder.profeName?.text = grupo.profe
        when(grupo.estado){
            true -> {
                holder.estado?.text = "Pasada"
            }
            false -> {
                holder.estado?.text = "No Pasada"
            }
        }
    }

    // Devolver el tamaño de tu dataset (invocado por el layout manager)
    override fun getItemCount(): Int{
        return listaGrupo.size
    }

    override fun onClick(view: View?) {
        clickListener?.onClick(view)
    }

    fun setOnClickListener(listener: View.OnClickListener) {
        clickListener = listener
    }










}