@file:Suppress("unused", "UNUSED_PARAMETER")

package ke.co.basecode.utils

import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.res.Resources
import android.graphics.Point
import android.graphics.PorterDuff
import android.net.ConnectivityManager
import android.net.Uri
import android.os.Build
import android.text.Editable
import android.text.TextWatcher
import android.util.Patterns
import android.view.Menu
import android.view.View
import android.view.WindowManager
import android.widget.EditText
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.Toast
import androidx.annotation.AttrRes
import androidx.annotation.ColorInt
import androidx.annotation.StringRes
import com.google.android.material.snackbar.Snackbar
import ke.co.basecode.R
import ke.co.basecode.logging.BeeLog


/**
 * Created by William Makau on 18/06/2019.
 * Company: Think Synergy Limited
 * Email: williammakau070@gmail.com
 * Phone: +254706356815
 * Purpose:
 */
class BaseUtils {
    //stWyc&Y3bsb3M9


    companion object {
        var density = 1f
        var displaySize = Point()
        private var screenHeight = 0
        private var screenWidth = 0
        fun canConnect(context: Context): Boolean {
            val connMgr = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val networkInfo = connMgr.activeNetworkInfo
            return networkInfo != null && networkInfo.isConnected
        }
        fun tintMenu(activity: Activity, menu: Menu?, @ColorInt color: Int) {
            if (menu != null && menu.size() > 0) {
                for (i in 0 until menu.size()) {
                    val item = menu.getItem(i)
                    val icon = item.icon
                    if (icon != null) {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            icon.setTint(color)
                        } else {
                            icon.setColorFilter(color, PorterDuff.Mode.SRC_ATOP)
                        }
                    }
                }
            }
        }
        fun tintProgressBar(progressBar: ProgressBar, @ColorInt color: Int) {
            progressBar.indeterminateDrawable.setColorFilter(color, PorterDuff.Mode.SRC_IN)
        }

        fun tintImageView(imageView: ImageView, @ColorInt color: Int) {
            imageView.setColorFilter(color)
        }


        fun showErrorSnack(view: View, msg: String) {
            Snackbar.make(view, msg, Snackbar.LENGTH_INDEFINITE)
                .setAction(android.R.string.ok) { }.show()
        }


        @SuppressLint("MissingPermission")
        fun makeCall(context: Context, phone: String) {
            val callIntent = Intent(Intent.ACTION_CALL)
            callIntent.data = Uri.parse("tel:$phone")
            if (callIntent.resolveActivity(context.packageManager) != null) {
                context.startActivity(Intent.createChooser(callIntent, "Make call..."))
            } else {
                Toast.makeText(context, "Unable to find a calling application.", Toast.LENGTH_SHORT).show()
            }
        }

        fun uuidToLong(id: String?): Long {
            return (31 * 2 + (id?.hashCode() ?: 0)).toLong()
        }


        fun sendSms(context: Context, msg: String) {
            val intent = Intent(Intent.ACTION_SEND)
            intent.type = "text/plain"
            intent.putExtra(Intent.EXTRA_TEXT, msg)
            if (intent.resolveActivity(context.packageManager) != null) {
                context.startActivity(Intent.createChooser(intent, "Send sms..."))
            } else {
                Toast.makeText(context, "Unable to find a messaging application.", Toast.LENGTH_SHORT).show()
            }
        }

        fun shareText(context: Context, text: String) {
            val sendIntent = Intent()
            sendIntent.action = Intent.ACTION_SEND
            sendIntent.putExtra(Intent.EXTRA_TEXT, text)
            sendIntent.type = "text/plain"
            val intent = Intent.createChooser(sendIntent, "Share with...")
            context.startActivity(intent)
        }

        fun sendEmail(context: Context, emailAddress: String, subject: String, body: String) {
            val emailIntent = Intent(
                Intent.ACTION_SENDTO, Uri.fromParts(
                    "mailto", emailAddress, null
                )
            )
            emailIntent.putExtra(Intent.EXTRA_SUBJECT, subject)
            emailIntent.putExtra(Intent.EXTRA_TEXT, body)
            context.startActivity(Intent.createChooser(emailIntent, "Send email..."))
        }


        fun cacheInput(editText: EditText, @StringRes key: Int, prefUtils: PrefUtilsImpl) {
            val currentInput : String? = prefUtils.getString(key)
            editText.setText(currentInput)
            editText.setSelection(currentInput?.length!!)
            editText.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {

                }

                override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
                    prefUtils.writeString(key, charSequence.toString())
                }

                override fun afterTextChanged(editable: Editable) {

                }
            })
        }

        fun getScreenHeight(context: Context): Int {
            if (screenHeight == 0) {
                val wm = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
                val display = wm.defaultDisplay
                val size = Point()
                display.getSize(size)
                screenHeight = size.y
            }

            return screenHeight
        }

        fun getScreenWidth(context: Context): Int {
            if (screenWidth == 0) {
                val wm = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
                val display = wm.defaultDisplay
                val size = Point()
                display.getSize(size)
                screenWidth = size.x
            }

            return screenWidth
        }


        fun generateColor(`object`: Any): Int {
            // return ColorGenerator.MATERIAL.getColor(object);
            return 0
        }

        fun showNetworkErrorDialog(context : Context, message : String) {
            BeeLog.d("William", message)
            val alertBuilder = AlertDialog.Builder(context)
            if (!canConnect(context)) {
                alertBuilder.setMessage("No internet connection")
            } else {
                alertBuilder.setMessage("Oops! An error occurred.")
            }
            alertBuilder.setTitle("Cube Messenger")
            alertBuilder.setIcon(R.drawable.ic_warning_24dp)
            alertBuilder.setCancelable(false)
            alertBuilder.setPositiveButton("OK") { _, _ ->
                if (!canConnect(context)) {
                    //trigger network setting
                }
            }
            val alert = alertBuilder.create()
            alert.show()
        }

        /*public static TextDrawable getTextDrawableAvatar(String text, @Nullable Object object) {
            if (object == null) {
                object = text;
            }
            return TextDrawable.builder().round().build(text, generateColor(object));
        }*/

        fun dpToPx(dp: Int): Int {
            return (dp * Resources.getSystem().displayMetrics.density).toInt()
        }

        /**
         * Get the color value for the given color attribute
         */
        @ColorInt
        fun getColor(context: Context, @AttrRes colorAttrId: Int): Int {
            val attrs = intArrayOf(colorAttrId /* index 0 */)
            val ta = context.obtainStyledAttributes(attrs)
            val colorFromTheme = ta.getColor(0, 0)
            ta.recycle()
            return colorFromTheme
        }
    }

}
