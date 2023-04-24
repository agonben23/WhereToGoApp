package com.example.wheredatingapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.wheredatingapp.model.Usuario
import com.example.wheredatingapp.model.reader.ReaderWeb
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val usuario : EditText = findViewById(R.id.editTextUsuario)
        val password : EditText = findViewById(R.id.editTextPassword)

        val buttonLogin : Button = findViewById(R.id.buttonLogin)

        val changePassword : TextView = findViewById(R.id.textViewChangePassword)
        val register : TextView = findViewById(R.id.textViewRegister)

        buttonLogin.setOnClickListener {
            login(usuario.text.toString(), password.text.toString())
        }

        changePassword.setOnClickListener {
            TODO("Crear Activity para el cambio de contraseña")
        }

        register.setOnClickListener {
            TODO("Crear Activity para el registro de usuarios")
        }



    }

    private fun login(nick : String, password: String){

        if (nick.isNotBlank() && password.isNotBlank()) {

            val userBusqueda = Usuario(nick = nick, password = password)

            val scope = CoroutineScope(Job() + Dispatchers.Main)

            var usuario: Usuario?

            scope.launch {
                usuario = ReaderWeb.buscarUsuario(userBusqueda)

                if (usuario != null) {

                    launchMain(usuario!!)

                } else {
                    toastUserNotFound()
                }
            }
        }else{
            toastUserNotFound()
        }

    }

    private fun toastUserNotFound(){
        Toast.makeText(this, "Datos incorrecto(s)", Toast.LENGTH_SHORT).show()
    }

    private fun launchMain(usuario: Usuario){
        val intent = Intent(this, MainActivity::class.java)

        intent.putExtra("nick",usuario.nick)
        intent.putExtra("mail",usuario.mail)
        intent.putExtra("password",usuario.password)

        startActivity(intent)

    }
}