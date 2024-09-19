package com.jchc.pt_jchc_tienda.data

import com.jchc.pt_jchc_tienda.data.model.Producto
import kotlinx.coroutines.flow.Flow

//Esta interfaz accede a TiendaApi y a su función getTienda()
interface TiendaRepository {
    suspend fun getTienda(): Flow<Resultado<List<Producto>>> //Flow ayuda a realizar llamadas asíncronas
                                                            //para recoger una lista de Producto
}