package ke.co.basecode.network

import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException


/**
 * Created by William Makau on 18/06/2019.
 * Company: Think Synergy Limited
 * Email: williammakau070@gmail.com
 * Phone: +254706356815
 * Purpose:
 */
class AuthenticationInterceptor(private val authToken: String) : Interceptor {
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()

        val builder = original.newBuilder()
            .header("Authorization", "Bearer $authToken")

        val request = builder.build()
        return chain.proceed(request)
    }
}