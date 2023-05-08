package com.example.wheredatingapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView

class MenuActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)

        setUserConectado(intent.extras?.get("nick") as String)

        val opcion1 : LinearLayout = findViewById(R.id.linearLayoutOpcion1)

        opcion1.setOnClickListener {
            abrirVentana(R.string.menu_opcion1)
        }
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
        }

    }

}