package s.yarlykov.izinavigation.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.delay
import s.yarlykov.izinavigation.ui.di.ScopeActivity
import s.yarlykov.izinavigation.ui.fragments.ModelGridItem
import s.yarlykov.media.repo.RepoLocal
import s.yarlykov.media.repo.RepoRemote
import javax.inject.Inject

@ScopeActivity
class ViewModelActivity @Inject constructor(
    private val mediaRepoLocal: RepoLocal,
    private val mediaRepoRemote: RepoRemote
) : ViewModel() {

    fun localMedia(): LiveData<ModelGridItem> =
        liveData {
            mediaRepoLocal.fetchMedia().forEach {
                delay(500)
                emit(ModelGridItem(it))
            }
        }

    fun remoteMedia(): LiveData<ModelGridItem> =
        liveData {
            mediaRepoRemote.fetchMedia().collect { emit(ModelGridItem(it)) }
        }
}