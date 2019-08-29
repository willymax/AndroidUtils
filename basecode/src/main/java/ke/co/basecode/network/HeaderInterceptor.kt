package ke.co.basecode.network

import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException


/**
 * Created by William Makau on 09/07/2019.
 * Company: Think Synergy Limited
 * Email: williammakau070@gmail.com
 * Phone: +254706356815
 * Purpose:
 */
class HeaderInterceptor : Interceptor {
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()

        val builder = original.newBuilder()
            .header("Content-Type", "application/json")

        val request = builder.build()
        return chain.proceed(request)
    }
}