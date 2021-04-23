package s.yarlykov.izinavigation.ui.fragments

import android.view.ViewGroup
import androidx.annotation.LayoutRes
import s.yarlykov.lib.smartadapter.adapter.Collector
import s.yarlykov.lib.smartadapter.controller.BindableItemController
import s.yarlykov.lib.smartadapter.model.item.BindableItem

class ControllerGridItem(@LayoutRes val layoutRes: Int) :
    BindableItemController<ModelGridItem, ViewHolderGridItem>() {

    override fun bind(
        holder: ViewHolderGridItem,
        item: BindableItem<ModelGridItem, ViewHolderGridItem>
    ) {
        bind(holder, item.data)
    }

    override fun createViewHolder(
        parent: ViewGroup,
        eventCollector: Collector?
    ): ViewHolderGridItem {
        return ViewHolderGridItem(parent, layoutRes)
    }

    override fun viewType(): Int = layoutRes
}