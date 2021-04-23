package s.yarlykov.izinavigation.ui.fragments

import android.animation.Animator
import android.animation.ObjectAnimator
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.RotateAnimation
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
            itemView.translationX = if (adapterPosition % 2 == 0) 1000f else -1000f
            itemView.animate().translationX(0f).setDuration(150)
                .setListener(object : Animator.AnimatorListener {
                    override fun onAnimationStart(p0: Animator?) {
                    }

                    override fun onAnimationEnd(p0: Animator?) {
                        data.isVisible = true

                        // Подкастылено с числом 7. Подразумевается, что это последний элемент.
                        if (adapterPosition == 7) {
                            itemView.rotate()
                        }
                    }

                    override fun onAnimationCancel(p0: Animator?) {
                    }

                    override fun onAnimationRepeat(p0: Animator?) {
                    }
                }).start()
        }
    }

    /**
     *
     * ObjectAnimator.ofFloat(this, "rotation", 0f, 30f)
     */
    private fun View.rotate() {
        val rotation = RotateAnimation(
            0f, 30f, Animation.RELATIVE_TO_SELF,
            0.5f, Animation.RELATIVE_TO_SELF, 0.5f
        )

        rotation.duration = 150
        this.startAnimation(rotation)
    }
}