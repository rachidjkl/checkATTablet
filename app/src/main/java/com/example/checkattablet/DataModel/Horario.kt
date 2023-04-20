package com.example.checkattablet.DataModel

import com.google.gson.annotations.SerializedName

data class Horario(@SerializedName("id_horario") var idHorario: Int,
            @SerializedName("id_clase") var idClase: Int,
            @SerializedName("id_modulo") var idModulo: Int,
            @SerializedName("hora_inicio") var horaInicio: String,
            @SerializedName("hora_fin") var horaFin: String,
            @SerializedName("nombre_modulo") var nombreModulo: String,
            @SerializedName("siglas_uf") var siglasUf: String,
            @SerializedName("idPasarListaGrupo") var idPasarListaGrupo: Int)