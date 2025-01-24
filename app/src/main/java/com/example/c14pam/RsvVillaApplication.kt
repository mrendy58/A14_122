package com.example.c14pam

import android.app.Application
import com.example.c14pam.repository.AppContainer
import com.example.c14pam.repository.RsvVillaContainer

class RsvVillaApplication:Application() {
    lateinit var container: AppContainer
    override fun onCreate() {
        super.onCreate()
        container = RsvVillaContainer()
    }
}