package s.yarlykov.izinavigation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.coroutines.ExperimentalCoroutinesApi
import s.yarlykov.izinavigation.app.App
import s.yarlykov.izinavigation.ui.di.ComponentActivity
import s.yarlykov.izinavigation.ui.di.ComponentProvider

@ExperimentalCoroutinesApi
class MainActivity : AppCompatActivity(), ComponentProvider {

    private lateinit var componentActivity: ComponentActivity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        /**
         * Это нужно вызывать ДО setContentView(...) !!!
         */
        componentActivity = (application as App)
            .appComponent
            .mainActivityComponentFactory
            .create()

        setContentView(R.layout.activity_main)
        initNavComponents()
    }

    /**
     * Отдать компонент
     */
    override fun component(): ComponentActivity = componentActivity

    /**
     * Инициализация Navigation-компонентов
     */
    private fun initNavComponents() {
        val navView: BottomNavigationView = findViewById(R.id.nav_view)

        val navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home,
                R.id.navigation_dashboard,
                R.id.navigation_notifications,
                R.id.navigation_selection_dialog
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }
}