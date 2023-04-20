package com.example.checkattablet.DataModel

import com.google.gson.annotations.SerializedName

data class PasarLista (@SerializedName("id_pasar_lista") var id_pasar_lista: Int,
                       @SerializedName("id_alumno") var idAlumno: Int,
                       @SerializedName("id_horario") var idHorario: Int,
                       @SerializedName("id_uf") var idUf: Int,
                       @SerializedName("id_datetime") var dateTime: String,
                       @SerializedName("asistencia") var asistencia: String,
                       @SerializedName("pasada") var pasada: Int,
                       @SerializedName("id_lista_grupo") var idListaGrupo: Int)