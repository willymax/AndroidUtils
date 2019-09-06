package com.willymax.androidutils

import android.app.Application
import android.content.Context
import androidx.multidex.MultiDex
import ke.co.basecode.logging.BeeLog




/**
 * Created by William Makau on 18/06/2019.
 * Company: Think Synergy Limited
 * Email: williammakau070@gmail.com
 * Phone: +254706356815
 * Purpose:
 */
class App : Application() {
    override fun onCreate() {
        super.onCreate()
        BeeLog.init(BuildConfig.DEBUG, TAG)
        instance = this
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }

    companion object {

        private val TAG = App::class.java.simpleName

        var instance: App? = null
            private set
    }
}