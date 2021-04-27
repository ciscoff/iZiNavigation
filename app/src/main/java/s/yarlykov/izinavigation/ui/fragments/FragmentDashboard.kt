package s.yarlykov.izinavigation.ui.fragments

import android.animation.ObjectAnimator
import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.core.view.children
import androidx.fragment.app.Fragment
import kotlinx.coroutines.ExperimentalCoroutinesApi
import s.yarlykov.izinavigation.R
import s.yarlykov.izinavigation.ui.ViewModelActivity
import s.yarlykov.izinavigation.ui.di.ComponentProvider
import javax.inject.Inject
import kotlin.random.Random

class FragmentDashboard : Fragment(R.layout.fragment_dashboard) {

    companion object {
        private const val PROGRESS_MAX = 100
    }

    @Inject
    lateinit var viewModel: ViewModelActivity

    @ExperimentalCoroutinesApi
    override fun onAttach(context: Context) {
        super.onAttach(context)
        (requireActivity() as? ComponentProvider)?.component()?.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        (view as ViewGroup).children
            .forEach { pb ->
                (pb as? ProgressBar)?.apply {
                    max = PROGRESS_MAX
                    progress = 0
                    animateProgress()
                }
            }
    }

    private fun ProgressBar.animateProgress() {
        val p = Random.nextInt(20, 100)
        val d = Random.nextLong(200, 700)

        ObjectAnimator.ofInt(this, "progress", p).apply {
            duration = d
            start()
        }
    }
}