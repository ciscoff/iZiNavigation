package s.yarlykov.izinavigation.ui

import android.app.PendingIntent
import android.content.Intent
import android.net.Uri
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
                R.id.chipActivity -> 0 to R.string.see_more_activities
                else -> R.id.navigation_selection_dialog to R.string.app_name
            }

            /**
             * Итак, DeepLink - это по сути intent filter. Для активити он задается в манифесте, а
             * для фрагментов навигационного контроллера - в XML навигации. Если нам нужно через
             * deep link открыть активити, то мы самостоятельно создаем Intent, указывая URI. Intent
             * открытия навигационного фрагмента мы создаем опосредованно через NavController.
             *
             * TODO: На что обратить внимание.
             * У нас для deep link получается ссылка со схемой https - "https://gizmo.forever".
             * Это значит, что при первой попытке открыть ссылку появится диалог выбора: браузер
             * или приложение. Если выбрать приложение с опцией "Только сейчас", то в следующий
             * раз диалог появится снова. Если выбрать "Всегда", то связка 'scheme + host' будет
             * привязана в системе к нашему приложению и активити будет открываться сразу.
             *
             * TODO: Однако.
             * Допустим мы выбрали "Всегда" открывать URI через браузер. Если теперь попробовать
             * открыть URI через ADB с указанием пакета, то откроется наше активити, а не браузер.
             *
             * adb shell am start -W -a android.intent.action.VIEW -d "https://gizmos.forever" s.yarlykov.izinavigation
             *
             * А если без указания пакета, то откроется браузер как дефолтовое приложение
             *
             * adb shell am start -W -a android.intent.action.VIEW -d "https://gizmos.forever"
             */
            val pendingIntent = if (destId == 0) {
                val intent: Intent = Intent(Intent.ACTION_VIEW, Uri.parse(URI_ACTIVITY)).apply {
                    flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                }
                PendingIntent.getActivity(
                    requireContext(),
                    0,
                    intent,
                    PendingIntent.FLAG_UPDATE_CURRENT
                )
            } else {
                navController
                    .createDeepLink()
                    .setDestination(destId)
                    .createPendingIntent()
            }

            notifier.postNotification(destId, hintId, context, pendingIntent)

            dismiss()
        }
    }

    companion object {
        private const val URI_ACTIVITY = "https://gizmos.forever"
    }
}