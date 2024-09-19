package com.jchc.pt_jchc_tienda.data

import android.os.Build
import androidx.annotation.RequiresExtension
import com.jchc.pt_jchc_tienda.data.model.Producto
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException

//Implementación de TiendaRepository

class TiendaRepositoryImpl(
    private val tiendaApi: TiendaApi
): TiendaRepository {
    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    override suspend fun getTienda(): Flow<Resultado<List<Producto>>> {
        return flow{
            val productosFromApi = try{
                tiendaApi.getTienda()
            }catch (e: IOException){
                e.printStackTrace()
                emit(Resultado.Error(mensaje = "ERROR CARGANDO LOS PRODUCTOS"))
                return@flow
            }catch(e: retrofit2.HttpException){
                e.printStackTrace()
                emit(Resultado.Error(mensaje = "ERROR CARGANDO LOS PRODUCTOS"))
                return@flow
            } catch(e: Exception){
                e.printStackTrace()
                emit(Resultado.Error(mensaje = "ERROR CARGANDO LOS PRODUCTOS"))
                return@flow
            }
            emit(Resultado.Success(productosFromApi))
        }
    }
}