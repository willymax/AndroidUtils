package ke.co.basecode.utils

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.StringRes
import com.google.android.material.bottomsheet.BottomSheetDialog
import ke.co.basecode.R


/**
 * Created by William Makau on 18/06/2019.
 * Company: Think Synergy Limited
 * Email: williammakau070@gmail.com
 * Phone: +254706356815
 * Purpose:
 */
class BaseProgressDialog(private val mContext: Context) {
    private val mBottomSheetDialog: BottomSheetDialog?
    private val mView: View
    private val mMessageTV: TextView

    val isShowing: Boolean
        get() = mBottomSheetDialog != null && mBottomSheetDialog.isShowing

    init {
        this.mBottomSheetDialog = BottomSheetDialog(mContext)
        val nullParent : ViewGroup? = null
        this.mView = LayoutInflater.from(mContext).inflate(R.layout.view_progress_dialog, nullParent, false)
        this.mMessageTV = mView.findViewById(R.id.messageTV)
    }


    fun setMessage(message: String): BaseProgressDialog {
        mMessageTV.text = message
        return this
    }

    @Suppress("unused")
    fun setMessage(@StringRes message: Int): BaseProgressDialog {
        setMessage(mContext.getString(message))
        return this
    }

    fun setCancelable(cancellable: Boolean): BaseProgressDialog {
        mBottomSheetDialog?.setCancelable(cancellable)
        return this
    }

    fun show() {
        mBottomSheetDialog?.setContentView(mView)
        mBottomSheetDialog?.show()
    }

    fun dismiss() {
        mBottomSheetDialog?.dismiss()
    }
}
