package net.luispiressilva.kilabs_luis_silva.di.modules.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import net.luispiressilva.kilabs_luis_silva.components.viewmodel.ViewModelFactory
import net.luispiressilva.kilabs_luis_silva.di.annotations.ViewModelKey
import net.luispiressilva.kilabs_luis_silva.ui.main.MainViewModel

/**
 * Created by Luis Silva on 14/02/2019.
 */
@Module
abstract class ViewModelModule {

    @Binds
    internal abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    internal abstract fun presenterViewModel(viewModel: MainViewModel): ViewModel
}