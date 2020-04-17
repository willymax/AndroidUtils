package ke.co.basecode.utils

import android.content.Context
import android.content.SharedPreferences
import androidx.annotation.StringRes
import ke.co.basecode.logging.BeeLog


/**
 * Created by William Makau on 18/06/2019.
 * Company: Think Synergy Limited
 * Email: williammakau070@gmail.com
 * Phone: +254706356815
 * Purpose:
 */
open class PrefUtilsImpl(private val context: Context, private val sharedPreferences: SharedPreferences) {

    protected open fun invalidate() {}

    open fun remove(@StringRes key: Int) {
        sharedPreferences.edit().remove(resolveKey(key)).apply()
        invalidate()
    }

    fun writeString(@StringRes key: Int, value: String?) {
        sharedPreferences.edit().putString(resolveKey(key), value).apply()
        invalidate()
    }

    fun getString(@StringRes key: Int): String? {
        return sharedPreferences.getString(resolveKey(key), "")
    }

    fun getString(@StringRes key: Int, defVal: String): String? {
        return try {
            sharedPreferences.getString(resolveKey(key), defVal)
        } catch (e: Exception){
            BeeLog.e(e)
            defVal
        }
    }

    fun getInt(@StringRes key: Int): Int {
        return try {
            sharedPreferences.getInt(resolveKey(key), 0)
        } catch (ex: Exception){
            BeeLog.e(ex)
            0
        }
    }

    fun writeLong(@StringRes key: Int, value: Long) {
        sharedPreferences.edit().putLong(resolveKey(key), value).apply()
        invalidate()
    }

    fun getLong(@StringRes key: Int): Long {
        return try {
            sharedPreferences.getLong(resolveKey(key), 0)
        } catch (e: Exception) {
            BeeLog.e(e)
            0
        }
    }

    fun getLong(@StringRes key: Int, defVal: Long): Long {
        return try {
            sharedPreferences.getLong(resolveKey(key), defVal.toLong())
        } catch (ex: Exception) {
            BeeLog.e(ex)
            defVal
        }
    }


    fun getBoolean(@StringRes key: Int, defVal: Boolean): Boolean {
        return try {
            sharedPreferences.getBoolean(resolveKey(key), defVal)
        } catch (ex: Exception) {
            BeeLog.e(ex)
            defVal
        }
    }

    fun writeInt(@StringRes key: Int, `val`: Int) {
        sharedPreferences.edit().putInt(resolveKey(key), `val`).apply()
    }

    fun writeBoolean(@StringRes key: Int, value: Boolean) {
        sharedPreferences.edit().putBoolean(resolveKey(key), value).apply()
        invalidate()
    }

    fun getBoolean(@StringRes key: Int): Boolean {
        return try {
            sharedPreferences.getBoolean(resolveKey(key), false)
        } catch (e: Exception) {
            BeeLog.e(e)
            false
        }
    }

    public fun clear() {
        sharedPreferences.edit().clear().apply()
        invalidate()
    }

    private fun resolveKey(@StringRes key: Int): String {
        return "key_" + context.resources.getResourceEntryName(key)
        //return String.valueOf("key_" + key);
        //return getContext().getString(key).trim().replaceAll(" ","");
    }

    companion object {
        private const val TAG = "PrefUtilsImpl"
    }
}