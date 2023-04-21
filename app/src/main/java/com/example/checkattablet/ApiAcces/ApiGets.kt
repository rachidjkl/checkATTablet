package com.example.checkattablet.ApiAcces

import Alumno
import com.example.checkattablet.DataModel.Horario
import com.example.checkattablet.DataModel.PasarLista
import com.example.checkattablet.DataModel.PasarListaGrupo
import com.example.checkattablet.DataModel.Profesor
import Modulo
import Uf
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiGets {

    companion object
    {
        val BASE_URL = "http://abp-politecnics.com/2023/dam01/"
    }

    @GET("api/ClaseModulo/Modulo/idClase//{idClase}")
    fun getModulo(@Path("idClase") idClase: Int): Call<MutableList<Modulo>>

    @GET("api/Ufs/Uf/idModulo/{idModulo}")
    fun getUf(@Path("idModulo") idModulo: Int): Call<MutableList<Uf>>

    @GET("api/Alumno/idClase/{idClase}/idUf/{idUf}/idModulo/{idModulo}")
    fun getAlumnos(@Path("idClase") clase: Int, @Path("idUf") uf: Int, @Path("idModulo") modulo: Int): Call<MutableList<Alumno>>


    @GET("api/Horarios/HorariosDia/idClase/{idClase}/diaSemana/{diaSemana}")
    fun getHorarios(@Path("idClase") clase: Int, @Path("diaSemana") diaSemana: String): Call<List<Horario>>

    //get
    @GET("api/Pasar_listas_grupo/idHorario/{idHorario}/fecha/{fecha}")
    fun getPasarListaGrupo(@Path("idHorario") idHorario: Int, @Path("fecha") fecha: String): Call<PasarListaGrupo>//get

    //get profe
    @GET("api/Profesors/{id}")
    fun getProfesor(@Path("id") idProfe: Int): Call<Profesor>

    //insert
    @POST("api/Pasar_listas_grupo")
    fun InsertPasarListaGrupo(@Body pasarListaGrupo: PasarListaGrupo): Call<Void>

    //update estado a true
    @POST("api/Pasar_listas_grupo/setEstadoTrue/id_lista_grupo/{id_lista_grupo}")
    fun UpdateEstadoPasarListaGrupo(@Path("id_lista_grupo") idListaGrupo: Int): Call<Void>

    @POST("api/Pasar_Lista/{Pasar_Lista}")
    fun InsertPasarLista(@Body pasarLista: PasarLista): Call<Void>


}