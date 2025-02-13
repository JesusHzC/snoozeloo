package com.jesushz.snoozeloo.core.data.audio

import android.content.Context
import android.media.AudioAttributes
import android.media.AudioManager.STREAM_ALARM
import android.media.MediaPlayer
import android.net.Uri
import android.os.Build
import com.jesushz.snoozeloo.core.data.model.Ringtone
import com.jesushz.snoozeloo.core.domain.audio.AudioManager
import com.jesushz.snoozeloo.core.domain.audio.AudioManager.Companion.SILENT
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AudioManagerImpl(
    private val context: Context
): AudioManager {

    private var mediaPlayer: MediaPlayer? = null

    override suspend fun getAvailableSounds(): List<Ringtone> = withContext(Dispatchers.IO) {
        val ringtoneManager = android.media.RingtoneManager(context).apply {
            setType(android.media.RingtoneManager.TYPE_ALARM)
        }

        val defaultRingtoneUri = android.media.RingtoneManager
            .getActualDefaultRingtoneUri(context, android.media.RingtoneManager.TYPE_ALARM)
            .buildUpon()
            .clearQuery()
            .build()
        var defaultRingtoneName = ""

        val cursor = ringtoneManager.cursor

        val ringtones = mutableListOf<Ringtone>()
        ringtones.add(Ringtone("Silent", SILENT))

        while (cursor.moveToNext()) {
            val id = cursor.getString(android.media.RingtoneManager.ID_COLUMN_INDEX)
            val title = cursor.getString(android.media.RingtoneManager.TITLE_COLUMN_INDEX)
            val uri = cursor.getString(android.media.RingtoneManager.URI_COLUMN_INDEX)
            val fullUri = "$uri/$id"

            if (fullUri != defaultRingtoneUri.toString()) {
                ringtones.add(Ringtone(title, fullUri))
            } else {
                defaultRingtoneName = title
            }
        }

        if (ringtones.size >= 2) {
            ringtones.add(1, Ringtone("Default (${defaultRingtoneName})", defaultRingtoneUri.toString()))
        }

        return@withContext ringtones
    }

    @Suppress("DEPRECATION")
    override fun play(uri: String, volume: Float, isLooping: Boolean) {
        val fullUri: Uri = try {
            if (uri == SILENT) {
                null
            } else {
                Uri.parse(uri)
            }
        } catch (e: Exception) {
            null
        } ?: return

        if (isPlaying()) {
            stop()
        }

        mediaPlayer = MediaPlayer().apply {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                setAudioAttributes(
                    AudioAttributes.Builder()
                        .setUsage(AudioAttributes.USAGE_ALARM)
                        .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                        .build()
                )
            } else {
                setAudioStreamType(STREAM_ALARM)
            }
            setDataSource(context, fullUri)
            setVolume(volume, volume)
            prepare()
            start()
            this.isLooping = isLooping
        }
    }

    override fun stop() {
        mediaPlayer?.stop()
        mediaPlayer?.release()
        mediaPlayer = null
    }

    override fun isPlaying(): Boolean {
        return try {
            mediaPlayer?.isPlaying == true
        } catch (e: Exception) {
            stop()
            false
        }
    }

}
