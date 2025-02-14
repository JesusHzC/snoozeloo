package com.jesushz.snoozeloo.core.data.database.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.jesushz.snoozeloo.core.data.database.entity.AlarmEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface AlarmDao {

    @Upsert
    suspend fun upsertAlarm(alarm: AlarmEntity)

    @Query("SELECT * FROM alarm ORDER by hour ASC")
    fun getAll(): Flow<List<AlarmEntity>>

    @Query("SELECT * FROM alarm WHERE id = :id")
    suspend fun getAlarmById(id: String): AlarmEntity?

    @Query("UPDATE alarm SET enabled = 0 WHERE id = :id")
    suspend fun disableAlarmById(id: String)

    @Query("DELETE FROM alarm WHERE id = :id")
    suspend fun deleteAlarmById(id: String)

    @Query("DELETE FROM alarm")
    suspend fun deleteAllAlarms()

}
