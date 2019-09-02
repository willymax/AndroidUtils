package ke.co.basecode.network

import android.text.TextUtils
import android.util.Log
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import ke.co.basecode.rest.Client
import ke.co.basecode.utils.PrefUtils
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


/**
 * Created by William Makau on 18/06/2019.
 * Company: Think Synergy Limited
 * Email: williammakau070@gmail.com
 * Phone: +254706356815
 * Purpose:
 */
object ServiceGenerator {

    private val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

//    private val builder = Retrofit.Builder()
//        .baseUrl(BackEnd.BASE_URL)
//        .addConverterFactory(MoshiConverterFactory.create(moshi))
//        .addCallAdapterFactory(CoroutineCallAdapterFactory())

    fun <S> createService(serviceClass: Class<S>): S {
        val user = PrefUtils.instance?.getUser()
        return if (user != null) {
            createService(serviceClass, user.token)
        } else {
            createService(serviceClass, null)
        }
    }

    private fun <S> createService(serviceClass: Class<S>, authToken: String?): S {
        val logger = HttpLoggingInterceptor()
        logger.level = HttpLoggingInterceptor.Level.BODY

        val client = OkHttpClient.Builder()
            .addInterceptor(logger)
            .addInterceptor(HeaderInterceptor())

        if (!authToken.isNullOrEmpty()) {
            val interceptor = AuthenticationInterceptor(authToken)
            if (!client.interceptors().contains(interceptor)) {
                client.addInterceptor(interceptor)
            }
        }
        return Retrofit.Builder()
            .baseUrl(Client.instance.config.baseUrl)
            .client(client.build())
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .build()
            .create(serviceClass)
    }
}
