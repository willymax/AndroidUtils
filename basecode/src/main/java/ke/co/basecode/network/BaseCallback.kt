package ke.co.basecode.network

import android.util.Log
import com.google.gson.GsonBuilder
import ke.co.basecode.model.APIError
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Exception


/**
 * Created by William Makau on 18/06/2019.
 * Company: Think Synergy Limited
 * Email: williammakau070@gmail.com
 * Phone: +254706356815
 * Purpose:
 */
abstract class BaseCallback<T> : Callback<T> {
    override fun onResponse(call: Call<T>, response: Response<T>) {
        if (response.isSuccessful) {
            onResponse(response.body())
        } else {
            var apiError: APIError<*>? = null
            try {
                apiError = GsonBuilder().create().fromJson(response.errorBody()?.string(), APIError::class.java)
            } catch (ex: IllegalStateException) {

            } catch (e: Exception) {

            }
            onErrorOccurred(apiError, "Api Error", errorCode = response.code())
        }
    }

    override fun onFailure(call: Call<T>, t: Throwable) {
        onErrorOccurred(message = t.message, errorCode = 0)
    }

    private fun onFinishWithError(message: String, errorCode: Int) {
        onErrorOccurred(message = "$errorCode - $message", errorCode = errorCode)
    }

    protected abstract fun onResponse(response: T?)
    protected abstract fun onErrorOccurred(apiError: APIError<*>? = null, message: String? = "An error occurred", errorCode: Int)
    companion object {
        const val TAG = "BaseCallback"
    }
}