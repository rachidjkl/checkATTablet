package com.example.checkattablet

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
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


        var alumnoId: ListaAlumnosListasPasadas? = null

        val radioGroup = view.findViewById<RadioGroup>(R.id.myRadioGroup)

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

        val listaAlumno = mutableListOf<ListaAlumnosListasPasadas>(
            ListaAlumnosListasPasadas(1, "Marc Alzamora Lazaro", ""),
            ListaAlumnosListasPasadas(2, "Mario Leiva Torres", "P"),
            ListaAlumnosListasPasadas(3, "Joel Marcos Cano", "R"),
            ListaAlumnosListasPasadas(4, "Rachid Ghenem Arias", "FI"),
            ListaAlumnosListasPasadas(5, "Joaquin Custodio Valderas", "FJ"),
            ListaAlumnosListasPasadas(6, "Raul Lendines Ramos", "Irse antes de acabar"),
            ListaAlumnosListasPasadas(7, "Marc Alzamora Lazaro", ""),
            ListaAlumnosListasPasadas(8, "Mario Leiva Torres", "P"),
            ListaAlumnosListasPasadas(9, "Joel Marcos Cano", "R"),
            ListaAlumnosListasPasadas(10, "Rachid Ghenem Arias", "FI"),
            ListaAlumnosListasPasadas(11, "Joaquin Custodio Valderas", "FJ"),
            ListaAlumnosListasPasadas(12, "Raul Lendines Ramos", "Irse antes de acabar"),
        )

        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerViewListasPasadas2)

        val adapter = ListaAlumnosListasPasadasAdaptador(requireContext(), listaAlumno)
        recyclerView.hasFixedSize()
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter

        adapter.setOnClickListener {
            // Update the selected item and refresh the view
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
            }
        }

        radioGroup.setOnCheckedChangeListener { group, checkedId ->
            val radioButton = view.findViewById<RadioButton>(checkedId)
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
            }
            adapter.notifyDataSetChanged()
        }

    }
}