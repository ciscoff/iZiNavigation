package s.yarlykov.media.di

import dagger.Component
import s.yarlykov.media.repo.RepoLocal
import s.yarlykov.media.repo.RepoRemote

/**
 * Gradle модуль :media ничего не знает про модуль :app, соответственно не видит ни одного
 * класса оттуда. Но модуль :app видит все из :media потому что подтянул его как зависимость.
 */

@Component(modules = [ModuleRepo::class])
interface ComponentRepo {

    @Component.Builder
    interface Builder {
        fun plus(module: ModuleRepo): Builder
        fun build(): ComponentRepo
    }

    val repoLocal: RepoLocal
    val repoRemote: RepoRemote
}