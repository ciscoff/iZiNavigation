package s.yarlykov.izinavigation.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.merge
import s.yarlykov.izinavigation.ui.di.ScopeActivity
import s.yarlykov.izinavigation.ui.fragments.ModelGridItem
import s.yarlykov.media.repo.RepoLocal
import s.yarlykov.media.repo.RepoRemote
import javax.inject.Inject
import kotlin.random.Random

@ScopeActivity
class ViewModelActivity @Inject constructor(
    private val mediaRepoLocal: RepoLocal,
    private val mediaRepoRemote: RepoRemote
) : ViewModel() {

    fun localMedia(): LiveData<ModelGridItem> =
        liveData {
            mediaRepoLocal.fetchMedia().forEach {
                delay(Random.nextLong(100, 800))
                emit(ModelGridItem(it))
            }
        }

    fun remoteMedia(): LiveData<ModelGridItem> =
        liveData {
            mediaRepoRemote.fetchMedia().collect {
                delay(Random.nextLong(300, 1000))
                emit(ModelGridItem(it))
            }
        }

    @ExperimentalCoroutinesApi
    fun sharedMedia() = liveData {
        merge(mediaRepoLocal.fetchMedia().asFlow(), mediaRepoRemote.fetchMedia())
            .collect {
                delay(300)
                emit(ModelGridItem(it))
            }
    }
}