package s.yarlykov.izinavigation.ui.di

import dagger.Module
import dagger.Provides
import s.yarlykov.lib.smartadapter.adapter.SmartAdapter
import s.yarlykov.lib.smartadapter.model.SmartList

@Module
class ModuleActivity {

    @Provides
    fun smartAdapter(): SmartAdapter = SmartAdapter()

    @Provides
    fun smartList() : SmartList = SmartList.create()
}