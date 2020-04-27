package ke.co.basecode.network

import android.app.AlertDialog
import android.content.Context
import com.google.gson.GsonBuilder
import ke.co.basecode.R
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
            onResponse(response.body())
        } else {
            var apiError: APIError<*>? = null
            try {
                apiError = GsonBuilder().create().fromJson(response.errorBody()?.string(), APIError::class.java)
            } catch (ex: IllegalStateException) {

            } catch (e: Exception) {

            }
            onErrorOccurred(apiError, "Api Error", response.code())
        }
        endProgress()
    }

    override fun onFailure(call: Call<T>, t: Throwable) {
        endProgress()
        onErrorOccurred(message = t.message, errorCode = 0)
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

    private fun showDialogError(message: String) {
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
    protected abstract fun onErrorOccurred(apiError: APIError<*>? = null, message: String? = "An error occurred", errorCode: Int)
}