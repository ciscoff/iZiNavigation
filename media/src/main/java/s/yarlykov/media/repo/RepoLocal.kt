package s.yarlykov.media.repo

import android.graphics.Bitmap

interface RepoLocal {
    suspend fun fetchMedia() : List<Bitmap>
}