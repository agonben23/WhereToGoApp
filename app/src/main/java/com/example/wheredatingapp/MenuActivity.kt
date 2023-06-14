package com.example.wheredatingapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView

/**
 * Es la pantalla del menú principal
 */
class MenuActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)

        setUserConectado(intent.extras?.get("nick") as String)

        val opcion1 : LinearLayout = findViewById(R.id.linearLayoutOpcion1)
        val opcion2 : LinearLayout = findViewById(R.id.linearLayoutOpcion2)
        val opcion3 : LinearLayout = findViewById(R.id.linearLayoutOpcion3)
        val opcion4 : LinearLayout = findViewById(R.id.linearLayoutOpcion4)

        opcion1.setOnClickListener {
            abrirVentana(R.string.menu_opcion1)
        }

        opcion2.setOnClickListener {
            abrirVentana(R.string.menu_opcion2)
        }
        opcion3.setOnClickListener { AppToast.inDevelloping(this) }
        opcion4.setOnClickListener { abrirVentana(R.string.salir) }
    }

    private fun setUserConectado(nick : String){
        val textView : TextView = findViewById(R.id.textViewUserConectado)

        textView.text = "Usuario conectado : $nick"
    }

    private fun abrirVentana(opcion : Int){

        when(opcion){
            R.string.menu_opcion1 -> {

                val intent: Intent = Intent(this,SearchMidpointCityActivity::class.java)
                startActivity(intent)

            }

            R.string.menu_opcion2 -> {

                val intent : Intent = Intent(this,PlacesActivity::class.java)
                startActivity(intent)

            }

            R.string.salir ->{
                val intent = Intent(this,LoginActivity::class.java)
                startActivity(intent)
            }
        }

    }

}