package ke.co.basecode.network

import android.app.AlertDialog
import android.content.Context
import android.util.Log
import com.google.gson.GsonBuilder
import ke.co.basecode.R
import ke.co.basecode.logging.BeeLog
import ke.co.basecode.model.APIError
import ke.co.basecode.utils.BaseProgressDialog
import ke.co.basecode.utils.BaseUtils
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Created by Willy on 04/03/2020
 * Email williammakau070@gmail.com
 * @author willi
 */
abstract class BaseCallbackWithLoading<T> @JvmOverloads constructor(
    private val context: Context,
    showProgressDialog: Boolean = true,
    canCancelProgressDialog: Boolean = false,
    progressMessage: String = "Please wait..."
) : Callback<T> {

    private var progressDialog: BaseProgressDialog? = null

    constructor(context: Context, progressMessage: String) : this(context, true, false, progressMessage)

    init {
        if (showProgressDialog) {
            startProgress(canCancelProgressDialog, progressMessage)
        }
    }

    override fun onResponse(call: Call<T>, response: Response<T>) {
        if (response.isSuccessful) {
            Log.d("FINISH_RESPONSE", "SUCCESS")
            onResponse(response.body())
        } else {
            val apiError = GsonBuilder().create().fromJson(response.errorBody()?.string(), APIError::class.java)
            onErrorOccurred(apiError, "Api Error")
            Log.d("FINISH_RESPONSE", "ERROR FROM SERVER")
        }
        endProgress()
    }

    override fun onFailure(call: Call<T>, t: Throwable) {
        endProgress()
        Log.d("ERROR", "MESSAGE: ${t.message}")
        onErrorOccurred(message = t.message)
    }

    private fun startProgress(cancel: Boolean, message: String) {
        progressDialog = BaseProgressDialog(context)
        progressDialog?.setCancelable(cancel)
        progressDialog?.setMessage(message)
        progressDialog?.show()
    }

    private fun endProgress() {
        if (progressDialog != null) {
            if (progressDialog?.isShowing == true) {
                progressDialog?.dismiss()
            }
        }
    }

    private fun onFinishWithError(message: String, errorCode: Int) {
        onErrorOccurred(message = "$errorCode - $message")
        Log.d("FINISH_ERROR", "MESSAGE: $message,CODE: $errorCode")
    }

    private fun showDialogError(message: String) {
        BeeLog.d("William", message)
        val alertBuilder = AlertDialog.Builder(this.context)
        if (!BaseUtils.canConnect(context)) {
            alertBuilder.setMessage("No internet connection")
        } else {

            alertBuilder.setMessage("Oops! An error occurred.")
        }
        alertBuilder.setTitle("Cube Messenger")
        alertBuilder.setIcon(R.drawable.ic_warning_24dp)
        alertBuilder.setCancelable(false)
        alertBuilder.setPositiveButton("OK") { _, _ ->
            if (!BaseUtils.canConnect(context)) {
                //trigger network setting
            }
        }
        val alert = alertBuilder.create()
        alert.show()

    }

    protected abstract fun onResponse(response: T?)
    protected abstract fun onErrorOccurred(apiError: APIError<*>? = null, message: String? = "An error occurred")
}