package de.arjmandi.gymble

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin

class GymbleApplication: Application(){
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@GymbleApplication)
            modules(
                domainModule,
                dataModule,
                matchingModule
            )
        }
    }

    override fun onTerminate() {
        super.onTerminate()
        stopKoin()
    }
}