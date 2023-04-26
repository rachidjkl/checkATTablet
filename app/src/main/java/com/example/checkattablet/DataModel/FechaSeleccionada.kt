package com.example.checkattablet.DataModel

import com.google.gson.annotations.SerializedName

data class FechaSeleccionada ( var fecha: String,
                               var year: Int,
                              var month: Int,
                              var dayOfMonth: Int,
                                var currentDayOfWeek: String)
