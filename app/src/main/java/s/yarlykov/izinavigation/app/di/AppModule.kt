package s.yarlykov.izinavigation.app.di

import android.content.Context
import dagger.Module
import dagger.Provides
import s.yarlykov.izinavigation.R
import s.yarlykov.izinavigation.ui.di.ComponentActivity
import s.yarlykov.media.di.ComponentRepo

@Module
class AppModule {

    @Provides
    fun getName(context: Context): String = context.getString(R.string.app_name)
}