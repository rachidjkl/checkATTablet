package com.example.checkattablet

import androidx.lifecycle.ViewModel

class SharedViewModel: ViewModel() {
    val listaGrupo: MutableList<ListaGrupo> = mutableListOf()
}