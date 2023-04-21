package com.example.checkattablet.DataModel

import com.google.gson.annotations.SerializedName

data class Profesor(@SerializedName("id_profe") var idProfe: Int,
                    @SerializedName("nombre_profe") var nombreProfesor: String,
                    @SerializedName("apellido1_profe") var apellido1Profe: String,
                    @SerializedName("apellido2_profe") var apellido2Profe: String,
                    @SerializedName("email_profe") var emailProfe: String)
