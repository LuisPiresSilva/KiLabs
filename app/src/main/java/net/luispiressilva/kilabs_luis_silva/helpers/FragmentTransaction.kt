package net.luispiressilva.kilabs_luis_silva.helpers

import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction


/**
 * Created by Luis Silva on 12/10/2018.
 */


/**
 * inline helper function to avoid beginTransaction and commitNow functions
 *
 * |
 *
 * simply write in a code block the functions you were going to execute
 *
 * |
 *
 * example:
 * ```
 * inTransaction {
 *      replace(R.id.container, SomeFragment.newInstance())
 * }
 * ```
 *
 * |
 *
 * begin and commitNow are done automatecally
 */
inline fun FragmentManager.hpInTransaction(func: FragmentTransaction.() -> Unit) {
    val fragmentTransaction = beginTransaction()
    fragmentTransaction.func()
    fragmentTransaction.commitNow()
}
