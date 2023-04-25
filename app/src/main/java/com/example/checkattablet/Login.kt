package com.example.checkattablet

import android.content.Intent
import android.graphics.*
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import com.example.checkattablet.ApiAcces.ApiGets
import com.example.checkattablet.ApiAcces.RetrofitClient
import com.example.checkattablet.DataModel.Profe
import com.example.checkattablet.DataModel.UserCep
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking

class Login : AppCompatActivity() {

    companion object{
        lateinit var userLogin: UserCep
        lateinit var userProfe: Profe
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)


        val btnLogin = findViewById<Button>(R.id.btnLogin)
        val emailProfe = findViewById<EditText>(R.id.emailProfe)

        fun callApiUserCep(email:String) = runBlocking {
            var userLogged = globalFun1(email)
            if (userLogged == null){
                userLogin = UserCep(40001, "ERROR", "1",1)
                Toast.makeText(this@Login, "Error al consultar la Usercep", Toast.LENGTH_SHORT).show()
            }else{
                userLogin = userLogged
            }

        }

        fun callApiProfe(idUserCep: Int) = runBlocking {
            var ProfeLogged = globalFun3(idUserCep)
            if (ProfeLogged == null){
                userProfe = Profe(20021,"12345A", "ERROR");
                Toast.makeText(this@Login, "Error al consultar el Alumno", Toast.LENGTH_SHORT).show()
            }else{
                userProfe = ProfeLogged.get(0)
            }

        }

        btnLogin.setOnClickListener{

            callApiUserCep(emailProfe.text.toString())
            if (userLogin.tipoUser == 1){

                callApiProfe(userLogin.idUserCep)
                //profeNombreLogIn.text = userProfe.nombreProfe
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }

        }

        val imageView = findViewById<ImageView>(R.id.imageView)
        val bitmap = BitmapFactory.decodeResource(resources, R.drawable.alumno_ideal) // Cargar imagen desde drawable
        val diameter = 200 // Diámetro del círculo

        val scaledBitmap = Bitmap.createScaledBitmap(bitmap, diameter, diameter, true) // Escalar imagen al tamaño del círculo

        val circleBitmap = Bitmap.createBitmap(diameter, diameter, Bitmap.Config.ARGB_8888) // Crear nuevo bitmap para el círculo
        val canvas = Canvas(circleBitmap) // Dibujar en el nuevo bitmap
        val paint = Paint()
        paint.isAntiAlias = true
        paint.color = Color.WHITE
        canvas.drawCircle(diameter / 2f, diameter / 2f, diameter / 2f, paint) // Dibujar círculo blanco
        paint.xfermode = PorterDuffXfermode(PorterDuff.Mode.SRC_IN)
        canvas.drawBitmap(scaledBitmap, 0f, 0f, paint) // Dibujar imagen en el círculo

        imageView.setImageBitmap(circleBitmap) // Mostrar el bitmap del círculo en el ImageView
    }

    private suspend fun globalFun1(email :String ):UserCep? {

        val userCepApi = RetrofitClient.getInstance().create(ApiGets::class.java)

        return GlobalScope.async {
            val call = userCepApi.getUsuarioCep(email)
            val response = call.execute()
            response.body()
        }.await()
    }

    private suspend fun globalFun3(id_usuario_cep :Int):List<Profe>? {

        val userCepApi = RetrofitClient.getInstance().create(ApiGets::class.java)

        return GlobalScope.async {
            val call = userCepApi.getProfesorObject(id_usuario_cep)
            val response = call.execute()
            response.body()
        }.await()
    }

}