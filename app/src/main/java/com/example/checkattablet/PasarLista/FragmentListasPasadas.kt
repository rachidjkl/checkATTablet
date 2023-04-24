package com.example.checkattablet.PasarLista

import android.app.DatePickerDialog
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.checkattablet.*
import com.example.checkattablet.ApiAcces.ApiGets
import com.example.checkattablet.ApiAcces.RetrofitClient
import com.example.checkattablet.DataModel.Horario
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.TextStyle
import java.util.*


class FragmentListasPasadas : Fragment() {

    companion object{
        lateinit var listaHorariosDiaClase: List<Horario>
        lateinit var  diaSemanaBusqueda: String
        var fecha: String = ""
    }


    fun callApiUserCep(diaSemana: String) = runBlocking {
        var listHoraios = globalFun1(diaSemana)
        if (listHoraios == null) {

            Toast.makeText(requireActivity(), "Error al consultar los Horarios", Toast.LENGTH_SHORT).show()
        } else {
            listaHorariosDiaClase = listHoraios
        }
    }
    fun convertirFecha(fecha: String): String {
        val formatoEntrada = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            DateTimeFormatter.ofPattern("d/M/yyyy")
        } else {
            TODO("VERSION.SDK_INT < O")
        }
        val fechaEntrada = LocalDate.parse(fecha, formatoEntrada)
        val formatoSalida = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        return fechaEntrada.format(formatoSalida)
    }



        override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflar el layout del fragmento


        return inflater.inflate(R.layout.fragment_listas_pasadas, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val refreshButton = view.findViewById<Button>(R.id.refresh)
        val datePickerButton = view.findViewById<Button>(R.id.date_picker_button)
        val fechaActual = view.findViewById<TextView>(R.id.fechaActual)
        val cal = Calendar.getInstance()


            // Obtiene la fecha actual
            val year = cal.get(Calendar.YEAR)
            val month = cal.get(Calendar.MONTH)
            val dayOfMonth = cal.get(Calendar.DAY_OF_MONTH)

            // Crea un objeto Calendar para la fecha actual
            val currentCalendar = Calendar.getInstance()
            currentCalendar.set(year, month, dayOfMonth)

            // Obtiene el nombre del día de la semana correspondiente a la fecha actual en español
            var currentDayOfWeek = currentCalendar.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale("es", "ES"))


            datePickerButton.text = "${dayOfMonth}/${month + 1}/${year}"
            fechaActual.text = "Fecha Actual ${dayOfMonth}/${month + 1}/${year} - $currentDayOfWeek"

            if (fecha == ""){
                fecha = String.format("%04d-%02d-%02d", year, month + 1, dayOfMonth)
            }






        //DELETE THIS SHIT
        //fecha = "2023-04-17"
        //currentDayOfWeek = "lunes"
        callApiUserCep(currentDayOfWeek)


        var recyclerView = view.findViewById<RecyclerView>(R.id.recyclerViewListasPasadas)

        var adapter = ListasPasadasAdaptador(requireContext(), listaHorariosDiaClase,fecha)
        recyclerView.hasFixedSize()
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter

        refreshButton.setOnClickListener(){
            callApiUserCep(diaSemanaBusqueda)
            val updatedAdapter = ListasPasadasAdaptador(requireContext(), listaHorariosDiaClase, fecha)
            recyclerView.adapter = updatedAdapter
            updatedAdapter.notifyDataSetChanged()
            updatedAdapter.setOnClickListener {

                var horarioPasarLista = listaHorariosDiaClase[recyclerView.getChildAdapterPosition(it)]
                val bundle = Bundle()
                bundle.putSerializable("horarioPasarLista", horarioPasarLista)
                bundle.putSerializable("fecha", fecha)

                val fragmentoPasarLista = FragmentPasarLista()
                fragmentoPasarLista.arguments = bundle
                val fragmentManager = requireActivity().supportFragmentManager
                val fragmentTransaction = fragmentManager.beginTransaction()

                // Agregar el fragmento actual a la pila de fragmentos
                fragmentTransaction.addToBackStack(null)

                // Reemplazar el fragmento actual con el FragmentListasPasadas2
                fragmentTransaction.replace(R.id.fragmentooo, fragmentoPasarLista)
                fragmentTransaction.commit()
            }
        }

        adapter.setOnClickListener {

            var horarioPasarLista = listaHorariosDiaClase[recyclerView.getChildAdapterPosition(it)]
            val bundle = Bundle()
            bundle.putSerializable("horarioPasarLista", horarioPasarLista)
            bundle.putSerializable("fecha", fecha)

            val fragmentoPasarLista = FragmentPasarLista()
            fragmentoPasarLista.arguments = bundle
            val fragmentManager = requireActivity().supportFragmentManager
            val fragmentTransaction = fragmentManager.beginTransaction()

            // Agregar el fragmento actual a la pila de fragmentos
            fragmentTransaction.addToBackStack(null)

            // Reemplazar el fragmento actual con el FragmentListasPasadas2
            fragmentTransaction.replace(R.id.fragmentooo, fragmentoPasarLista)
            fragmentTransaction.commit()
        }

        datePickerButton.setOnClickListener {

            val datePicker = DatePickerDialog(
                requireContext(),
                { _, selectedYear, selectedMonth, selectedDayOfMonth ->
                    // Aquí se ejecutará el código cuando el usuario seleccione una fecha
                    val selectedDate = "${selectedDayOfMonth}/${selectedMonth + 1}/${selectedYear}"

                    // Crea un objeto Calendar para la fecha seleccionada
                    val selectedCalendar = Calendar.getInstance()
                    selectedCalendar.set(selectedYear, selectedMonth, selectedDayOfMonth)

                    // Obtiene el nombre del día de la semana correspondiente a la fecha seleccionada
                    val selectedDayOfWeek = selectedCalendar.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.getDefault())


                    fechaActual.text = "Fecha Actual ${selectedDate} - ${selectedDayOfWeek}"
                    datePickerButton.text = selectedDate

                    fecha = convertirFecha(selectedDate)
                    diaSemanaBusqueda = obtenerDiaSemana(fecha)
                },
                year, month, dayOfMonth

            )
            datePicker.show()

        }


    }

    fun obtenerDiaSemana(fecha: String): String {
        val formatoEntrada = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            DateTimeFormatter.ofPattern("yyyy-MM-dd")
        } else {
            TODO("VERSION.SDK_INT < O")
        }
        val fechaLocal = LocalDate.parse(fecha, formatoEntrada)
        return fechaLocal.dayOfWeek.getDisplayName(TextStyle.FULL, Locale("es", "ES"))
    }

        private suspend fun globalFun1(diaSemana :String ):List<Horario>? {

            val userCepApi = RetrofitClient.getInstance().create(ApiGets::class.java)

            return GlobalScope.async {
                val call = userCepApi.getHorarios(130002,diaSemana)
                val response = call.execute()
                response.body()
            }.await()
        }





}