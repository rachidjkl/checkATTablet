package com.example.checkattablet

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


class FragmentPasarLista : Fragment() {

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

        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerView)

        val adapter = ListaAlumnosAdaptador(requireContext(), listaAlumno)
        recyclerView.hasFixedSize()
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter

        adapter.setOnClickListener {
            // Update the selected item and refresh the view
            val grupo = listaAlumno[recyclerView.getChildAdapterPosition(it)]
            adapter.selectedItem = grupo
            adapter.notifyDataSetChanged()

            // ...
        }

        radioGroup.setOnCheckedChangeListener { group, checkedId ->
            val radioButton = view.findViewById<RadioButton>(checkedId)
            /*when(radioButton.text.toString()) {
                "Presente" -> {
                    adapter.selectedRadioButtonText = "P"
                }
                "Retraso" -> {
                    adapter.selectedRadioButtonText = "R"
                }
                "Falta Justificada" -> {
                    adapter.selectedRadioButtonText = "FJ"
                }
                "Falta Injustificada" -> {
                    adapter.selectedRadioButtonText = "FI"
                }
                "Irse antes de acabar" -> {
                    adapter.selectedRadioButtonText = "Irse antes de acabar"
                }
            }*/

            //adapter.notifyDataSetChanged()
        }


    }
}
