package com.example.checkattablet.DataModel

import com.google.gson.annotations.SerializedName

data class Falta (
    @SerializedName("id_pasar_Lista") var idPasarLista: Int,
    @SerializedName("hora_falta") var idHorario: Int,)
