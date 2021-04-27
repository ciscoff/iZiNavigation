package s.yarlykov.izinavigation.ui.di

import kotlinx.coroutines.ExperimentalCoroutinesApi

/**
 * Активити реализует этот интерфейс, чтобы предоставлять дочерним фрагментам
 * компонент ComponentActivity.
 */
@ExperimentalCoroutinesApi
interface ComponentProvider {
    fun component(): ComponentActivity
}