package com.example.wheredatingapp

import android.content.Context
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
import java.io.EOFException
import java.net.ConnectException
import java.net.SocketTimeoutException

/**
 * Es la pantalla de inicio, donde se encuentra el login y el acceso al registro
 */
class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val context = this

        val usuario : EditText = findViewById(R.id.editTextUsuario)
        val password : EditText = findViewById(R.id.editTextPassword)

        val buttonLogin : Button = findViewById(R.id.buttonLogin)

        val changePassword : TextView = findViewById(R.id.textViewChangePassword)
        val register : TextView = findViewById(R.id.textViewRegister)

        buttonLogin.setOnClickListener {
            login(context,usuario.text.toString(), password.text.toString())
        }

        changePassword.setOnClickListener {
            AppToast.inDevelloping(context)
        }

        register.setOnClickListener {
            registration()
        }



    }

    private fun registration() {
        val intent = Intent(this,RegistrationActivity::class.java)
        startActivity(intent)
    }

    private fun login(context: Context, nick : String, password: String){

        if (nick.isNotBlank() && password.isNotBlank()) {

            val userBusqueda = Usuario(nick = nick, password = password)

            val scope = CoroutineScope(Job() + Dispatchers.Main)

                scope.launch {

                    try {

                        val usuario = ReaderWeb(context).buscarUsuario(userBusqueda)

                        if (usuario != null) {

                            launchMain(
                                usuario
                            )

                        } else {
                            AppToast.userNotFound(context)
                        }
                    } catch (e: EOFException) {
                        AppToast.userNotFound(context)
                    } catch (e : SocketTimeoutException){
                        AppToast.noInternetConnection(context)
                    } catch (e : ConnectException){
                        AppToast.noInternetConnection(context)
                    }
                }

        }else{
            AppToast.userNotFound(context)
        }

    }

    private fun launchMain(
        usuario: Usuario
    ){
        val intent = Intent(this, MenuActivity::class.java)

        intent.putExtra("nick",usuario.nick)
        intent.putExtra("mail",usuario.mail)
        intent.putExtra("password",usuario.password)


        startActivity(intent)

    }
}