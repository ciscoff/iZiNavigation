package s.yarlykov.media.repo

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toBitmap
import s.yarlykov.media.utils.drawableToBitmap
import s.yarlykov.media.utils.logIt
import javax.inject.Inject
import javax.inject.Named

class RepoLocalImpl(
    private val context: Context,
    @Named("local") private val drawables: List<Int>
) : RepoLocal {

    override suspend fun fetchMedia(): List<Bitmap> {
        return drawables.mapNotNull { id -> context.drawableToBitmap(id) }
    }
}