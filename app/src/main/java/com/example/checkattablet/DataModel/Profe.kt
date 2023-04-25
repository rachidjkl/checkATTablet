package com.example.checkattablet.DataModel

import com.google.gson.annotations.SerializedName

data class Profe (@SerializedName("id_profe") var idProfe: Int,
                  @SerializedName("nombre_completo") var nombreProfe: String,
                  @SerializedName("correo_cep") var correoProfe: String)