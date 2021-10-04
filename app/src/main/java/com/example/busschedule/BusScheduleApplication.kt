package com.example.busschedule

import android.app.Application
import com.example.busschedule.database.AppDatabase

class BusScheduleApplication : Application() {
    //содержит результат getDatabase()
    val database: AppDatabase by lazy { AppDatabase.getDatabase(this) }
}