package com.meifei.app

import android.app.Application
import com.meifei.app.data.AppRepository
import com.meifei.app.data.local.db.AppDatabase
import com.meifei.app.data.local.datastore.FlightPreferences

class MeiFeiApplication : Application() {
    lateinit var repository: AppRepository
        private set

    override fun onCreate() {
        super.onCreate()
        val database = AppDatabase.build(this)
        val preferences = FlightPreferences(this)
        repository = AppRepository(database.flightSessionDao(), preferences)
    }
}
