package com.jchc.pt_jchc_tienda

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
//import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.size.Size
import com.jchc.pt_jchc_tienda.data.TiendaRepositoryImpl
import com.jchc.pt_jchc_tienda.data.model.Producto
import com.jchc.pt_jchc_tienda.presentation.ProductosViewModel
import com.jchc.pt_jchc_tienda.ui.theme.PT_jchc_tiendaTheme
import kotlinx.coroutines.flow.collectLatest

class MainActivity : ComponentActivity() {

    private val viewModel by viewModels<ProductosViewModel>(factoryProducer = {
        object: ViewModelProvider.Factory{
            override fun <T: ViewModel> create(modelClass: Class<T>): T {
                return ProductosViewModel(TiendaRepositoryImpl(InstanciaRetrofit.api)) as T
            }
        }
    })

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PT_jchc_tiendaTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val tienda = viewModel.productos.collectAsState().value
                    val context = LocalContext.current
                    //Utilizamos Flow para conseguir la información, en caso
                    //de que no esté disponible, se lanza un Toast
                    LaunchedEffect(key1 = viewModel.showErrorToastChannel){
                        viewModel.showErrorToastChannel.collectLatest { show->
                            if (show){
                                Toast.makeText(
                                    context, "ERROR AL RECUPERAR DATOS", Toast.LENGTH_LONG
                                ).show()
                            }
                        }
                    }

                    if(tienda.isEmpty()){ //Mientras la información no haya cargado, se muestra un Progress Indicator
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator()
                        }
                    }else{ //De lo contrario se muestra la interfaz compose
                            // Producto() que recibe la información del API
                        LazyColumn(
                            modifier = Modifier.fillMaxSize(),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            contentPadding = PaddingValues(16.dp)
                        ){
                            items(tienda.size){index ->
                                Producto(tienda[index]) //El compose del UI maneja un objeto a la vez, por lo que
                                                        //está dentro de un loop para todos los elementos de "tienda"
                            }
                        }
                    }

                }
            }

        }
    }
}

