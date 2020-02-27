package ke.co.basecode.utils

import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
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

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.widget.TextView
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.math.BigDecimal
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*



/**
 * Created by William Makau on 18/06/2019.
 * Company: Think Synergy Limited
 * Email: williammakau070@gmail.com
 * Phone: +254706356815
 * Purpose:
 */
open class BaseUtils {

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

        const val PRICE_FORMAT = "%,.2f"
        private val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd hh:mm:ss", Locale.getDefault())


        fun formatPrice(price: Double): String {
            return String.format(Locale.ENGLISH, PRICE_FORMAT, price)
        }


//    fun formatCurrency(bigDecimal: BigDecimal): String {
//
//        return (App.instance?.getString(R.string.currency_code) + " "
//                + bigDecimal.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString())
//    }


        fun startNewActivity(context: Context, activityName: String) {
            val intent: Intent
            try {
                intent = Intent(context, Class.forName(activityName))
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                context.startActivity(intent)
            } catch (e: ClassNotFoundException) {

            }

        }

        fun startNewActivity(context: Context, mClass: Class<*>) {
            val intent = Intent(context, mClass)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            context.startActivity(intent)
        }

        fun getDaysOfWeek(numDays: Array<String>): String {
            val strDays = arrayOf("Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday")
            val weekDays = StringBuilder()
            for (i in numDays.indices) {
                if (i != numDays.size - 1) {
                    weekDays.append(strDays[Integer.valueOf(numDays[i])]).append(", ")
                } else {
                    weekDays.append(strDays[Integer.valueOf(numDays[i])])
                }
            }
            return weekDays.toString()
        }

        fun getPassedDays(datetime: String): String {
            //milliseconds
            var different: Long
            var elapsedTime = ""
            try {
                different = Calendar.getInstance().time.time - simpleDateFormat.parse(datetime).time
                val secondsInMilli: Long = 1000
                val minutesInMilli = secondsInMilli * 60
                val hoursInMilli = minutesInMilli * 60
                val daysInMilli = hoursInMilli * 24

                val elapsedDays = different / daysInMilli
                different %= daysInMilli
                val elapsedMonths = elapsedDays / 31
                val elapsedYears = elapsedMonths / 12

                val elapsedHours = different / hoursInMilli
                different %= hoursInMilli

                val elapsedMinutes = different / minutesInMilli
                different %= minutesInMilli

                if (elapsedYears > 0) {
                    elapsedTime = if (elapsedYears == 1L) {
                        "about $elapsedYears year ago"
                    } else {
                        "about $elapsedYears years ago"
                    }
                } else if (elapsedMonths > 0) {
                    elapsedTime = if (elapsedMonths == 1L) {
                        "about $elapsedMonths month ago"
                    } else {
                        "about $elapsedMonths months ago"
                    }
                } else if (elapsedDays > 0) {
                    elapsedTime = if (elapsedDays == 1L) {
                        "about $elapsedDays day ago"
                    } else {
                        "about $elapsedDays days ago"
                    }
                } else if (elapsedHours > 0) {
                    elapsedTime = if (elapsedHours == 1L) {
                        "about $elapsedHours hour ago"
                    } else {
                        "about $elapsedHours hours ago"
                    }
                } else if (elapsedMinutes > 0) {
                    elapsedTime = if (elapsedMinutes == 1L) {
                        "about $elapsedMinutes minute ago"
                    } else {
                        "about $elapsedMinutes minutes ago"
                    }
                } else {
                    elapsedTime = "Just now"
                }

            } catch (e: ParseException) {
                e.printStackTrace()
            }

            return elapsedTime
        }

        fun isEmailValid(email: String?): Boolean {
            if (email == null) {
                return false
            }
            return Patterns.EMAIL_ADDRESS.matcher(email).matches()
        }
        fun  stringToRequestBody(input : String) = input.toRequestBody("text/plain".toMediaTypeOrNull())

        fun getDiffBtwTimestamps(firstTimestamp: String, secondTimestamp: String): String {
            var elapsedTime = ""
            try {
                val diffInDays =
                    ((simpleDateFormat.parse(firstTimestamp).time - simpleDateFormat.parse(secondTimestamp).time) / (1000 * 60 * 60 * 24)).toInt()
                val months = diffInDays / 31
                if (months < 1) {
                    elapsedTime = "$diffInDays days ago"
                } else if (months >= 1 && months <= 12) {
                    if (months == 1) {
                        elapsedTime = "about $months month ago"
                    } else {
                        elapsedTime = "about $months months ago"
                    }
                } else if (months >= 13 && months <= 24) {
                    elapsedTime = "about $months year ago"
                } else {
                    elapsedTime = "about $months years ago"
                }
            } catch (e: ParseException) {
                e.printStackTrace()
            }
            return elapsedTime
        }

        fun convertLayoutToImage(mContext: Context, count: Int, drawableId: Int): Drawable {
            val inflater = LayoutInflater.from(mContext)
            val view : View = inflater.inflate(R.layout.badge_icon_layout, null)
            (view.findViewById(R.id.icon_badge) as ImageView).setImageResource(drawableId)
            val textView = view.findViewById(R.id.count) as TextView
            textView.setText(count)
            view.measure(
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED)
            )
            view.layout(0, 0, view.measuredWidth, view.measuredHeight)
            // view.setDrawingCacheEnabled(true)
            // view.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH)
            val bitmap = Bitmap.createBitmap(view.drawingCache)
            // view.setDrawingCacheEnabled(false)
            return BitmapDrawable(mContext.resources, bitmap)
        }
    }

}
