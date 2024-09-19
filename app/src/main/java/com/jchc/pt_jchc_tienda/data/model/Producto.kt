package com.jchc.pt_jchc_tienda.data.model

import com.google.gson.annotations.SerializedName

data class Producto(
    //CLASE PARA EL JSON CONSULTADO EN EL API
    @SerializedName("id")
    val id: String? = null,
    @SerializedName("title")
    val title: String? = null,
    @SerializedName("description")
    val desc: String? = null,
    @SerializedName("image")
    val image: String? = null,
    @SerializedName("price")
    val price: Double = 0.0,
    @SerializedName("category")
    val category: String? = null
)

class Tienda: ArrayList<Producto>() //ARRAY DE "PRODUCTO"

