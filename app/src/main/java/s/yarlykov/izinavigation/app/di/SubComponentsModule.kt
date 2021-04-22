package s.yarlykov.izinavigation.app.di

import dagger.Module
import s.yarlykov.izinavigation.ui.di.ComponentActivity
import s.yarlykov.media.di.ComponentRepo

/**
 * Этот модуль играет роль аргегатора сабкомпонентов
 */
@Module(subcomponents = [ComponentActivity::class/*, ComponentRepo::class*/])
class SubComponentsModule {}