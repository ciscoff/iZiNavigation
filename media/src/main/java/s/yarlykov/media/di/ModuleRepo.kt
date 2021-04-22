package s.yarlykov.media.di

import android.content.Context
import dagger.Module
import dagger.Provides
import s.yarlykov.media.R
import s.yarlykov.media.repo.RepoLocal
import s.yarlykov.media.repo.RepoLocalImpl
import s.yarlykov.media.repo.RepoRemote
import s.yarlykov.media.repo.RepoRemoteImpl
import javax.inject.Named

/**
 * Context будет доступен от родительского компонента
 */
@Module
class ModuleRepo(private val context: Context) {

    @Named("local")
    @get:Provides
    val drawablesLocal = listOf(
        R.drawable.ic_polymer,
        R.drawable.ic_scooter,
        R.drawable.ic_sick,
        R.drawable.ic_skate,
    )

    @Named("remote")
    @get:Provides
    val drawablesRemote = listOf(
        R.drawable.ic_airplanemode,
        R.drawable.ic_alarm,
        R.drawable.ic_android,
        R.drawable.ic_houses,
    )

    @Provides
    fun repoLocal(): RepoLocal = RepoLocalImpl(context, drawablesLocal)

    @Provides
    fun repoRemote(): RepoRemote = RepoRemoteImpl(context, drawablesRemote)
}