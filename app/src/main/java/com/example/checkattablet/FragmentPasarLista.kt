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

        var alumnoId: ListaAlumnos? = null

        val radioGroup = view.findViewById<RadioGroup>(R.id.myRadioGroup)

        val listaAlumno = mutableListOf<ListaAlumnos>(
            ListaAlumnos(1, "Marc Alzamora Lazaro", ""),
            ListaAlumnos(2, "Mario Leiva Torres", ""),
            ListaAlumnos(3, "Joel Marcos Cano", ""),
            ListaAlumnos(4, "Rachid Ghenem Arias", ""),
            ListaAlumnos(5, "Joaquin Custodio Valderas", ""),
            ListaAlumnos(6, "Raul Lendines Ramos", ""),
            ListaAlumnos(7, "Marc Alzamora Lazaro", ""),
            ListaAlumnos(8, "Mario Leiva Torres", ""),
            ListaAlumnos(9, "Joel Marcos Cano", ""),
            ListaAlumnos(10, "Rachid Ghenem Arias", ""),
            ListaAlumnos(11, "Joaquin Custodio Valderas", ""),
            ListaAlumnos(12, "Raul Lendines Ramos", "")
        )

        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerView)

        val adapter = ListaAlumnosAdaptador(requireContext(), listaAlumno)
        recyclerView.hasFixedSize()
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter

        adapter.setOnClickListener {

            val alumnoSeleccionado = listaAlumno[recyclerView.getChildAdapterPosition(it)]
            alumnoId = listaAlumno.find { ListaAlumnos -> ListaAlumnos.idAlumno.equals(alumnoSeleccionado.idAlumno) }
            adapter.selectedItem = alumnoSeleccionado
            adapter.notifyDataSetChanged()

            when(alumnoSeleccionado.asistencia){
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
                else ->{
                    radioGroup.clearCheck()
                }
            }


        }


        radioGroup.setOnCheckedChangeListener { group, checkedId ->
            val radioButton = view.findViewById<RadioButton>(checkedId)
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

            }
        }



    }
}
