package net.luispiressilva.kilabs_luis_silva.helpers

import org.mockito.Mockito

/**
 * Created by Luis Silva on 08/03/2019.
 */


//to address
//java.lang.IllegalStateException: Mockito.any() must not be null
//we do not accept null's, Mockito.any() does
fun <T> any(): T {
    Mockito.any<T>()
    return uninitialized()
}

private fun <T> uninitialized(): T = null as T