package com.example.busschedule.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.busschedule.Schedule
import com.example.busschedule.ScheduleDao

//указать Room, что делать со всеми этими классами DAO
@Database(entities = arrayOf(Schedule::class), version = 1)
abstract class AppDatabase : RoomDatabase() {

    //    один экземпляр базы данных, чтобы предотвратить состояние гонки или другие потенциальные проблемы.
    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null


        //возврат экземпляра AppDatabase
        fun getDatabase(context: Context): AppDatabase {
//оператор Элвиса, чтобы либо вернуть существующий экземпляр базы данных (если он уже существует),
//либо создать базу данных в первый раз, если это необходимо
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context,
                    AppDatabase::class.java,
                    "app_database"
                )
                        //createFromAsset () для загрузки существующих данных
                    .createFromAsset("database/bus_schedule.db")
                    .build()
                INSTANCE = instance

                instance
            }
        }
    }

    abstract fun scheduleDao(): ScheduleDao
}