package s.yarlykov.izinavigation.ui.fragments

import android.view.ViewGroup
import android.widget.ImageView
import s.yarlykov.lib.smartadapter.holder.BindableViewHolder

class ViewHolderGridItem(parent: ViewGroup, layoutRes: Int) :
    BindableViewHolder<ModelGridItem>(parent, layoutRes) {

    override fun bind(data: ModelGridItem) {
        (itemView as? ImageView)?.setImageBitmap(data.bitmap)
    }
}