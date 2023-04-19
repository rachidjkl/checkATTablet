package com.example.checkattablet

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.checkattablet.ApiAcces.ApiGets
import com.example.checkattablet.ApiAcces.RetrofitClient
import com.example.checkattablet.DataModel.Horario
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
import java.util.*


class FragmentListasPasadas : Fragment() {

    companion object{
        lateinit var listaHorariosDiaClase: List<Horario>
    }

    fun callApiUserCep(diaSemana: String) = runBlocking {
        var listHoraios = globalFun1(diaSemana)
        if (listHoraios == null) {

            Toast.makeText(requireActivity(), "Error al consultar los Horarios", Toast.LENGTH_SHORT).show()
        } else {
            listaHorariosDiaClase = listHoraios
        }
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
        val currentDayOfWeek = currentCalendar.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale("es", "ES"))

        datePickerButton.text = "${dayOfMonth}/${month + 1}/${year}"
        fechaActual.text = "Fecha Actual ${dayOfMonth}/${month + 1}/${year} - $currentDayOfWeek"

        callApiUserCep(currentDayOfWeek)

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

                    datePickerButton.text = selectedDate
                    fechaActual.text = "Fecha Actual ${selectedDate} - ${selectedDayOfWeek}"
                },
                year, month, dayOfMonth
            )

            datePicker.show()
        }

        val listaGrupo = mutableListOf<ListaGrupo>(
            ListaGrupo(1, "8:40", "9:40", "M010", "UF2", "Paz", true),
            ListaGrupo(2, "9:40", "10:40", "M08", "UF5", "Jose Luis", true),
            ListaGrupo(3, "10:40", "11:40", "M04", "UF3", "Francisco", true),
            ListaGrupo(4, "12:00", "13:00", "M013", "UF1", "Marcos", false),
            ListaGrupo(5, "13:00", "14:00", "M013", "UF1", "Marcos", false),
        )

        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerViewListasPasadas)

        val adapter = ListasPasadasAdaptador(requireContext(), listaHorariosDiaClase)
        recyclerView.hasFixedSize()
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter

        adapter.setOnClickListener {
            val grupo = listaGrupo[recyclerView.getChildAdapterPosition(it)]
            val bundle = Bundle()
            bundle.putSerializable("listaGrupo", grupo)
            val fragmentoPasarLista = FragmentListasPasadas2()
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

        private suspend fun globalFun1(diaSemana :String ):List<Horario>? {

            val userCepApi = RetrofitClient.getInstance().create(ApiGets::class.java)

            return GlobalScope.async {
                val call = userCepApi.getHorarios(130002,diaSemana)
                val response = call.execute()
                response.body()
            }.await()
        }

    fun obtenerDiaDeLaSemana(): String {
        val calendar = Calendar.getInstance()
        val diaDeLaSemana = calendar.get(Calendar.DAY_OF_WEEK)

        when (diaDeLaSemana) {
            Calendar.SUNDAY -> return "Domingo"
            Calendar.MONDAY -> return "Lunes"
            Calendar.TUESDAY -> return "Martes"
            Calendar.WEDNESDAY -> return "Miércoles"
            Calendar.THURSDAY -> return "Jueves"
            Calendar.FRIDAY -> return "Viernes"
            Calendar.SATURDAY -> return "Sábado"
            else -> return "Error"
        }
    }



}