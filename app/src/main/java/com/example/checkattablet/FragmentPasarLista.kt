package com.example.checkattablet

import Alumno
import Modulo
import Uf
import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.checkattablet.ApiAcces.ApiGets
import com.example.checkattablet.ApiAcces.RetrofitClient
import com.example.checkattablet.DataModel.Falta
import com.example.checkattablet.DataModel.Horario
import com.example.checkattablet.DataModel.PasarLista
import com.example.checkattablet.PasarLista.FragmentListasPasadas
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.text.SimpleDateFormat
import java.util.*


class FragmentPasarLista : Fragment() {

    var horarioPasarLista: Horario? = null;

    var ListaPasadaAlumnos =  mutableListOf<PasarLista>()
    var listaAlumno: MutableList<Alumno>? = null
    var listaModulos: MutableList<Modulo>? = mutableListOf()
    var listaUfs: MutableList<Uf>? = null

    var alumnoSeleccionado: Alumno? = null
    var adapter: PasrListaAdapter? = null
    var selectedRadioButtonText = ""



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
        var listaAlumnos = listaAlumno

        if (listaAlumnos != null){
            cargarAlumnosRecycler(listaAlumnos)
        }

    }

    fun getFechaActual(): String {
        val cal = Calendar.getInstance()
        val formatoFecha = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        return formatoFecha.format(cal.time)
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
        horarioPasarLista = arguments?.getSerializable("horarioPasarLista") as? Horario
        var fecha =  arguments?.getSerializable("fecha") as? String

        //dia
        var fechaActualPasarLista = view.findViewById<TextView>(R.id.fechaPasarLista)
        fechaActualPasarLista.setText(fecha)

        //hora
        var horarioText = view.findViewById<TextView>(R.id.textViewHorasSeleccionadas)
        horarioText.setText("Hora: "+horarioPasarLista!!.horaInicio.substring(0, 5) + " : "+horarioPasarLista!!.horaFin.substring(0, 5))

        //spinner modulo select
        val value = horarioPasarLista!!.pasarListaGrupo.idModulo
        val modulosSpinner = view.findViewById<Spinner>(R.id.modulos_spinner)
        val radioGroup = view.findViewById<RadioGroup>(R.id.myRadioGroup)

        //modulo spinner
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
        //set modulo spinner position
        var position = listaModulos!!.indexOfFirst { it.idModulo == value }
        modulosSpinner.setSelection(position)
        modulosSpinner.isEnabled = false



        radioGroup.setOnCheckedChangeListener { _, checkedId ->
            val radioButton = view.findViewById<RadioButton>(checkedId)
            if (radioButton != null) {
                when(radioButton.text.toString()) {
                    "Presente" -> {
                        selectedRadioButtonText = "P"
                    }
                    "Retraso" -> {
                        selectedRadioButtonText =  "R"
                    }
                    "Falta Justificada" -> {
                        selectedRadioButtonText = "FJ"
                    }
                    "Falta Injustificada" -> {
                        selectedRadioButtonText = "FI"
                    }
                    "Irse antes de acabar" -> {
                        selectedRadioButtonText = "IA"
                    }
                    else ->{
                        selectedRadioButtonText = "Error"
                    }
                }
            }
            alumnoSeleccionado?.asistencia = selectedRadioButtonText
            adapter?.notifyDataSetChanged()
        }

        val pasarListaButton = view.findViewById<Button>(R.id.buttonValidarLista)

        pasarListaButton.setOnClickListener(){
            //evitamos que pueda hacer doble click
            pasarListaButton.isClickable = false
            //ocultamos
            ocultarCargandoScreen(view)

            var listaAlumnos = listaAlumno!!


            for (alumno in listaAlumnos) {
                var pasarListaAlumno : PasarLista = PasarLista(null, alumno.idAlumno,horarioPasarLista!!.idHorario,uf!!
                ,horarioPasarLista!!.pasarListaGrupo.fecha,alumno.asistencia,1,horarioPasarLista!!.pasarListaGrupo.idListaGrupo!!)

                ListaPasadaAlumnos.add(pasarListaAlumno)
            }
            insertPasarListaList(ListaPasadaAlumnos)
            //el profe tambien se tendra que actuaolizar
            updateListaPasadaGrupoNoPasadaToPasada(horarioPasarLista!!.pasarListaGrupo.idListaGrupo!!,Login.userProfe.idProfe)

            pasarListaButton.isClickable = true

            val fragmentoListasPasadas = FragmentListasPasadas()
            val fragmentManager = requireActivity().supportFragmentManager
            val fragmentTransaction = fragmentManager.beginTransaction()

            // Agregar el fragmento actual a la pila de fragmentos
            fragmentTransaction.addToBackStack(null)

            // Reemplazar el fragmento actual con el FragmentListasPasadas2
            fragmentTransaction.replace(R.id.fragmentooo, fragmentoListasPasadas)
            fragmentTransaction.commit()
        }





    }

    private fun cargarAlumnosRecycler(listaAlumnos: MutableList<Alumno>) {

        for (alumno in listaAlumnos) {
            alumno.asistencia = "P"
        }

        val recyclerView = view?.findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView?.hasFixedSize()
        recyclerView?.layoutManager = LinearLayoutManager(requireContext())

        adapter = PasrListaAdapter(requireContext(), listaAlumnos )
        recyclerView?.adapter = adapter

        adapter?.setOnClickListener {
            val alumnoSeleccionadoRecyclerView = listaAlumnos[recyclerView!!.getChildAdapterPosition(it)]
            alumnoSeleccionado = listaAlumnos.find { alumno -> alumno.idAlumno == alumnoSeleccionadoRecyclerView.idAlumno }
            adapter?.selectedItem = alumnoSeleccionadoRecyclerView
            adapter?.notifyDataSetChanged()
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

    fun insertPasarListaList(lista: MutableList<PasarLista>) {

        val userCepApi = RetrofitClient.getInstance().create(ApiGets::class.java)

        GlobalScope.launch() {
            val call = userCepApi.InsertPasarLista(lista)
            val response = call.execute()

            if (response.code() != 204) {
               // Toast.makeText(requireActivity(), "Error al pasar Lista", Toast.LENGTH_SHORT).show()

            } else {
                // Toast.makeText(requireActivity(), "Se ha pasado lista", Toast.LENGTH_SHORT).show()
            }


        }
    }
        fun updateListaPasadaGrupoNoPasadaToPasada(idListaGrupo: Int, idProfe: Int) {

        val userCepApi = RetrofitClient.getInstance().create(ApiGets::class.java)

        GlobalScope.launch() {
            val call = userCepApi.UpdateEstadoPasarListaGrupo(idListaGrupo,idProfe)
            val response = call.execute()

        }

    }

    fun ocultarCargandoScreen(view: View) {
        var cargandoScreen = view.findViewById<LinearLayout>(R.id.cargandoLayout3)
        cargandoScreen.visibility = View.VISIBLE

        Handler().postDelayed({
            cargandoScreen.visibility = View.GONE
        }, 2000) // 2000 milisegundos = 2 segundos
    }

}
