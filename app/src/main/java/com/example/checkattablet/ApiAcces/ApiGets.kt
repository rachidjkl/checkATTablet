package com.example.checkattablet.ApiAcces

import Alumno
import com.example.checkattablet.DataModel.Horario
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiGets {

    companion object
    {
        val BASE_URL = "http://abp-politecnics.com/2023/dam01/"
    }

    @GET("api/Alumno/idClase/{idClase}/idUf/{idUf}/idModulo/{idModulo}")
    fun getAlumnos(@Path("idClase") clase: Int, @Path("idUf") uf: Int, @Path("idModulo") modulo: Int): Call<MutableList<Alumno>>


    @GET("api/Horarios/HorariosDia/idClase/{idClase}/diaSemana/{diaSemana}")
    fun getHorarios(@Path("idClase") clase: Int, @Path("diaSemana") diaSemana: String): Call<List<Horario>>


}