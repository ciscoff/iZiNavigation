package s.yarlykov.izinavigation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.coroutines.ExperimentalCoroutinesApi
import s.yarlykov.izinavigation.R
import s.yarlykov.izinavigation.databinding.SelectionDialogBinding
import s.yarlykov.izinavigation.ui.di.ComponentProvider
import s.yarlykov.izinavigation.utils.Notifier
import javax.inject.Inject

class SelectionDialogFragment : BottomSheetDialogFragment() {

    @Inject
    lateinit var notifier: Notifier

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = SelectionDialogBinding.inflate(inflater, container, false).root

    @ExperimentalCoroutinesApi
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (requireActivity() as? ComponentProvider)?.component()?.inject(this)

        val binding = SelectionDialogBinding.bind(view)

        // TODO Пока не используется
        val args: SelectionDialogFragmentArgs by navArgs()

        binding.buttonOk.setOnClickListener {
            val context = requireContext().applicationContext
            val navController = findNavController()

            val (destId, hintId) = when (binding.chipGroup.checkedChipId) {
                R.id.chipHome -> R.id.navigation_home to R.string.see_more_icons
                R.id.chipDash -> R.id.navigation_dashboard to R.string.see_more_dashboards
                R.id.chipNotify -> R.id.navigation_notifications to R.string.see_more_notify
                else -> R.id.navigation_selection_dialog to R.string.app_name
            }

            /**
             * Создать DeepLink.
             */
            val pendingIntent = navController
                .createDeepLink()
                .setDestination(destId)
                .createPendingIntent()

            notifier.postNotification(destId, hintId, context, pendingIntent)

            dismiss()
        }
    }
}