package ke.co.basecode.utils

import android.app.Activity
import android.app.Dialog
import android.content.ActivityNotFoundException
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Build
import android.provider.Settings
import android.util.TypedValue
import android.view.Menu
import android.view.View
import android.view.WindowManager
import android.widget.ImageView
import android.widget.Toast

import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.core.widget.NestedScrollView

import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.google.android.gms.maps.GoogleMap

import java.net.URI
import java.net.URISyntaxException
import java.text.SimpleDateFormat

import ke.co.basecode.R
import java.util.*
import kotlin.math.roundToInt

object Tools {
    val screenWidth: Int
        get() = Resources.getSystem().displayMetrics.widthPixels

    val screenHeight: Int
        get() = Resources.getSystem().displayMetrics.heightPixels

    /**
     * For device info parameters
     */
    val deviceName: String
        get() {
            val manufacturer = Build.MANUFACTURER
            val model = Build.MODEL
            return if (model.startsWith(manufacturer)) {
                model
            } else {
                "$manufacturer $model"
            }
        }

    val androidVersion: String
        get() = Build.VERSION.RELEASE + ""

    fun setSystemBarColor(act: Activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val window = act.window
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            window.statusBarColor = act.resources.getColor(R.color.colorPrimaryDark)
        }
    }

    fun setSystemBarColor(act: Activity, @ColorRes color: Int) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val window = act.window
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            window.statusBarColor = act.resources.getColor(color)
        }
    }

    fun setSystemBarColorInt(act: Activity, @ColorInt color: Int) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val window = act.window
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            window.statusBarColor = color
        }
    }

    fun setSystemBarColorDialog(act: Context, dialog: Dialog, @ColorRes color: Int) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val window = dialog.window
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            window.statusBarColor = act.resources.getColor(color)
        }
    }

    fun setSystemBarLight(act: Activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val view = act.findViewById<View>(android.R.id.content)
            var flags = view.systemUiVisibility
            flags = flags or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            view.systemUiVisibility = flags
        }
    }

    fun setSystemBarLightDialog(dialog: Dialog) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val view = dialog.findViewById<View>(android.R.id.content)
            var flags = view.systemUiVisibility
            flags = flags or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            view.systemUiVisibility = flags
        }
    }

    fun clearSystemBarLight(act: Activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val window = act.window
            window.statusBarColor = ContextCompat.getColor(act, R.color.colorPrimaryDark)
        }
    }

    /**
     * Making notification bar transparent
     */
    fun setSystemBarTransparent(act: Activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val window = act.window
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.statusBarColor = Color.TRANSPARENT
        }
    }

    fun displayImageOriginal(ctx: Context, img: ImageView, @DrawableRes drawable: Int) {
        try {
            Glide.with(ctx).load(drawable)
                //                    .crossFade()
                //                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                .into(img)
        } catch (e: Exception) {
        }

    }

    //    public static void displayImageRound(final Context ctx, final ImageView img, @DrawableRes int drawable) {
    //        try {
    //            Glide.with(ctx).load(drawable).asBitmap().centerCrop().into(new BitmapImageViewTarget(img) {
    //                @Override
    //                protected void setResource(Bitmap resource) {
    //                    RoundedBitmapDrawable circularBitmapDrawable = RoundedBitmapDrawableFactory.create(ctx.getResources(), resource);
    //                    circularBitmapDrawable.setCircular(true);
    //                    img.setImageDrawable(circularBitmapDrawable);
    //                }
    //            });
    //        } catch (Exception e) {
    //        }
    //    }

    fun displayImageOriginal(ctx: Context, img: ImageView, url: String, onError: (Exception?) -> Unit?, onSuccess: (Drawable?) -> Unit?) {
        try {
            Glide.with(ctx)
                .load(url)
                .fitCenter()
                .listener(object : RequestListener<Drawable?> {
                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: Target<Drawable?>?,
                        isFirstResource: Boolean
                    ): Boolean {
                        onError(e)
                        return false
                    }

                    override fun onResourceReady(
                        resource: Drawable?,
                        model: Any?,
                        target: Target<Drawable?>?,
                        dataSource: DataSource?,
                        isFirstResource: Boolean
                    ): Boolean {
                        onSuccess(resource)
                        return false
                    }
                })
                //                    .crossFade()
                //                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                .into(img)
        } catch (e: Exception) {
        }

    }

    fun getFormattedDateShort(dateTime: Long): String {
        val newFormat = SimpleDateFormat("MMM dd, yyyy", Locale.US)
        return newFormat.format(Date(dateTime))
    }

    fun getFormattedDateSimple(dateTime: Long): String {
        val newFormat = SimpleDateFormat("MMMM dd, yyyy", Locale.US)
        return newFormat.format(Date(dateTime))
    }

    fun getFormattedDateEvent(dateTime: Long): String {
        val newFormat = SimpleDateFormat("EEE, MMM dd yyyy", Locale.US)
        return newFormat.format(Date(dateTime))
    }

    fun getFormattedTimeEvent(time: Long): String {
        val newFormat = SimpleDateFormat("h:mm a", Locale.US)
        return newFormat.format(Date(time))
    }

    fun getEmailFromName(name: String?): String? {
        return if (name != null && name != "") {
            name.replace(" ", ".").toLowerCase(Locale.US).plus("@mail.com")
        } else name
    }

    fun dpToPx(c: Context, dp: Int): Int {
        val r = c.resources
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            dp.toFloat(),
            r.displayMetrics
        ).roundToInt()
    }

    fun configActivityMaps(googleMap: GoogleMap): GoogleMap {
        // set map type
        googleMap.mapType = GoogleMap.MAP_TYPE_NORMAL
        // Enable / Disable zooming controls
        googleMap.uiSettings.isZoomControlsEnabled = false

        // Enable / Disable Compass icon
        googleMap.uiSettings.isCompassEnabled = true
        // Enable / Disable Rotate gesture
        googleMap.uiSettings.isRotateGesturesEnabled = true
        // Enable / Disable zooming functionality
        googleMap.uiSettings.isZoomGesturesEnabled = true

        googleMap.uiSettings.isScrollGesturesEnabled = true
        googleMap.uiSettings.isMapToolbarEnabled = true

        return googleMap
    }

    fun copyToClipboard(context: Context, data: String) {
        val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clip = ClipData.newPlainText("clipboard", data)
        clipboard.primaryClip = clip
        Toast.makeText(context, "Text copied to clipboard", Toast.LENGTH_SHORT).show()
    }

    fun nestedScrollTo(nested: NestedScrollView, targetView: View) {
        nested.post { nested.scrollTo(500, targetView.bottom) }
    }

    fun dip2px(context: Context, dpValue: Float): Int {
        val scale = context.resources.displayMetrics.density
        return (dpValue * scale + 0.5f).toInt()
    }

    fun px2dip(context: Context, pxValue: Float): Int {
        val scale = context.resources.displayMetrics.density
        return (pxValue / scale + 0.5f).toInt()
    }

    fun toggleArrow(view: View): Boolean {
        if (view.rotation == 0.toFloat()) {
            view.animate().setDuration(200).rotation(180.toFloat())
            return true
        } else {
            view.animate().setDuration(200).rotation(0.toFloat())
            return false
        }
    }

    @JvmOverloads
    fun toggleArrow(show: Boolean, view: View, delay: Boolean = true): Boolean {
        if (show) {
            view.animate().setDuration(if (delay) 200 else 0).rotation(180.toFloat())
            return true
        } else {
            view.animate().setDuration(if (delay) 200 else 0).rotation(0.toFloat())
            return false
        }
    }

    fun changeNavigateionIconColor(toolbar: Toolbar, @ColorInt color: Int) {
        val drawable = toolbar.navigationIcon
        drawable?.mutate()
        drawable?.setColorFilter(color, PorterDuff.Mode.SRC_ATOP)
    }

    fun changeMenuIconColor(menu: Menu, @ColorInt color: Int) {
        for (i in 0 until menu.size()) {
            val drawable = menu.getItem(i).icon ?: continue
            drawable.mutate()
            drawable.setColorFilter(color, PorterDuff.Mode.SRC_ATOP)
        }
    }

    fun changeOverflowMenuIconColor(toolbar: Toolbar, @ColorInt color: Int) {
        try {
            val drawable = toolbar.overflowIcon
            drawable?.mutate()
            drawable?.setColorFilter(color, PorterDuff.Mode.SRC_ATOP)
        } catch (e: Exception) {
        }

    }

    fun toCamelCase(input: String): String {
        var input = input
        input = input.toLowerCase(Locale.US)
        val titleCase = StringBuilder()
        var nextTitleCase = true

        for (c in input.toCharArray()) {
            if (Character.isSpaceChar(c)) {
                nextTitleCase = true
            } else if (nextTitleCase) {
                Character.toTitleCase(c)
                nextTitleCase = false
            }

            titleCase.append(c)
        }

        return titleCase.toString()
    }

    fun insertPeriodically(text: String, insert: String, period: Int): String {
        val builder = StringBuilder(text.length + insert.length * (text.length / period) + 1)
        var index = 0
        var prefix = ""
        while (index < text.length) {
            builder.append(prefix)
            prefix = insert
            builder.append(text.substring(index, (index + period).coerceAtMost(text.length)))
            index += period
        }
        return builder.toString()
    }


    fun rateAction(activity: Activity) {
        val uri = Uri.parse("market://details?id=" + activity.packageName)
        val goToMarket = Intent(Intent.ACTION_VIEW, uri)
        try {
            activity.startActivity(goToMarket)
        } catch (e: ActivityNotFoundException) {
            activity.startActivity(
                Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("http://play.google.com/store/apps/details?id=" + activity.packageName)
                )
            )
        }

    }

    fun getVersionCode(ctx: Context): Int {
        try {
            val manager = ctx.packageManager
            val info = manager.getPackageInfo(ctx.packageName, 0)
            return info.versionCode
        } catch (e: PackageManager.NameNotFoundException) {
            return -1
        }

    }


    //    public static String getVersionName(Context ctx) {
    //        try {
    //            PackageManager manager = ctx.getPackageManager();
    //            PackageInfo info = manager.getPackageInfo(ctx.getPackageName(), 0);
    //            return ctx.getString(R.string.app_version) + " " + info.versionName;
    //        } catch (PackageManager.NameNotFoundException e) {
    //            return ctx.getString(R.string.version_unknown);
    //        }
    //    }

    //    public static String getVersionNamePlain(Context ctx) {
    //        try {
    //            PackageManager manager = ctx.getPackageManager();
    //            PackageInfo info = manager.getPackageInfo(ctx.getPackageName(), 0);
    //            return info.versionName;
    //        } catch (PackageManager.NameNotFoundException e) {
    //            return ctx.getString(R.string.version_unknown);
    //        }
    //    }

    //    public static DeviceInfo getDeviceInfo(Context context) {
    //        DeviceInfo deviceInfo = new DeviceInfo();
    //        deviceInfo.device = Tools.getDeviceName();
    //        deviceInfo.os_version = Tools.getAndroidVersion();
    //        deviceInfo.app_version = Tools.getVersionCode(context) + " (" + Tools.getVersionNamePlain(context) + ")";
    //        deviceInfo.serial = Tools.getDeviceID(context);
    //        return deviceInfo;
    //    }

    fun getDeviceID(context: Context): String? {
        var deviceID = Build.SERIAL
        if (deviceID == null || deviceID.trim().isEmpty() || deviceID!!.equals("unknown")) {
            try {
                deviceID = Settings.Secure.getString(
                    context.getContentResolver(),
                    Settings.Secure.ANDROID_ID
                )
            } catch (e: Exception) {
            }

        }
        return deviceID
    }

    fun getFormattedDateOnly(dateTime: Long): String {
        val newFormat = SimpleDateFormat("dd MMM yy")
        return newFormat.format(Date(dateTime))
    }

    fun directLinkToBrowser(activity: Activity, url: String) {
        try {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            activity.startActivity(intent)
        } catch (e: Exception) {
            Toast.makeText(activity, "Ops, Cannot open url", Toast.LENGTH_LONG).show()
        }

    }

    private fun appendQuery(uri: String, appendQuery: String): String {
        try {
            val oldUri = URI(uri)
            var newQuery = oldUri.query
            if (newQuery == null) {
                newQuery = appendQuery
            } else {
                newQuery += "&$appendQuery"
            }
            val newUri = URI(
                oldUri.scheme,
                oldUri.authority,
                oldUri.path, newQuery, oldUri.fragment
            )
            return newUri.toString()
        } catch (e: Exception) {
            e.printStackTrace()
            return uri
        }

    }

    //    public static void openInAppBrowser(Activity activity, String url, boolean from_notif) {
    //        url = appendQuery(url, "t=" + System.currentTimeMillis());
    //        if (!URLUtil.isValidUrl(url)) {
    //            Toast.makeText(activity, "Ops, Cannot open url", Toast.LENGTH_LONG).show();
    //            return;
    //        }
    //        ActivityWebView.navigate(activity, url, from_notif);
    //    }

    fun getHostName(url: String): String {
        try {
            val uri = URI(url)
            var newUrl = uri.host
            if (!newUrl.startsWith("www.")) newUrl = "www.$newUrl"
            return newUrl
        } catch (e: URISyntaxException) {
            e.printStackTrace()
            return url
        }

    }
}
