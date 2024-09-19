package com.jchc.pt_jchc_tienda

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.size.Size
import com.jchc.pt_jchc_tienda.data.model.Producto

//UI diseñada con Compose para la pantalla que muestra los objetos regresados por el API

@Composable
fun Producto(producto: Producto){

    val imageState = rememberAsyncImagePainter(
        model = ImageRequest.Builder(LocalContext.current).data(producto.image)
            .size(Size.ORIGINAL).build()
    ).state

    //Ponemos una bandera para ver si el artículo está expandido o no
    var isExpanded by remember{ mutableStateOf(false) }

    //surfaceColor se actualizará gradualmente de un color a otro
    val surfaceColor by animateColorAsState(
        if (isExpanded) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surface,
    )

    Column(
        modifier = Modifier
            .clip(RoundedCornerShape(15.dp))
            .padding(
                vertical = 5.dp
            )
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.primaryContainer)
            .clickable { isExpanded = !isExpanded },
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Text( //Título del producto
            modifier = Modifier.padding(
                horizontal = 15.dp,
                vertical = 15.dp
            ),
            text = "${producto.title}",
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp,
            textAlign = TextAlign.Center
            )

        if(imageState is AsyncImagePainter.State.Error){
            Box( //Si alguna imagen no puede cargar, se muestra un Progress Indicator
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp),
                contentAlignment = Alignment.Center
            ){
                CircularProgressIndicator()
            }
        }
        if(imageState is AsyncImagePainter.State.Success){
            Image( //Imagen del producto
                painter = imageState.painter,
                modifier= Modifier
                    .fillMaxWidth()
                    .height(250.dp),
                contentDescription = producto.title
            )
        }

        Spacer(modifier = Modifier.height(10.dp))

        Text( //Texto del precio
            modifier = Modifier.padding(
                horizontal = 15.dp
            ),
            text = "PRECIO : $${producto.price} USD",
            fontSize = 18.sp
        )

        //Spacer(modifier = Modifier.height(7.dp))

        Text(
            text = "Categoría: ${producto.category}",
            fontSize = 13.sp
        )

        Surface(
            shape= MaterialTheme.shapes.medium,
            color = surfaceColor, //el color de surfaceColor cambiará gradualmente del primario al de surface
            modifier = Modifier
                .animateContentSize()
                .padding(12.dp)
                .clip(RoundedCornerShape(10.dp))
        ){
            Text(
                modifier = Modifier.padding( //texto de la descripción del producto
                    horizontal = 15.dp,
                    vertical = 10.dp),
                text = if(isExpanded){
                    "${producto.desc}"
                } else{
                      "Ver más"
                      },
                textAlign = TextAlign.Justify,
                maxLines = if(isExpanded) Int.MAX_VALUE else 1, //Si isExpanded == true, se muestran todas las líneas
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

