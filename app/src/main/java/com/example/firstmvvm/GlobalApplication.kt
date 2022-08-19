package com.example.firstmvvm

import android.app.Application
import androidx.room.Room
import com.example.firstmvvm.model.AppDataBase

class GlobalApplication : Application(){
    companion object{
        lateinit var appInstance: GlobalApplication
            private set
        lateinit var appDataBaseInstance: AppDataBase
            private set
    }

    override fun onCreate() {
        super.onCreate()
        appInstance = this

        appDataBaseInstance =
            Room.databaseBuilder(appInstance.applicationContext, AppDataBase::class.java, "db")
                .allowMainThreadQueries() // <- 개발시에만 적용
                .build()
    }
}