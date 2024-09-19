package com.jchc.pt_jchc_tienda.data

sealed class Resultado<T>( //Clase que recibe el resultado de la llamada al API
    val data: T? = null,
    val mensaje: String? = null
){
    class Success<T>(data: T?): Resultado<T>(data)
    class Error<T> (data: T? = null, mensaje: String?): Resultado<T>(data, mensaje)
}
