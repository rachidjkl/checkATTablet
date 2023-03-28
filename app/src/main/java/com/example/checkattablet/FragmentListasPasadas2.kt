package com.example.checkattablet

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
import org.w3c.dom.Text
import java.util.*


class FragmentListasPasadas2 : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflar el layout del fragmento


        return inflater.inflate(R.layout.fragment_listas_pasadas2, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val fechaActual2 = view.findViewById<TextView>(R.id.fechaActual2)
        val cal = Calendar.getInstance()
        fechaActual2.text = "${cal.get(Calendar.DAY_OF_MONTH)}/${cal.get(Calendar.MONTH) + 1}/${cal.get(Calendar.YEAR)}"

        val grupo = arguments?.getSerializable("listaGrupo") as? ListaGrupo
        var nombreProfe = view.findViewById<TextView>(R.id.nombreProfesor)
        nombreProfe.text = grupo?.profe

        val textViewHorasSeleccionadas = view.findViewById<TextView>(R.id.textViewHorasSeleccionadas)
        textViewHorasSeleccionadas.text = "Hora : " + grupo?.horaInicio + " - " + grupo?.horaFinal

        val btnVolver = view.findViewById<Button>(R.id.btnVolver)

        btnVolver.setOnClickListener{
            val fragmentManager = requireActivity().supportFragmentManager
            fragmentManager.popBackStack()
        }

        val listaAlumno = mutableListOf<ListaAlumnos>(
            ListaAlumnos(1, "Marc Alzamora Lazaro", "P"),
            ListaAlumnos(2, "Mario Leiva Torres", "P"),
            ListaAlumnos(3, "Joel Marcos Cano", "R"),
            ListaAlumnos(4, "Rachid Ghenem Arias", "FI"),
            ListaAlumnos(5, "Joaquin Custodio Valderas", "FJ"),
            ListaAlumnos(6, "Raul Lendines Ramos", "Irse antes de acabar"),
            ListaAlumnos(7, "Marc Alzamora Lazaro", "P"),
            ListaAlumnos(8, "Mario Leiva Torres", "P"),
            ListaAlumnos(9, "Joel Marcos Cano", "R"),
            ListaAlumnos(10, "Rachid Ghenem Arias", "FI"),
            ListaAlumnos(11, "Joaquin Custodio Valderas", "FJ"),
            ListaAlumnos(12, "Raul Lendines Ramos", "Irse antes de acabar"),
        )

        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerViewListasPasadas2)

        val adapter = ListaAlumnosAdaptador(requireContext(), listaAlumno)
        recyclerView.hasFixedSize()
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter

        adapter.setOnClickListener {
            // Update the selected item and refresh the view
            val grupo = listaAlumno[recyclerView.getChildAdapterPosition(it)]
            adapter.selectedItem = grupo
            adapter.notifyDataSetChanged()
        }

    }

}