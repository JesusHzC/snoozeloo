package com.jesushz.snoozeloo.core.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.jesushz.snoozeloo.core.data.database.converters.SetConverter
import com.jesushz.snoozeloo.core.data.database.dao.AlarmDao
import com.jesushz.snoozeloo.core.data.database.entity.AlarmEntity

@Database(
    entities = [
        AlarmEntity::class
    ],
    version = 1,
)
@TypeConverters(
    SetConverter::class
)
abstract class SnoozeDatabase: RoomDatabase() {

    abstract fun alarmDao(): AlarmDao

    companion object {
        const val DATABASE_NAME = "snooze_db"
    }
}
