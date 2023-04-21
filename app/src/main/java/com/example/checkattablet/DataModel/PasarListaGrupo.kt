package com.example.checkattablet.DataModel

import com.google.gson.annotations.SerializedName

data class PasarListaGrupo (@SerializedName("id_lista_grupo") var idListaGrupo: Int?,
                           @SerializedName("hora_inicio") var horaInicio: String,
                           @SerializedName("hora_final") var horaFin: String,
                           @SerializedName("modulo") var idModulo: Int,
                           @SerializedName("profe") var idProfe: Int,
                           @SerializedName("id_horario") var idHorario: Int,
                           @SerializedName("fecha") var fecha: String,
                            @SerializedName("estado") var estado: Int,)