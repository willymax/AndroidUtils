package ke.co.basecode.logging

import android.util.Log


/**
 * Created by William Makau on 18/06/2019.
 * Company: Think Synergy Limited
 * Email: williammakau070@gmail.com
 * Phone: +254706356815
 * Purpose:
 */
object BeeLog {
    var DEBUG = false
    private var tag: String? = null

    fun init(debug: Boolean, logTag: String?) {
        tag = logTag
        DEBUG = debug
    }

    fun d(subTag: String, message: Any?) {
        if (DEBUG) {
            Log.d(tag, "$subTag : $message")
        }
    }

    fun e(subTag: String, message: Any?) {
        if (DEBUG) {
            Log.e(tag, "$subTag : $message")
        }
    }

    fun e(subTag: String, e: Exception?) {
        if (DEBUG) {
            if (e != null) {
                Log.e(tag, subTag + " : " + e.localizedMessage)
                e.printStackTrace()
            }
        }
    }

    fun e(e: Exception?) {
        if (DEBUG) {
            if (e != null) {
                Log.e(tag, " : " + e.localizedMessage)
                addToHistory(tag, e.localizedMessage)
                e.printStackTrace()
            }
        }
    }

    private fun addToHistory(subTag: String?, message: String?) {
//        executeAsync {
//            val logItem = LogItem(title = subTag.toString(), details = message.toString())
//            if (subTag?.contains("LogItem") == false){
//                LogItemsDatabase.getInstance().logItems().insert(logItem)
//            }
//        }
    }

    fun w(subTag: String, message: Any?) {
        if (DEBUG) {
            Log.w(tag, "$subTag : $message")
        }
    }


    fun i(subTag: String, message: Any?) {
        if (DEBUG) {
            Log.i(tag, "$subTag : $message")
        }
    }


    fun e(subTag: String, e: Throwable?) {
        if (DEBUG) {
            Log.e(tag, subTag + " : " + e?.message)
        }
    }
}// no instance