package net.luispiressilva.kilabs_luis_silva.network

/**
 * Created by Luis Silva on 07/03/2019.
 */

class networkError(
    val code : Int,
    val type : ErrorType,
    val message : String?,

    val throwable : Throwable?
)



enum class ErrorType {
    SERVER,
    ERROR
}

