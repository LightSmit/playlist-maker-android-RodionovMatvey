package com.practicum.playlist_maker_android_rodionovmatvey.data.db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    version = 4,
    entities = [
        TrackEntity::class,
        PlaylistEntity::class
    ],
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun trackDao(): TrackDao
    abstract fun playlistDao(): PlaylistDao
}