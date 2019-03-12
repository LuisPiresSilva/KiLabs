package net.luispiressilva.kilabs_luis_silva.ui.base

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import androidx.lifecycle.ViewModel

/**
 * Created by Luis Silva on 29/01/2019.
 */

abstract class BasePresenter<View> : ViewModel(), LifecycleObserver {

    private var view: View? = null
    private var viewLifecycle: Lifecycle? = null

    fun attachView(view: View, viewLifecycle: Lifecycle) {
        this.view = view
        this.viewLifecycle = viewLifecycle

        viewLifecycle.addObserver(this)
    }

    protected fun view(): View? {
        return view
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    private fun onViewDestroyed() {
        view = null
        viewLifecycle = null
    }
}