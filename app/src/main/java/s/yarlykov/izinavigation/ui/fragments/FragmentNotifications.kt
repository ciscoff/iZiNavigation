package s.yarlykov.izinavigation.ui.fragments

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import kotlinx.android.synthetic.main.fragment_notifications.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.suspendCancellableCoroutine
import s.yarlykov.izinavigation.R
import s.yarlykov.izinavigation.ui.ViewModelActivity
import s.yarlykov.izinavigation.ui.di.ComponentProvider
import javax.inject.Inject
import kotlin.random.Random

@ExperimentalCoroutinesApi
class FragmentNotifications : Fragment(R.layout.fragment_notifications) {

    /**
     * Рестранслируем извещения об окончании анимации красного и синего элементов.
     * Ну или извещаем о готовности к следующей анимации.
     */
    private val redViewReadiness = MutableStateFlow(System.currentTimeMillis())
    private val blueViewReadiness = MutableStateFlow(System.currentTimeMillis())

    @Inject
    lateinit var viewModel: ViewModelActivity

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (requireActivity() as? ComponentProvider)?.component()?.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewLifecycleOwner.lifecycleScope.launch {

            /**
             * Анимируем красный элемент
             */
            launch {
                redViewReadiness.collect {
                    viewRed.animator().run {
                        start()
                        redViewReadiness.emit(awaitEnd())
                    }
                }
            }

            /**
             * Анимируем синий элемент
             */
            launch {
                blueViewReadiness.collect {
                    viewBlue.animator().run {
                        start()
                        blueViewReadiness.emit(awaitEnd())
                    }
                }
            }
        }
    }

    /**
     * Цикл преобразований:
     * translate -> alpha 1 -> alpha 0 -> ..... translate
     */
    private fun View.animator(): AnimatorSet {

        translationX = Random.nextDouble(-500.0, 500.0).toFloat()
        translationY = Random.nextDouble(-500.0, 500.0).toFloat()

        val fadeOut = ObjectAnimator.ofFloat(this, "alpha", 1f, 0f).apply {
            duration = Random.nextLong(150, 300)
        }

        val fadeIn = ObjectAnimator.ofFloat(this, "alpha", 0f, 1f).apply {
            duration = Random.nextLong(100, 400)
        }

        return AnimatorSet().apply { play(fadeOut).after(fadeIn) }
    }

    /**
     * Ждем окончания анимации и сигналим возвратом результата.
     */
    private suspend fun AnimatorSet.awaitEnd() = suspendCancellableCoroutine<Long> { cont ->

        cont.invokeOnCancellation { cancel() }

        addListener(object : AnimatorListenerAdapter() {
            private var endedSuccessfully = true

            override fun onAnimationCancel(animation: Animator) {
                endedSuccessfully = false
            }

            override fun onAnimationEnd(animation: Animator) {
                animation.removeListener(this)

                if (cont.isActive) {
                    if (endedSuccessfully) {
                        cont.resume(System.currentTimeMillis(), null)
                    } else {
                        cont.cancel()
                    }
                }
            }
        })
    }
}