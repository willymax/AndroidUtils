package ke.co.basecode.utils

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import ke.co.basecode.R
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
object Utils {
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
