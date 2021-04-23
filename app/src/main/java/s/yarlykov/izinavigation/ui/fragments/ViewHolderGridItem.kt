package s.yarlykov.izinavigation.ui.fragments

import android.animation.Animator
import android.view.ViewGroup
import android.widget.ImageView
import s.yarlykov.lib.smartadapter.holder.BindableViewHolder

class ViewHolderGridItem(parent: ViewGroup, layoutRes: Int) :
    BindableViewHolder<ModelGridItem>(parent, layoutRes) {

    /**
     * Чтобы анимировать только новые элементы списка добавлен флаг isVisible.
     * Если он true, то элемент уже был выведен на экран с анимацией и его не нужно
     * дергать повторно.
     */
    override fun bind(data: ModelGridItem) {
        (itemView as? ImageView)?.setImageBitmap(data.bitmap)

        if (!data.isVisible) {
            itemView.translationX = -1000f
            itemView.animate().translationX(0f).setDuration(150)
                .setListener(object : Animator.AnimatorListener {
                    override fun onAnimationStart(p0: Animator?) {
                    }

                    override fun onAnimationEnd(p0: Animator?) {
                        data.isVisible = true
                    }

                    override fun onAnimationCancel(p0: Animator?) {
                    }

                    override fun onAnimationRepeat(p0: Animator?) {
                    }
                }).start()
        }
    }
}