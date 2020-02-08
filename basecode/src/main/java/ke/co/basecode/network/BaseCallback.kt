package ke.co.basecode.network

import android.app.AlertDialog
import android.content.Context
import android.util.Log
import ke.co.basecode.R
import ke.co.basecode.logging.BeeLog
import ke.co.basecode.utils.BaseProgressDialog
import ke.co.basecode.utils.BaseUtils
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
abstract class BaseCallback<T> @JvmOverloads constructor(
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
            Log.d("FINISH_RESPONSE: ", "SUCCESS")
        } else {
            Log.d("FINISH_RESPONSE: ", "ERROR FROM SERVER")
        }
        onResponse(response)
        endProgress()
    }

    override fun onFailure(call: Call<T>, t: Throwable) {
        endProgress()
        onError(t.message)
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
        onFailure("$errorCode - $message")
        Log.d("FINISH_ERROR: ", "MESSAGE: $message,CODE: $errorCode")
    }

    private fun onError(message: String?) {
        onFailure(message)
        Log.d("ERROR: ", "MESSAGE: $message")
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

    protected abstract fun onResponse(response: Response<T>)
    protected abstract fun onFailure(message: String?)
}