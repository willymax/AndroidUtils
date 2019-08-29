package ke.co.basecode.utils

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import ke.co.basecode.App
import ke.co.basecode.model.User
import com.squareup.moshi.Moshi
import ke.co.basecode.R


/**
 * Created by William Makau on 18/06/2019.
 * Company: Think Synergy Limited
 * Email: williammakau070@gmail.com
 * Phone: +254706356815
 * Purpose:
 */
class PrefUtils private constructor() : PrefUtilsImpl() {
    private var user: User? = null
    private var mSharedPreferences: SharedPreferences? = null

    protected override val sharedPreferences: SharedPreferences
        get() {
            if (mSharedPreferences == null) {
                mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
            }
            return this.mSharedPreferences!!
        }

    protected override val context: Context
        get() = App.instance!!

    val isPurchasingHead: Boolean
        get() = getUser()?.role?.name == "PURCHASING_HEAD"

    val isDepartmentHead: Boolean
        get() = getUser()?.role?.name == "DEPARTMENT_HEAD"
    val isDepartmentUser: Boolean
        get() = getUser()?.role?.name == "DEPARTMENT_USER"


    fun signOut() {
        sharedPreferences.edit().clear().apply()
        invalidate()
    }

    fun saveUser(user: User?) {
        val moshi = Moshi.Builder().build()
        val jsonAdapter = moshi.adapter<Any>(User::class.java)
        writeString(R.string.pref_user, jsonAdapter.toJson(user))
        invalidate()
    }

    fun getUser(): User? {
        if (user == null) {
            val userJson = getString(R.string.pref_user, "")
            return if (userJson.isEmpty()) {
                null
            } else {
                val moshi = Moshi.Builder().build()
                val jsonAdapter = moshi.adapter(User::class.java)
                this.user = jsonAdapter.fromJson(userJson)
                user
            }
        }
        return user
    }


    /**
     * Load shared prefs again
     */
    protected override fun invalidate() {
        mInstance = null
    }

    companion object {

        private val TAG = PrefUtils::class.java.simpleName
        @Volatile
        private var mInstance: PrefUtils? = null

        val instance: PrefUtils?
            get() {
                if (mInstance == null) {
                    mInstance = PrefUtils()
                }
                return mInstance
            }
    }
}