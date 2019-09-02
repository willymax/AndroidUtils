package ke.co.basecode.rest

import android.app.Application
import android.text.TextUtils
import ke.co.basecode.R


/**
 * Created by William Makau on 18/06/2019.
 * Company: Think Synergy Limited
 * Email: williammakau070@gmail.com
 * Phone: +254706356815
 * Purpose:
 */
class Client private constructor(val config: Config) {

    companion object {
        private val TAG = "Client"

        private var mInstance: Client? = null

        fun init(config: Config) {
            if (mInstance == null) {
                mInstance = Client(config)
            } else {
                throw IllegalArgumentException("Client should only be initialized once," + " most probably in the Application class")
            }
        }

        val instance: Client
            @Synchronized get() {
                if (mInstance == null) {
                    throw IllegalArgumentException("Client has not been initialized," + " it should be initialized once, most probably in the Application class")
                }
                return mInstance as Client
            }

        fun absoluteUrl(relativeUrl: String, client: Client): String {
            var url = client.config.baseUrl + relativeUrl
            if (!TextUtils.isEmpty(client.config.token)) {
                url = url + "?token=" + client.config.token
            }
            return url
        }
    }

    abstract class Config {

        abstract val context: Application

        abstract val baseUrl: String

        abstract val token: String?

        protected val requestTimeout: Int
            get() = DEFAULT_REQUEST_TIMEOUT

        protected val responseTimeout: Int
            get() = DEFAULT_RESPONSE_TIMEOUT

        protected val maxRetries: Int
            get() = 2

        protected val userAgent: String
            get() = context.getString(R.string.app_name)

        protected fun onError() {}

        protected fun enableRedirects(): Boolean {
            return false
        }

        protected fun enableRelativeRedirects(): Boolean {
            return true
        }

        companion object {
            private const val DEFAULT_REQUEST_TIMEOUT = 10000
            private const val DEFAULT_RESPONSE_TIMEOUT = 30000
        }
    }
}