package com.example.checkattablet

import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.navigation.fragment.findNavController
import com.example.checkattablet.ApiAcces.ApiGets
import com.example.checkattablet.ApiAcces.RetrofitClient
import com.example.checkattablet.DataModel.ClasePers
import com.example.checkattablet.PasarLista.AdapterClases
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.util.*


class FragmentAjustes : Fragment() {

    private var allClases: List<ClasePers>? = null

    init {
        main()
    }
    fun main() = runBlocking {
        allClases = globalFun()
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {





















        return inflater.inflate(R.layout.fragment_ajustes, container, false)
    }


















    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val btnEspanol = view.findViewById<Button>(R.id.btnEspanol)
        val btnCatalan = view.findViewById<Button>(R.id.btnCatalan)
        val btnIngles = view.findViewById<Button>(R.id.btnIngles)

        btnEspanol.setOnClickListener {
            btnEspanol.setBackgroundResource(R.drawable.btn_seleccionado)
            btnCatalan.setBackgroundResource(R.drawable.btn_adicional)
            btnIngles.setBackgroundResource(R.drawable.btn_adicional)
            val locale = Locale("es")
            Locale.setDefault(locale)
            val config = Configuration()
            config.locale = locale
            resources.updateConfiguration(config, resources.displayMetrics)
            val intent = Intent(requireContext(), MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
            requireActivity().finish()
        }
        btnCatalan.setOnClickListener {
            btnEspanol.setBackgroundResource(R.drawable.btn_adicional)
            btnCatalan.setBackgroundResource(R.drawable.btn_seleccionado)
            btnIngles.setBackgroundResource(R.drawable.btn_adicional)
            val locale = Locale("ca")
            Locale.setDefault(locale)
            val config = Configuration()
            config.locale = locale
            resources.updateConfiguration(config, resources.displayMetrics)
            val intent = Intent(requireContext(), MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
            requireActivity().finish()
        }
        btnIngles.setOnClickListener {
            btnEspanol.setBackgroundResource(R.drawable.btn_adicional)
            btnCatalan.setBackgroundResource(R.drawable.btn_adicional)
            btnIngles.setBackgroundResource(R.drawable.btn_seleccionado)
            val locale = Locale("en")
            Locale.setDefault(locale)
            val config = Configuration()
            config.locale = locale
            resources.updateConfiguration(config, resources.displayMetrics)
            val intent = Intent(requireContext(), MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
            requireActivity().finish()
        }
    }



    private suspend fun globalFun(): List<ClasePers>? {
        val userCepApi = RetrofitClient.getInstance().create(ApiGets::class.java)
        return GlobalScope.async {
            val call = userCepApi.getAllClasses()
            val response = call.execute()
            response.body()
        }.await()
    }


}