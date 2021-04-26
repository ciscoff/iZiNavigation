package s.yarlykov.izinavigation.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import s.yarlykov.izinavigation.R

/**
 * Тестирование через adb:
 *
 * cd /d C:\Users\syarlykov\AppData\Local\Android\Sdk\platform-tools
 * adb shell am start -W -a android.intent.action.VIEW -d "sy://gizmos" s.yarlykov.izinavigation
 *
 *  или так, без пакета
 *
 * adb shell am start -W -a android.intent.action.VIEW -d "sy://gizmos"
 */

class DeepActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_deep)
    }

    @ExperimentalCoroutinesApi
    private fun tempFun() {

        val flow = flow {
            coroutineScope {
                emit(10)
            }
        }

        val flow2 = channelFlow {
            send(1)
        }


        lifecycleScope.launch {

        }
    }
}