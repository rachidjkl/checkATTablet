package com.example.checkattablet.DataModel

import com.google.gson.annotations.SerializedName

data class ClasePers (
    @SerializedName("id_clase") var idClase: Int,
    @SerializedName("nombre_clase") var nombreClase: String)