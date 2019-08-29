package ke.co.basecode

import android.app.Application
import android.content.Context
import androidx.multidex.MultiDex
import ke.co.basecode.logging.BeeLog
import ke.co.basecode.network.BackEnd
import ke.co.basecode.rest.Client
import ke.co.basecode.utils.PrefUtils




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

        Client.init(object : Client.Config() {
            protected override val context: Application
                get() = this@App

            override val baseUrl: String
                get() = BackEnd.BASE_URL

            override val token: String?
                get() = if (PrefUtils.instance?.getUser() != null) {
                    PrefUtils.instance?.getUser()?.token
                } else null
        })
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