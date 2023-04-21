package com.example.checkattablet

import Alumno
import Modulo
import Uf
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.checkattablet.ApiAcces.ApiGets
import com.example.checkattablet.ApiAcces.RetrofitClient
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking


class FragmentPasarLista : Fragment() {

    var listaAlumno: MutableList<Alumno>? = null
    var listaModulos: MutableList<Modulo>? = mutableListOf()
    var listaUfs: MutableList<Uf>? = null

    var clase = 130002
    var modulo: Int? = null
    var uf: Int? = null

    init {
        main()
    }

    fun main() = runBlocking {
        listaModulos = cargarModulos(clase)
    }
    fun runUf() = runBlocking {
        listaUfs = modulo?.let { cargarUfs(it) }
        llenarSpinnerUf(listaUfs)
    }

    fun runAlumnos() = runBlocking {
        listaAlumno = uf?.let { modulo?.let { it1 -> cargarAlumnos(clase, it, it1) } }
        cargarAlumnosRecycler(listaAlumno)
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



        val radioGroup = view.findViewById<RadioGroup>(R.id.myRadioGroup)








        val modulosSpinner = view.findViewById<Spinner>(R.id.modulos_spinner)
        modulosSpinner.adapter = listaModulos?.let { it.map { it.nombreModulo } }
            ?.let { MySpinnerAdapter(requireContext(), it) }

        modulosSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val moduloSeleccionado = listaModulos?.get(position)
                if (moduloSeleccionado != null) {
                    modulo = moduloSeleccionado.idModulo
                    runUf()
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // No se ha seleccionado nada
            }
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

    private fun llenarSpinnerUf(listaUfs: MutableList<Uf>?) {
        val ufsSpinner = view?.findViewById<Spinner>(R.id.uf_spinner)
        if (ufsSpinner != null) {
            ufsSpinner.adapter = listaUfs?.let { it.map { it.nombreUf } }
                ?.let { MySpinnerAdapter(requireContext(), it) }
        }

        if (ufsSpinner != null) {
            ufsSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                    val UfSeleccionado = listaUfs?.get(position)
                    if (UfSeleccionado != null) {
                        uf = UfSeleccionado.idUF
                        runAlumnos()
                    }
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                    // No se ha seleccionado nada
                }
            }
        }
    }

    private fun cargarAlumnosRecycler(listaAlumno: MutableList<Alumno>?) {

        var alumnoId: Alumno? = null
        val recyclerView = view?.findViewById<RecyclerView>(R.id.recyclerView)
        val adapter = listaAlumno?.let { ListaAlumnosAdaptador(requireContext(), it) }
        recyclerView?.hasFixedSize()
        if (recyclerView != null) {
            recyclerView.layoutManager = LinearLayoutManager(requireContext())
        }
        if (recyclerView != null) {
            recyclerView.adapter = adapter
        }

        adapter?.setOnClickListener {

            val alumnoSeleccionado = recyclerView?.let { it1 -> listaAlumno?.get(it1.getChildAdapterPosition(it)) }
            if (alumnoSeleccionado != null) {
                alumnoId = listaAlumno?.find { Alumno -> Alumno.idAlumno.equals(alumnoSeleccionado.idAlumno) }
            }
            adapter.selectedItem = alumnoSeleccionado
            adapter.notifyDataSetChanged()

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

    suspend fun cargarModulos(clase : Int): MutableList<Modulo>? {

        val userCepApi = RetrofitClient.getInstance().create(ApiGets::class.java)

        return GlobalScope.async {
            val call = userCepApi.getModulo(clase)
            val response = call.execute()
            response.body()
        }.await()
    }

    suspend fun cargarUfs(modulo : Int): MutableList<Uf>? {

        val userCepApi = RetrofitClient.getInstance().create(ApiGets::class.java)

        return GlobalScope.async {
            val call = userCepApi.getUf(modulo)
            val response = call.execute()
            response.body()
        }.await()
    }

}
