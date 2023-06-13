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
import java.io.EOFException
import java.net.ConnectException
import java.net.SocketTimeoutException

class RegistrationActivity : AppCompatActivity() {

    private val context = this

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

            if(datosValidos(email,nick,password)){

              val userBusqueda = Usuario(mail = email, nick = nick, password = password)

              val scope = CoroutineScope(Job() + Dispatchers.Main)
              val context = this

              scope.launch {

                try {

                    ReaderWeb(context).insertUsuario(userBusqueda)

                    Toast.makeText(
                        context,
                        "Usuario registrado correctamente",
                        Toast.LENGTH_LONG
                    ).show()

                } catch (e: EOFException) {
                    AppToast.registrationError(context)
                } catch (e : SocketTimeoutException){
                    AppToast.noInternetConnection(context)
                }catch (e: ConnectException){
                    AppToast.noInternetConnection(context)
                }
              }

            }
        }else{
            AppToast.registrationError(this)
        }
    }

    private fun datosValidos(email: String,nick: String,password: String) : Boolean{

        if(password.length < 5){

            Toast.makeText(context,"Contraseña no válida",Toast.LENGTH_SHORT).show()

            return false

        }else if(nick.contains("{}^[]()#") || nick.length < 5){

            Toast.makeText(context,"Nick de usuario no válido",Toast.LENGTH_SHORT).show()

            return false
        }else if(!emailValido(email)){

            Toast.makeText(context,"email no válido",Toast.LENGTH_SHORT).show()

            return false
        }else{
            return true
        }

    }

    private fun emailValido(email: String) : Boolean{
        val regexEmail = Regex("^[A-Za-z](.*)(@)(.{1,})(\\.)(.{1,})")

        return email.matches(regexEmail)
    }


}