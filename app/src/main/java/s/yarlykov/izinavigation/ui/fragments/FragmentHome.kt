package s.yarlykov.izinavigation.ui.fragments

import android.animation.ObjectAnimator
import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.animation.Animation
import android.view.animation.RotateAnimation
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import s.yarlykov.izinavigation.R
import s.yarlykov.izinavigation.ui.ViewModelActivity
import s.yarlykov.izinavigation.ui.di.ComponentProvider
import s.yarlykov.izinavigation.utils.logIt
import s.yarlykov.lib.smartadapter.adapter.SmartAdapter
import s.yarlykov.lib.smartadapter.model.SmartList
import javax.inject.Inject
import kotlin.random.Random

@ExperimentalCoroutinesApi
class FragmentHome : Fragment(R.layout.fragment_home) {

    companion object {
        const val COLUMNS = 4
    }

    @Inject
    lateinit var viewModel: ViewModelActivity

    @Inject
    lateinit var smartAdapter: SmartAdapter

    @Inject
    lateinit var smartList: SmartList

    private val itemController = ControllerGridItem(R.layout.layout_item_grid)

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (requireActivity() as? ComponentProvider)?.component()?.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        val manager = CellLayoutManager(requireContext(), COLUMNS)
        val decorator = manager.decorator

        recyclerView.apply {
            layoutManager = manager
            adapter = smartAdapter
            addItemDecoration(decorator)
        }

        if (::viewModel.isInitialized) {

            viewModel.sharedMedia().observe(viewLifecycleOwner) { item ->
                smartList.apply { addItem(item, itemController) }.also(smartAdapter::updateModel)
            }
        }
    }
}