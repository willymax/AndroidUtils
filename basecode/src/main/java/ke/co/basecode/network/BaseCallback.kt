package ke.co.basecode.network

import android.util.Log
import com.google.gson.GsonBuilder
import ke.co.basecode.model.APIError
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


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
            Log.d(TAG, "SUCCESS")
            onResponse(response.body())
        } else {
            val apiError = GsonBuilder().create().fromJson(response.errorBody()?.string(), APIError::class.java)
            onErrorOccurred(apiError, "Api Error")
            Log.d(TAG, "ERROR FROM SERVER")
        }
    }

    override fun onFailure(call: Call<T>, t: Throwable) {
        Log.d(TAG, "MESSAGE: ${t.message}")
        onErrorOccurred(message = t.message)
    }

    private fun onFinishWithError(message: String, errorCode: Int) {
        onErrorOccurred(message = "$errorCode - $message")
        Log.d(TAG, "MESSAGE: $message,CODE: $errorCode")
    }

    protected abstract fun onResponse(response: T?)
    protected abstract fun onErrorOccurred(apiError: APIError<*>? = null, message: String? = "An error occurred")
    companion object {
        const val TAG = "BaseCallback"
    }
}