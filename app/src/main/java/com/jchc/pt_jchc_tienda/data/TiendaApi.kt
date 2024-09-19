package com.jchc.pt_jchc_tienda.data

import com.jchc.pt_jchc_tienda.data.model.Producto
import com.jchc.pt_jchc_tienda.data.model.Tienda
import retrofit2.http.GET

interface TiendaApi {

    //FUNCIONES PARA LOS ENDPOINTS
    //https://fakestoreapi.com/products
    @GET("products")
    suspend fun getTienda(): Tienda

    //URL BASE DEL API
    companion object{
        const val BASE_URL = "https://fakestoreapi.com/"
    }

}