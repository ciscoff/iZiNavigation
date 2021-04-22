package s.yarlykov.media.repo

import android.graphics.Bitmap
import kotlinx.coroutines.flow.Flow

interface RepoRemote {
    suspend fun fetchMedia() : Flow<Bitmap>
}