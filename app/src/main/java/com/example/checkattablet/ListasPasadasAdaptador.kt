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
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.example.checkattablet.ApiAcces.ApiGets
import com.example.checkattablet.ApiAcces.RetrofitClient
import com.example.checkattablet.DataModel.Horario
import com.example.checkattablet.DataModel.PasarListaGrupo
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking

class ListasPasadasAdaptador (private val context: Context,
                         private val listaHorariosDiaClase: List<Horario>, private val fecha: String):
    RecyclerView.Adapter<ListasPasadasAdaptador.GruposViewHolder>(),
    View.OnClickListener, View.OnLongClickListener{

    private val layout = R.layout.listaspasadas_adaptador
    private var clickListener: View.OnClickListener? = null
    private var clickLongListener: View.OnLongClickListener? = null
    private var idPasarListagrupo: Int = 0



    fun callApiPasarListaGrupo(horario :Horario, fecha: String) = runBlocking {

        var pasarListaGrupo = globalFunGet(horario.idHorario, fecha)

        if (pasarListaGrupo == null) {
            var newPasarListaGrupo = PasarListaGrupo(null,horario.horaInicio,horario.horaFin,horario.idModulo,10005,horario.idHorario,fecha)

            globalFunCreatePasarListaGrupo(newPasarListaGrupo)
        }else{
            idPasarListagrupo = pasarListaGrupo.idListaGrupo!!.toInt()
        }
    }


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

        callApiPasarListaGrupo(horario, fecha);


        holder.horaClase?.text      = horaInicio + "-" + horaFin
        holder.idModulo?.text       = horario.siglasUf // en realidad son siglas modulo
        holder.nombreProfe?.text
        holder.estadoLista?.text = idPasarListagrupo.toString()

    }


    private suspend fun globalFunGet(idHorario: Int, fecha: String):PasarListaGrupo? {

        val userCepApi = RetrofitClient.getInstance().create(ApiGets::class.java)

        return GlobalScope.async {
            val call = userCepApi.getPasarListaGrupo(idHorario, fecha)
            val response = call.execute()
            response.body()
        }.await()
    }

    private suspend fun globalFunCreatePasarListaGrupo ( pasarListaGrupo: PasarListaGrupo ):Void? {

        val userCepApi = RetrofitClient.getInstance().create(ApiGets::class.java)

        return GlobalScope.async {
            val call = userCepApi.InsertPasarListaGrupo(pasarListaGrupo)
            val response = call.execute()
            response.body()
        }.await()
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