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
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.util.*


class FragmentListasPasadas : Fragment() {

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
        datePickerButton.text = "${cal.get(Calendar.DAY_OF_MONTH)}/${cal.get(Calendar.MONTH) + 1}/${cal.get(Calendar.YEAR)}"
        fechaActual.text = "Fecha Actual ${cal.get(Calendar.DAY_OF_MONTH)}/${cal.get(Calendar.MONTH) + 1}/${cal.get(Calendar.YEAR)}"

        datePickerButton.setOnClickListener {

            val year = cal.get(Calendar.YEAR)
            val month = cal.get(Calendar.MONTH)
            val dayOfMonth = cal.get(Calendar.DAY_OF_MONTH)

            val datePicker = DatePickerDialog(
                requireContext(),
                { _, selectedYear, selectedMonth, selectedDayOfMonth ->
                    // Aquí se ejecutará el código cuando el usuario seleccione una fecha
                    val selectedDate = "${selectedDayOfMonth}/${selectedMonth + 1}/${selectedYear}"
                    datePickerButton.text = selectedDate
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

        val adapter = ListasPasadasAdaptador(requireContext(), listaGrupo)
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

}