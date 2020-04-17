package ke.co.basecode.authentication.utilities

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import ke.co.basecode.authentication.login.User
import com.google.gson.GsonBuilder
import ke.co.basecode.R
import ke.co.basecode.utils.PrefUtilsImpl


/**
 * Created by William Makau on 18/06/2019.
 * Company: Think Synergy Limited
 * Email: williammakau070@gmail.com
 * Phone: +254706356815
 * Purpose:
 */
class PrefUtils(val context: Context, private val sharedPreferences: SharedPreferences) : PrefUtilsImpl(context, sharedPreferences) {

    private var user: User? = null

//    protected override val sharedPreferences: SharedPreferences
//        get() {
//            if (mSharedPreferences == null) {
//                mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
//            }
//            return this.mSharedPreferences!!
//        }
//
//    protected override val context: Context
//        get() = App.getInstance()


    fun signOut() {
        this.clear()
        invalidate()
    }

    fun saveUser(user: User?) {
        val gsonBuilder = GsonBuilder().create()
        writeString(R.string.pref_user, gsonBuilder.toJson(user))
        invalidate()
    }

    fun getUser(): User? {
        if (user == null) {
            val userJson = getString(R.string.pref_user, "")
            return if (userJson.isNullOrEmpty()) {
                null
            } else {
                val gsonBuilder = GsonBuilder().create()
                this.user = gsonBuilder.fromJson(userJson, User::class.java)
                this.user
            }
        }
        return user
    }


    /**
     * Load shared prefs again
     */
    override fun invalidate() {
        mInstance = null
    }

    companion object {
        private val TAG = PrefUtils::class.java.simpleName
        @SuppressLint("StaticFieldLeak")
        @Volatile
        private var mInstance: PrefUtils? = null

        fun instance(context: Context, sharedPreferences: SharedPreferences): PrefUtils {
            if (mInstance == null) {
                mInstance =
                    PrefUtils(context, sharedPreferences)
            }
            return mInstance as PrefUtils
        }
    }
}