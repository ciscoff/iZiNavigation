package s.yarlykov.izinavigation.app.di

import android.content.Context
import dagger.BindsInstance
import dagger.Component
import s.yarlykov.izinavigation.ui.di.ComponentActivity
import s.yarlykov.media.di.ComponentRepo
import s.yarlykov.media.di.ModuleRepo
import javax.inject.Singleton

/**
 * Корневым компонентом является AppComponent. Его сабкомпонентами являются ComponentActivity и
 * ComponentRepo (из gradle-модуля :media). ComponentRepo предоставляет два репозитория, которые
 * нужны в ComponentActivity, чтобы создать ViewModel. ComponentActivity и ComponentRepo являются
 * "siblings", то есть между ними нет иерархической связи, оба находятся на одном уровне от
 * корневого компонента, а значит не могут предоставлять друг другу зависимости !!!
 *
 * Расширение графа зависимостей всегда идет строго сверху вниз, от корня к детям, их детям и т.д.
 * Но граф не может расширяться от ребенка к ребенку. Поэтому не получится назначить ComponentActivity
 * и ComponentRepo сабкомпонентами и 'перекидывать' между ними инстансы. Однако потребность в
 * репозиториях из ComponentRepo сохраняется и чтобы решить вопрос нужно просто в модуле :media
 * создать dagger-модуль и назначить его в AppComponent (см. ModuleRepo). То есть вообще отказаться
 * от компонента в :media.
 *
 * Или, можно пойти альтернативным путем !!! Задействовать механизм Dependent Components и сделать
 * AppComponent зависимым от ComponentRepo. Тогда он сможет получать оттуда инстансы и
 * предоставлять своим САБкомпонентам. При такой конфигурации у AppComponent'а в билдера
 * появляется строка:
 *
 *     fun componentRepo(component: ComponentRepo): Builder
 *
 * и необходимо явно создать и предоставить инстанс 'головного' компонента. ComponentRepo
 * естественно должен быть @Component, а не @Subcomponent.
 */

@Singleton
@Component(
    dependencies = [ComponentRepo::class],
    modules = [AppModule::class, /*ModuleRepo::class,*/ SubComponentsModule::class]
)
interface AppComponent {

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun context(context: Context): Builder

        fun plusModule(module: AppModule): Builder

        fun plusComponent(component: ComponentRepo): Builder

        fun build(): AppComponent
    }

    var mainActivityComponentFactory: ComponentActivity.Factory
}