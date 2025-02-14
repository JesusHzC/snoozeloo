package com.jesushz.snoozeloo.core.data.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "alarm")
data class AlarmEntity(
    @PrimaryKey(autoGenerate = false)
    val id: String,
    val name: String,
    val days: Set<Int>,
    val hour: Int,
    val minute: Int,
    val enabled: Boolean,
    val volume: Int,
    val audioUri: String,
    val vibrate: Boolean
)
