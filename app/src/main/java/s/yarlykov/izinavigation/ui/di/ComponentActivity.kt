package s.yarlykov.izinavigation.ui.di

import dagger.Subcomponent
import s.yarlykov.izinavigation.MainActivity
import s.yarlykov.izinavigation.ui.fragments.FragmentDashboard
import s.yarlykov.izinavigation.ui.fragments.FragmentHome
import s.yarlykov.izinavigation.ui.fragments.FragmentNotifications
import s.yarlykov.izinavigation.ui.ViewModelActivity
import s.yarlykov.lib.smartadapter.adapter.SmartAdapter
import s.yarlykov.lib.smartadapter.model.SmartList

@ScopeActivity
@Subcomponent(modules = [ModuleActivity::class])
interface ComponentActivity {

    @Subcomponent.Factory
    interface Factory {
        fun create(): ComponentActivity
    }

    fun inject(consumer: MainActivity)
    fun inject(consumer: FragmentDashboard)
    fun inject(consumer: FragmentNotifications)
    fun inject(consumer: FragmentHome)

    val smartAdapter: SmartAdapter

    val smartList: SmartList

    val viewModel: ViewModelActivity
}