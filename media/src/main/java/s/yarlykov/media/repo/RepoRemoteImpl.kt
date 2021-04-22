package s.yarlykov.media.repo

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import androidx.core.content.ContextCompat
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.delay
import s.yarlykov.media.utils.drawableToBitmap
import javax.inject.Inject
import javax.inject.Named

class RepoRemoteImpl constructor(
    private val context: Context,
    @Named("remote") private val drawables: List<Int>
) : RepoRemote {

    override suspend fun fetchMedia(): Flow<Bitmap> {

        return flow {
            drawables.forEach { id ->
                context.drawableToBitmap(id)?.let { bmp ->
                    emit(bmp)
                    delay(1000)
                }
            }
        }
    }
}