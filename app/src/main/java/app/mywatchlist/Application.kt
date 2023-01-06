package app.mywatchlist

import android.app.Application
import app.mywatchlist.utils.notify
import app.mywatchlist.utils.schedulePushNotifications
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MyWatchlistApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        schedulePushNotifications(this)
        notify(this)
    }
}