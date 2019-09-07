package ke.co.basecode.network

import android.content.Context
import android.net.ConnectivityManager
import com.readystatesoftware.chuck.BuildConfig
import com.readystatesoftware.chuck.ChuckInterceptor
import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit
import javax.net.ssl.HostnameVerifier

class NetworkUtils private constructor() {

    companion object {
        private lateinit var mCallback: Callback
        private lateinit var mInstance: NetworkUtils
        private var mClientInstance: OkHttpClient? = null

        fun init(callback: Callback) {
            mInstance = NetworkUtils()
            mCallback = callback
        }

        fun getClientInstance(): OkHttpClient {
            return mClientInstance ?: synchronized(this) {
                mClientInstance
                    ?: buildClient().also { mClientInstance = it }
            }
        }

        fun getInstance(): NetworkUtils {
            return mInstance
        }

        fun getCallback(): Callback {
            return mCallback
        }

        private fun buildClient(): OkHttpClient {
            val builder = OkHttpClient.Builder()
                .hostnameVerifier(HostnameVerifier { _, _ ->
                    true
                })
                .connectTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .followRedirects(false)


            // Add an Interceptor to the OkHttpClient.
            builder.addInterceptor { chain ->
                // Get the request from the chain.
                val original = chain.request()

                // Get url
                val url = original.url.newBuilder()
                // Get common params
                val commonParams = mCallback.getCommonParams()
                // Add common params to the url
                for (key in commonParams.keys) {
                    url.addQueryParameter(key, commonParams[key])
                }

                val request = original.newBuilder()
                    .url(url.build())
                    .method(original.method, original.body)
                    .header("Accept", "application/json")
                    .header("Authorization", "Bearer ${mCallback.getAuthToken()}")

                // Add the modified request to the chain.
                val response = chain.proceed(request.build())


                if (response.code == 401 || response.code == 403) {
                    mCallback.onAuthError(response.code)
                    mClientInstance?.dispatcher?.cancelAll()
                }

                response
            }

            //builder.addInterceptor(ChuckInterceptor(mCallback.getContext()))

            if (BuildConfig.DEBUG == true) {
                val logger = HttpLoggingInterceptor()
                logger.level = HttpLoggingInterceptor.Level.BODY
                builder.addInterceptor(logger)
            }
            return builder.build()
        }

        fun canConnect(context: Context): Boolean {
            val connMgr = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val networkInfo = connMgr.activeNetworkInfo
            return networkInfo != null && networkInfo.isConnected
        }

    }

    interface Callback {
        fun onAuthError(statusCode: Int)
        fun getAuthToken(): String?
        fun getErrorMessageFromResponseBody(statusCode: Int, responseBody: ResponseBody?): String
        fun getContext(): Context
        fun getCommonParams(): Map<String, String>
    }


}