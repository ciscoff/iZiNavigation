package s.yarlykov.izinavigation.ui.fragments

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.fragment.app.Fragment
import s.yarlykov.izinavigation.R
import s.yarlykov.izinavigation.ui.ViewModelActivity
import s.yarlykov.izinavigation.ui.di.ComponentProvider
import s.yarlykov.izinavigation.utils.logIt
import javax.inject.Inject

class FragmentNotifications : Fragment(R.layout.fragment_notifications) {

    @Inject
    lateinit var viewModel: ViewModelActivity

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (requireActivity() as? ComponentProvider)?.component()?.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val textView: TextView = view.findViewById(R.id.text_notifications)
    }
}