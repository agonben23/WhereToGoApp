package com.example.wheredatingapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.wheredatingapp.model.Lugar
import com.example.wheredatingapp.model.reader.ReaderWeb
import com.example.wheredatingapp.ui.theme.WhereDatingAppTheme
import kotlinx.coroutines.launch

class PlacesActivity : ComponentActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {

            WhereDatingAppTheme {

                PantallaLugares()

            }
        }


    }
}


    private suspend fun cargarLugares(ciudad: String) : List<Lugar>{

        var listaLugares = listOf<Lugar>()

        val newList = ReaderWeb.leerLugaresbyCiudad(ciudad)

        if (newList != null){
                listaLugares = newList
        }


        return listaLugares


}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaLugares(){

    val scope = rememberCoroutineScope()

    var listaLugares by remember {
        mutableStateOf(listOf<Lugar>())
    }

    Surface(modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background) {

        Column(horizontalAlignment = Alignment.CenterHorizontally) {

            var ciudad by remember {
                mutableStateOf("")
            }

            Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(top = 30.dp)) {
                Column(Modifier.weight(0.5f)) {
                    Text(text = "Buscar por ciudad")
                }
                Column(Modifier.weight(0.5f).padding(end = 10.dp)) {
                    TextField(value = ciudad, onValueChange = { newCiudad ->
                        ciudad = newCiudad
                    }
                    )
                }
                Column() {


                    Button(onClick =
                    {
                        scope.launch {
                            listaLugares = cargarLugares(ciudad)
                        }


                    }) {
                        Text(text = "Buscar")
                    }
                }
            }

            PlacesList(listaLugares = listaLugares)

        }
    }

}

@Preview(showBackground = true)
@Composable
fun PantallaLugaresPreview() {
    WhereDatingAppTheme {
        PantallaLugares()
      }
    }

@Composable
fun PlacesList(listaLugares : List<Lugar>){

    LazyColumn{

        items(listaLugares){
            Row() {
                Text(text = it.nombre)
                Text(text = it.descripcion)
            }
        }

    }

}