package net.luispiressilva.kilabs_luis_silva

import net.luispiressilva.kilabs_luis_silva.model.PhotoFlickr
import org.junit.Test

/**
 * Created by Luis Silva on 08/03/2019.
 */
class ModelTest {

    @Test
    fun equality_and_default_values_check() {

        val photo = PhotoFlickr("0")
        val photo2 = PhotoFlickr("0","", "", "", 0, "", 0, 0, 0, "", "","")

        assert(photo == photo2)
        assert(photo.hashCode() == photo2.hashCode())


        val app3 = PhotoFlickr("1")

        assert(photo != app3)
        assert(photo.hashCode() != app3.hashCode())
    }
}