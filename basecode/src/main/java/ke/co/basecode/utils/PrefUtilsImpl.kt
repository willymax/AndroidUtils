package ke.co.basecode.utils

import android.content.Context
import android.content.SharedPreferences
import androidx.annotation.StringRes


/**
 * Created by William Makau on 18/06/2019.
 * Company: Think Synergy Limited
 * Email: williammakau070@gmail.com
 * Phone: +254706356815
 * Purpose:
 */
abstract class PrefUtilsImpl {
    protected abstract val sharedPreferences: SharedPreferences

    protected abstract val context: Context

    protected abstract fun invalidate()

    fun remove(@StringRes key: Int) {
        sharedPreferences.edit().remove(resolveKey(key)).apply()
        invalidate()
    }

    fun writeString(@StringRes key: Int, value: String?) {
        sharedPreferences.edit().putString(resolveKey(key), value).apply()
        invalidate()
    }

    fun getString(@StringRes key: Int): String {
        return sharedPreferences.getString(resolveKey(key), "")!!
    }

    fun getString(@StringRes key: Int, defVal: String): String {
        return sharedPreferences.getString(resolveKey(key), defVal)!!
    }

    fun getInt(@StringRes key: Int): Int {
        return sharedPreferences.getInt(resolveKey(key), 0)
    }

    fun writeLong(@StringRes key: Int, value: Long) {
        sharedPreferences.edit().putLong(resolveKey(key), value).apply()
        invalidate()
    }

    fun getLong(@StringRes key: Int): Long {
        return sharedPreferences.getLong(resolveKey(key), 0)
    }

    fun getLong(@StringRes key: Int, defVal: Int): Long {
        return sharedPreferences.getLong(resolveKey(key), defVal.toLong())
    }


    fun getBoolean(@StringRes key: Int, defVal: Boolean): Boolean {
        return sharedPreferences.getBoolean(resolveKey(key), defVal)
    }

    fun writeInt(@StringRes key: Int, `val`: Int) {
        sharedPreferences.edit().putInt(resolveKey(key), `val`).apply()
    }

    fun writeBoolean(@StringRes key: Int, value: Boolean) {
        sharedPreferences.edit().putBoolean(resolveKey(key), value).apply()
        invalidate()
    }

    fun getBoolean(@StringRes key: Int): Boolean {
        return sharedPreferences.getBoolean(resolveKey(key), false)
    }

    fun clear() {
        sharedPreferences.edit().clear().apply()
        invalidate()
    }

    private fun resolveKey(@StringRes key: Int): String {
        return "key_" + context.resources.getResourceEntryName(key)
        //return String.valueOf("key_" + key);
        //return getContext().getString(key).trim().replaceAll(" ","");
    }


}