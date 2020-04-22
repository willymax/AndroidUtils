package ke.co.basecode

import android.content.Context
import android.content.SharedPreferences
import ke.co.basecode.authentication.utilities.PrefUtils

/**
 * Created by Willy on 17/04/2020
 * Email williammakau070@gmail.com
 * @author willi
 */
class BaseCodeInit private constructor(
    private val context: Context,
    private val sharedPreferences: SharedPreferences
) {
    fun getPrefUtils() = PrefUtils.instance(context, sharedPreferences)

    companion object {
        private var mInstance: BaseCodeInit? = null

        fun init(context: Context, sharedPreferences: SharedPreferences) {
            if (mInstance == null) {
                mInstance = BaseCodeInit(context, sharedPreferences)
            }
        }

        fun getInstance(): BaseCodeInit {
            return mInstance?.let {
                return@let it
            }
                ?: throw throw IllegalArgumentException("BaseCodeInit has not been initialized," + " it should be initialized once, most probably in the Application class")
        }
    }
}