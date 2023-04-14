package com.example.checkattablet

import Alumno
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.checkattablet.ApiAcces.ApiGets
import com.example.checkattablet.ApiAcces.RetrofitClient
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking


class FragmentPasarLista : Fragment() {

    var listaAlumno: MutableList<Alumno>? = null

    init {
        main()
    }

    fun main() = runBlocking {
        listaAlumno = cargarAlumnos(130000, 30000, 40000)
        
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflar el layout del fragmento
        return inflater.inflate(R.layout.fragment_pasar_lista, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var alumnoId: Alumno? = null

        val radioGroup = view.findViewById<RadioGroup>(R.id.myRadioGroup)


        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerView)

        val adapter = listaAlumno?.let { ListaAlumnosAdaptador(requireContext(), it) }
        recyclerView.hasFixedSize()
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter

        adapter?.setOnClickListener {

            val alumnoSeleccionado = listaAlumno?.get(recyclerView.getChildAdapterPosition(it))
            if (alumnoSeleccionado != null) {
                alumnoId = listaAlumno?.find { Alumno -> Alumno.idAlumno.equals(alumnoSeleccionado.idAlumno) }
            }
            adapter.selectedItem = alumnoSeleccionado
            adapter.notifyDataSetChanged()

        }


        radioGroup.setOnCheckedChangeListener { group, checkedId ->
            /*val radioButton = view.findViewById<RadioButton>(checkedId)
            if (radioButton != null) {
                when(radioButton.text.toString()) {
                    "Presente" -> {
                        alumnoId?.asistencia = "P"
                    }
                    "Retraso" -> {
                        alumnoId?.asistencia = "R"
                    }
                    "Falta Justificada" -> {
                        alumnoId?.asistencia = "FJ"
                    }
                    "Falta Injustificada" -> {
                        alumnoId?.asistencia = "FI"
                    }
                    "Irse antes de acabar" -> {
                        alumnoId?.asistencia = "Irse antes de acabar"
                    }
                    else ->{
                        alumnoId?.asistencia = ""
                    }
                }

                // Set the appropriate radio button for the next element's attendance
                when (alumnoId?.asistencia) {
                    "P" -> {
                        radioGroup.check(R.id.radioButtonPresente)
                    }
                    "R" -> {
                        radioGroup.check(R.id.radioButtonRetraso)
                    }
                    "FI" -> {
                        radioGroup.check(R.id.radioButtonFaltaI)
                    }
                    "FJ" -> {
                        radioGroup.check(R.id.radioButtonFaltaJ)
                    }
                    "Irse antes de acabar" -> {
                        radioGroup.check(R.id.radioButtonIrseAntes)
                    }
                    else -> {
                        radioGroup.clearCheck()
                    }
                }
                adapter.selectedItem = alumnoId
                adapter.notifyDataSetChanged()

            } else {
                alumnoId?.asistencia = ""

            }*/
        }




    }

    suspend fun cargarAlumnos(clase : Int, uf : Int, modulo: Int): MutableList<Alumno>? {

        val userCepApi = RetrofitClient.getInstance().create(ApiGets::class.java)

        return GlobalScope.async {
            val call = userCepApi.getAlumnos(clase,uf,modulo)
            val response = call.execute()
            response.body()
        }.await()
    }

}
