package com.jchc.pt_jchc_tienda.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jchc.pt_jchc_tienda.data.Resultado
import com.jchc.pt_jchc_tienda.data.TiendaRepository
import com.jchc.pt_jchc_tienda.data.model.Producto
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ProductosViewModel(
    private val tiendaRepository: TiendaRepository
): ViewModel() {
    //Declaramos un MutableStateFlow de tipo lista de Productos
    private val _productos = MutableStateFlow<List<Producto>>(emptyList())
    val productos = _productos.asStateFlow()

    private val _showErrorToastChannel = Channel<Boolean>()
    val showErrorToastChannel = _showErrorToastChannel.receiveAsFlow()

    init{
        //Utilizamos una corutina para hacer la petición al API
        viewModelScope.launch {
            tiendaRepository.getTienda().collectLatest {result ->
                when(result){
                    //Cuando la información no se recupera
                    // envía una actualización al ErrorToastChannel
                    is Resultado.Error -> {
                        _showErrorToastChannel.send(true)
                    }
                    is Resultado.Success -> {
                        result.data?.let{productos ->
                            _productos.update { productos }  //Cuando se recupera información
                                                    //actualiza el Flow de tipo lista de Productos
                        }
                    }
                }
            }
        }
    }
}