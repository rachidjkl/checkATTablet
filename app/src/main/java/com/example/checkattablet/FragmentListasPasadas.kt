package com.example.checkattablet

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView


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

        val listaGrupo = mutableListOf<ListaGrupo>(
            ListaGrupo(1, "8:40", "9:40", "M010-UF2", "Paz Rueda", true),
            ListaGrupo(2, "9:40", "10:40", "M08-UF2", "Jose Luis", true),
            ListaGrupo(3, "10:40", "11:40", "M04-UF2", "Francisco Fernandez", true),
            ListaGrupo(4, "12:00", "13:00", "M013", "Marcos Jimenez", false),
            ListaGrupo(5, "13:00", "14:00", "M013", "Marcos Jimenez", false),
        )

        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerViewListasPasadas)

    }

}