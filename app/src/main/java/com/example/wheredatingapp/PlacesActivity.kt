package com.example.wheredatingapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
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
import com.example.wheredatingapp.model.Ciudad
import com.example.wheredatingapp.model.Lugar
import com.example.wheredatingapp.model.reader.ReaderWeb
import com.example.wheredatingapp.ui.theme.WhereDatingAppTheme
import kotlinx.coroutines.launch

class PlacesActivity : ComponentActivity() {

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {

            var listaLugares by remember { mutableStateOf(listOf<Lugar>()) }

            WhereDatingAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {

                    Column(horizontalAlignment = Alignment.CenterHorizontally) {

                        var ciudad by remember {
                            mutableStateOf("")
                        }

                        Row() {
                            Text(text = "Busca por ciudad :")
                            TextField(value = ciudad, onValueChange = { newCiudad ->
                                ciudad = newCiudad
                            })
                            Button(onClick = {

                                listaLugares = cargarLugares(ciudad = ciudad)

                            }) {
                                Text(text = "Buscar")
                            }
                        }

                        PlacesList(listaLugares = listaLugares)

                    }

                }
            }
        }


    }
}

    @Composable
    private fun cargarLugares(ciudad: String) : List<Lugar>{

        val scope = rememberCoroutineScope()

        var listaLugares = listOf<Lugar>()

        scope.launch {

            val newList = ReaderWeb.leerLugaresbyCiudad(ciudad)

            if (newList != null){
                listaLugares = newList
            }
        }

        return listaLugares


}

@Composable
fun PantallaLugares(){

    

}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    WhereDatingAppTheme {
        Greeting("Android")
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