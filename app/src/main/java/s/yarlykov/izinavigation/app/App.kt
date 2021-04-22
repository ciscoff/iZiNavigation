package s.yarlykov.izinavigation.app

import android.app.Application
import s.yarlykov.izinavigation.app.di.AppComponent
import s.yarlykov.izinavigation.app.di.AppModule
import s.yarlykov.izinavigation.app.di.DaggerAppComponent
import s.yarlykov.media.di.DaggerComponentRepo
import s.yarlykov.media.di.ModuleRepo

class App : Application() {

    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()

        appComponent = DaggerAppComponent
            .builder()
            .context(this)
            .plusModule(AppModule())
            .plusComponent(
                DaggerComponentRepo
                    .builder()
                    .plus(ModuleRepo(this))
                    .build()
            )
            .build()
    }
}