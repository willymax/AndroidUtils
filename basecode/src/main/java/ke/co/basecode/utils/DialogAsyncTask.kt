package ke.co.basecode.utils

import android.app.Activity
import android.os.AsyncTask
import ke.co.basecode.R


/**
 * Created by William Makau on 18/06/2019.
 * Company: Think Synergy Limited
 * Email: williammakau070@gmail.com
 * Phone: +254706356815
 * Purpose:
 */
abstract class DialogAsyncTask<Params, Progress, Result> : AsyncTask<Params, Progress, Result>() {

    private var progressDialog: BaseProgressDialog? = null

    protected abstract val activity: Activity

    override fun onPreExecute() {
        super.onPreExecute()
        try {
            progressDialog = BaseProgressDialog(activity)
            progressDialog?.setMessage(activity.getString(R.string.message_waiting))
                ?.setCancelable(false)
                ?.show()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun onPostExecute(result: Result) {
        super.onPostExecute(result)
        if (progressDialog != null) {
            progressDialog?.dismiss()
            progressDialog = null
        }
    }
}