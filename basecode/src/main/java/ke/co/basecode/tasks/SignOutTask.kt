package ke.co.basecode.tasks

import android.app.Activity
import ke.co.basecode.R
import ke.co.basecode.utils.DialogAsyncTask
import ke.co.basecode.utils.PrefUtils


/**
 * Created by William Makau on 18/06/2019.
 * Company: Think Synergy Limited
 * Email: williammakau070@gmail.com
 * Phone: +254706356815
 * Purpose:
 */
class SignOutTask(private val taskListener: TaskListener) : DialogAsyncTask<Void, Void, String>() {

    override val activity: Activity
        get() = taskListener.activity

    interface TaskListener {
        val activity: Activity
        fun onComplete()
    }

    override fun doInBackground(vararg params: Void?): String {
        val cacheSignInId = PrefUtils.instance?.getString(R.string.cache_sign_in_id)
        val cachePassword = PrefUtils.instance?.getString(R.string.cache_password)
        PrefUtils.instance?.signOut()
        PrefUtils.instance?.writeString(R.string.cache_sign_in_id, cacheSignInId)
        PrefUtils.instance?.writeString(R.string.cache_password, cachePassword)
        return ""
    }

    override fun onPostExecute(result: String) {
        super.onPostExecute(result)
        taskListener.onComplete()
    }
}
