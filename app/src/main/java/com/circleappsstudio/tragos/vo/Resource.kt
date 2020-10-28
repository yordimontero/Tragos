//"vo" = Value Object.
//Clase para retornar estados de objetos (Carga, Éxito y Fallo).

package com.circleappsstudio.tragos.vo

import java.lang.Exception

sealed class Resource<out T> {

    class Loading<out T> : Resource<T>()

    data class Success<out T>(val data: T) : Resource<T>()

    data class Failure<out T>(val exception: Exception) : Resource<T>()

}