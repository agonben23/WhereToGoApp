package com.example.wheredatingapp

import android.content.Context
import android.widget.Toast

object AppToast{

    fun inDevelloping(context : Context){
        Toast.makeText(context, "En desarrollo", Toast.LENGTH_SHORT).show()
    }

}