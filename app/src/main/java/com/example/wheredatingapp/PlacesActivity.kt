package com.example.wheredatingapp

import android.content.Context
import android.inputmethodservice.Keyboard.Row
import android.os.Bundle
import android.widget.GridLayout
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import com.example.wheredatingapp.model.Ciudad
import com.example.wheredatingapp.model.Lugar
import com.example.wheredatingapp.model.reader.ReaderWeb
import com.example.wheredatingapp.ui.theme.WhereDatingAppTheme
import kotlinx.coroutines.launch
import java.net.ConnectException
import java.net.SocketTimeoutException

class PlacesActivity : ComponentActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {

            WhereDatingAppTheme {

                PantallaLugares(this)

            }
        }


    }
}


    private suspend fun cargarLugares(context: Context, ciudad: String) : List<Lugar>{

        var listaLugares = listOf<Lugar>()

        val newList = ReaderWeb(context).leerLugaresbyCiudad(ciudad)

        if (newList != null){

            if (newList.isNotEmpty()) {

                listaLugares = newList

            }else{
                AppToast.lugaresNotFound(context)
            }
        }

        return listaLugares


}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaLugares(context: Context){

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

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(top = 30.dp)
            ) {


                Text(text = "Buscar por ciudad ", modifier = Modifier.padding(start = 15.dp))
            }

            RowSeparator(separator = 5)

            Row() {


                TextField(
                    value = ciudad, onValueChange = { newCiudad ->
                        ciudad = newCiudad
                    },
                    modifier = Modifier.padding(horizontal = 15.dp)
                )


            }



            RowSeparator(separator = 15)

            Row() {
                Button(onClick = {

                    try {
                        scope.launch {
                            listaLugares = cargarLugares(context, ciudad)
                        }
                    } catch (e: SocketTimeoutException) {
                        AppToast.noInternetConnection(context)
                    } catch (e: ConnectException) {
                        AppToast.noInternetConnection(context)
                    }
                }) {
                    Text(text = "Buscar")
                }
            }




            PlacesList(listaLugares = listaLugares)
         }
        }
    }



@Composable
fun PlacesList(listaLugares : List<Lugar>){

    LazyColumn{

        items(listaLugares){

            RowSeparator(25)
            
            Row() {

                BoxPlace(lugar = it)
            }
        }

    }

}

@Composable
fun BoxPlace(lugar: Lugar){

    Box(modifier = Modifier
        .padding(horizontal = 10.dp)
        .border(1.dp, color = Color.Black)) {

        Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.padding(all = 20.dp)) {

            Row() {
                Text(text = lugar.nombre, fontWeight = FontWeight.Bold)
            }

            RowSeparator(separator = 5)

            Row {

                AsyncImage(
                        model = lugar.uriImagen,
                        contentDescription = null,
                        modifier = Modifier.size(200.dp)
                )


            }

            RowSeparator(separator = 5)

            Row() {

                Text(text = lugar.descripcion)

            }



        }

    }
}