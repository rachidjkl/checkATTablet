package com.example.checkattablet.ApiAcces

import Alumno
import com.example.checkattablet.DataModel.Horario
import com.example.checkattablet.DataModel.PasarLista
import com.example.checkattablet.DataModel.PasarListaGrupo
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
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

    //get
    @POST("api/Pasar_listas_grupo/idHorario/{idHorario}}/fecha/{fecha}")
    fun getPasarListaGrupo(@Path("idHorario") idHorario: Int, @Path("fecha") fecha: String): Call<PasarListaGrupo>

    //insert
    @POST("api/Pasar_listas_grupo")
    fun InsertPasarListaGrupo(@Body pasarListaGrupo: PasarListaGrupo): Call<Void>

    //update
    @POST("api/Pasar_listas_grupo/setEstadoTrue/id_lista_grupo/{id_lista_grupo}")
    fun UpdateEstadoPasarListaGrupo(@Path("id_lista_grupo") idListaGrupo: Int): Call<Void>

    @POST("api/Pasar_Lista/{Pasar_Lista}")
    fun InsertPasarLista(@Body pasarLista: PasarLista): Call<Void>
}