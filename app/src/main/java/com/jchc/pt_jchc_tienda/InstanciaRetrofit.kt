package com.jchc.pt_jchc_tienda

import com.jchc.pt_jchc_tienda.data.TiendaApi
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

//Instancia de Retrofit que maneja el llamado al API
object InstanciaRetrofit {
    private val interceptor: HttpLoggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    private val client: OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(interceptor)
        .build()

    val api: TiendaApi = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(TiendaApi.BASE_URL)
        .client(client)
        .build()
        .create(TiendaApi::class.java)
}