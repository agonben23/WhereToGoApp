package com.example.wheredatingapp

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

class RegistrationActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)

        val email : EditText = findViewById(R.id.editTextRegisterEmail)
        val usuario : EditText = findViewById(R.id.editTextRegisterUsuario)
        val password : EditText = findViewById(R.id.editTextRegisterPassword)

        val buttonLogin : Button = findViewById(R.id.buttonRegister)

        buttonLogin.setOnClickListener {
            registration(email.text.toString(),usuario.text.toString(), password.text.toString())
        }

    }

    private fun registration(email: String, nick: String, password: String) {

        if (email.isNotBlank() && nick.isNotBlank() && password.isNotBlank()) {

            val userBusqueda = Usuario(mail = email, nick = nick, password = password)

            val scope = CoroutineScope(Job() + Dispatchers.Main)
            val context = this

            scope.launch {
                val usuario = ReaderWeb.insertUsuario(userBusqueda)

                if(usuario != null){
                    Toast.makeText(context,"Usuario registrado correctamente",Toast.LENGTH_LONG).show()
                }else{
                    AppToast.registrationError(context)
                }


            }
        }else{
            AppToast.registrationError(this)
        }
    }


}