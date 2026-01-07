package com.pradheep.roomdb.utils.common_methods

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.Drawable
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.text.InputFilter
import android.text.Spannable
import android.text.SpannableString
import android.text.Spanned
import android.text.style.AbsoluteSizeSpan
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import android.util.Base64
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatDialog
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import androidx.core.view.ViewCompat
import org.json.JSONObject
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.StringTokenizer
import java.util.UUID
import java.util.concurrent.TimeUnit
import kotlin.random.Random
import android.content.res.Configuration
import android.graphics.drawable.ColorDrawable
import android.net.wifi.WifiManager
import android.os.Bundle
import android.telephony.TelephonyManager
import com.pradheep.roomdb.R
import pl.droidsonroids.gif.GifDrawable
import java.io.IOException
import java.util.Locale

const val EMAIL_PATTERN = ("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
        + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$")

fun dismissDialog(context: Context?, pd: AppCompatDialog?) {
    try {
        if (pd != null && pd.isShowing) {
            pd.dismiss()
        }
    } catch (e: java.lang.Exception) {
        e.printStackTrace()
    }
}

@SuppressLint("SimpleDateFormat")
fun convertDate(date: String?, inputFormate: String, outputFormate: String): String {
    try {
        val inputFormate = SimpleDateFormat(inputFormate)
        val date1 = inputFormate.parse(date)
        val outputFormate = SimpleDateFormat(outputFormate)
        Log.e("convertDate", "done = " + outputFormate.format(date1))
        return outputFormate.format(date1)
    } catch (e: Exception) {
        e.printStackTrace()
        Log.e("convertDate", "error = " + e.message)
    }
    return ""
}

fun getProgressDialog(context: Context): AppCompatDialog {
    val myCustomProgressDialog = MyCustomProgressDialog(context)
    myCustomProgressDialog.setCanceledOnTouchOutside(false)
    try {
        myCustomProgressDialog.show()
    }catch  (e: WindowManager.BadTokenException) {
        Log.e("WindowManagerBad ", e.toString())
    }
    return myCustomProgressDialog
}


@RequiresApi(Build.VERSION_CODES.P)
fun isOnline(context: Context): Boolean {
    val connectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    if (connectivityManager != null) {
        val capabilities =
            connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
        if (capabilities != null) {
            when {
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> {
                    Log.i("Internet", "NetworkCapabilities.TRANSPORT_CELLULAR")
                    val telephonyManager = context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
                    val cellSignalStrengths = telephonyManager.signalStrength
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                        if (cellSignalStrengths != null && cellSignalStrengths.level < 0) {
                            val signalStrength =
                                cellSignalStrengths.level // Get the level of the first signal strength
                            if (signalStrength < 2) { // Assuming < 2 is considered weak
                                toast(
                                    "Weak cellular connection, please check your signal.",
                                    false,
                                    context
                                )
                            }
                        }
                    }else{
                        // Fallback for older Android versions
                        if (capabilities.linkDownstreamBandwidthKbps < 150) {
                            toast("Weak cellular connection, please check your signal.", false, context)
                        }
                    }
                    return true
                }

                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> {
                    Log.i("Internet", "NetworkCapabilities.TRANSPORT_WIFI")
                    val wifiStrength = getWifiSignalStrength(context)
                    if (wifiStrength < 2) { // Assuming < 2 is considered weak
                        toast("Weak Wi-Fi connection, please check your signal.", false, context)
                    }
                    return true
                }

                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> {
                    Log.i("Internet", "NetworkCapabilities.TRANSPORT_ETHERNET")
                    return true
                }

                else -> {
                    // Handle unknown transport types
                    toast("Unknown network type.", false, context)
                    return false
                }
            }
        } else {
            toast("It looks like there is a connection issue. Please check your Wi-Fi or data settings.", false, context)
            return false
        }
    }
    return false
}


// Helper function to check Wi-Fi signal strength
@RequiresApi(Build.VERSION_CODES.R)
private fun getWifiSignalStrength(context: Context): Int {
    val wifiManager = context.applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager
    val wifiInfo = wifiManager.connectionInfo
    return wifiManager.calculateSignalLevel(wifiInfo.rssi) // Returns signal level from 0 (poor) to 4 (excellent)
}


fun dismissProgressDialog(pd: AppCompatDialog?) {
    try {
        if (pd != null && pd.isShowing) {
            pd.dismiss()
        }
    } catch (_: java.lang.Exception) {
    }
}

fun isNetWork(context: Context): Boolean {
    return isNetworkAvailable(context)
}

fun isNetworkAvailable(context: Context): Boolean {
    val connectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val activeNetworkInfo = connectivityManager.activeNetworkInfo
    return activeNetworkInfo != null
}



private var toast: android.widget.Toast? = null

fun toast(msg: Any?, isShort: Boolean = true, app: Context) {
    msg.let {
        if (toast == null) {
            toast = android.widget.Toast.makeText(
                app,
                msg.toString(),
                android.widget.Toast.LENGTH_SHORT
            )
        } else {
            toast!!.setText(msg.toString())

        }
        toast!!.duration =
            if (isShort) android.widget.Toast.LENGTH_SHORT else android.widget.Toast.LENGTH_LONG
        toast!!.show()
    }
}


fun changeTimeFormat(lastUpdated: String): String {

    Log.e("dateee12", lastUpdated)
    var timeStr = ""
    val tk = StringTokenizer(lastUpdated)
    val time = tk.nextToken()
    val date = lastUpdated.substring(lastUpdated.indexOf('-'), lastUpdated.length)
    Log.e("dateee12", date)
    val sdf = SimpleDateFormat("H:mm")
    val sdfs = SimpleDateFormat("hh:mm a")
    val dt: Date
    try {
        dt = sdf.parse(time)
        Log.e("lastUpdated123", sdfs.format(dt).toString())
        timeStr = sdfs.format(dt).toString() +" "+date
    } catch (e: ParseException) {
        e.printStackTrace()
    }
    return timeStr
}

/**
 * Function to setup calendar for every month
 */

fun calculateDifference(fromDate: String,toDate: String): Long {
    // Define the date format
    val dateFormat = SimpleDateFormat("yyyy-MM-dd")
    // Parse the past date string into a Date object
    val pastDate = dateFormat.parse(fromDate)
    val currentDate=dateFormat.parse(toDate)
    // Calculate the difference between current date and past date
    val differenceInMillis = currentDate.time - pastDate.time

    // Convert the time difference to days
    val differenceInDays = TimeUnit.MILLISECONDS.toDays(differenceInMillis)
    println("differenceInDays $differenceInDays")
    return differenceInDays
}

fun calculateDateDifference(pastDateString: String): Long {
    // Define the date format
    val dateFormat = SimpleDateFormat("yyyy-MM-dd")

    // Get the current date
    val currentDate = Date()
    Log.e("current date",currentDate.toString())

    // Parse the past date string into a Date object
    val pastDate = dateFormat.parse(pastDateString)

    // Calculate the difference between current date and past date
    val differenceInMillis = currentDate.time - pastDate.time

    // Convert the time difference to days
    val differenceInDays = TimeUnit.MILLISECONDS.toDays(differenceInMillis)
    println("differenceInDays $differenceInDays")
    return differenceInDays

}


// Method to apply style to TextView
fun applyStyleToText(
    textView: TextView,
    context: Context,
    text: String,
    firstLetterTextSize: Float,
    remainingTextSize: Float
) {
    val spannableString = SpannableString(text)

    // Set the first letter to be bold and have a different color and size
    spannableString.setSpan(StyleSpan(Typeface.BOLD), 0, 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
    // Set text color for the first letter
    val color = Color.parseColor("#1C77CA")
    spannableString.setSpan(
        ForegroundColorSpan(color),
        0,
        1,
        Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
    )

    // Convert dp to pixels for text sizes
    val density = context.resources.displayMetrics.density
    val firstLetterSizePixels = (firstLetterTextSize * density).toInt()
    val remainingTextSizePixels = (remainingTextSize * density).toInt()

    // Set the size for the first letter
    spannableString.setSpan(
        AbsoluteSizeSpan(firstLetterSizePixels),
        0,
        1,
        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
    )

    // Set the size for the rest of the text
    spannableString.setSpan(
        AbsoluteSizeSpan(remainingTextSizePixels),
        1,
        spannableString.length,
        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
    )

    // Apply the styled text to the TextView
    textView.text = spannableString
}


// its help for remove the white space
fun restrictSpaces(editText: EditText) {
    val filter = object : InputFilter {
        override fun filter(
            source: CharSequence?,
            start: Int,
            end: Int,
            dest: Spanned?,
            dstart: Int,
            dend: Int
        ): CharSequence? {
            source?.let {
                for (i in start until end) {
                    if (Character.isWhitespace(it[i])) {
                        return "" // Remove spaces
                    }
                }
            }
            return null
        }
    }

    editText.filters = arrayOf(filter)
}

fun setViewBackgroundTint(view: View, colorResId: Int) {
    val color = ContextCompat.getColor(view.context, colorResId)
    ViewCompat.setBackgroundTintList(view, ColorStateList.valueOf(color))
}

fun getValueFromJson(jsonString: String, key: String): String? {
    return try {
        val jsonObject = JSONObject(jsonString)
        jsonObject.getString(key)
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}


fun EditText.setupPasswordToggle() {
    // Set initial state
    this.transformationMethod = android.text.method.PasswordTransformationMethod.getInstance()
    val drawable = ContextCompat.getDrawable(context, com.google.android.material.R.drawable.m3_password_eye)
    drawable?.let { setDrawableEnd(it) }

    // Set click listener for the drawable end
    this.setOnTouchListener { _, event ->
        if (event.action == android.view.MotionEvent.ACTION_UP) {
            if (event.rawX >= (this.right - this.compoundDrawables[2].bounds.width())) {
                // Toggle password visibility
                this.transformationMethod =
                    if (this.transformationMethod == android.text.method.PasswordTransformationMethod.getInstance())
                        android.text.method.HideReturnsTransformationMethod.getInstance()
                    else
                        android.text.method.PasswordTransformationMethod.getInstance()

                // Toggle eye icon
                val drawableResId = if (this.transformationMethod == android.text.method.PasswordTransformationMethod.getInstance())
                    androidx.appcompat.R.drawable.abc_btn_radio_material
                else
                    androidx.appcompat.R.drawable.abc_btn_check_material
                val drawable = ContextCompat.getDrawable(context, drawableResId)
                drawable?.let { setDrawableEnd(it) }

                // Move cursor to the end of the text
                this.setSelection(this.text.length)

                return@setOnTouchListener true
            }
        }
        false
    }
}

private fun EditText.setDrawableEnd(drawable: Drawable) {
    val wrappedDrawable = DrawableCompat.wrap(drawable)
//    DrawableCompat.setTint(wrappedDrawable, ContextCompat.getColor(context, R.color.your_color))
    this.setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, wrappedDrawable, null)
}

fun Activity.hideSoftKeyboard() {
    currentFocus?.let {
        val inputMethodManager = ContextCompat.getSystemService(this, InputMethodManager::class.java)!!
        inputMethodManager.hideSoftInputFromWindow(it.windowToken, 0)
    }
}

fun setupUI(view: View) {

    // Set up touch listener for non-text box views to hide keyboard.
    if (view !is EditText) {
        view.setOnTouchListener { v, event ->
            view.clearFocus()
            false
        }
    }

    //If a layout container, iterate over children and seed recursion.
    if (view is ViewGroup) {
        for (i in 0 until view.childCount) {
            val innerView = view.getChildAt(i)
            setupUI(innerView)
        }
    }
}

//Generate Random Integers within a Range:
fun randomIntegers(index:Int) :Int{
    return Random.nextInt(1, index); // Generate a random integer between 1 and 100
//    println("Random Integer in Range: $randomIntInRange")
}

// show with some animation function
fun fadeInView(view: View) {
    view.apply {
        scaleX = 0.7f
        scaleY = 0.7f
        visibility = View.VISIBLE
        animate()
            .scaleX(1f)
            .scaleY(1f)
            .alpha(1f)
            .setDuration(300)
            .setListener(null)
    }
}

fun fadeOutView(view: View) {
    view.apply {
        animate()
            .scaleX(0.6f)
            .scaleY(0.6f)
            .alpha(0f)
            .setDuration(100)
            .setListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator) {
                    view.visibility = View.GONE
                }
            })
    }
}

fun flipViewLeftToRight(view: View,contentView:View) {
    view.apply {
        // Start the animation with a slight scale down
        scaleX = 0.6f
        scaleY = 0.6f

        animate()
            .rotationY(90f)  // Rotate halfway (to 90 degrees) for the left-to-right flip
            .setDuration(100)
            .withEndAction {
                // After reaching 90 degrees, rotate back to 0 degrees
                rotationY = -90f
                animate()
                    .rotationY(0f)
                    .scaleX(1f)  // Scale back to normal
                    .scaleY(1f)  // Scale back to normal
                    .setDuration(100)
                    .start()
                contentView.visibility=View.GONE
            }
            .start()
    }
}

fun flipViewRightToLeft(view: View,contentView:View) {
    view.apply {
        // Start the animation with a slight scale down
        scaleX = 0.6f
        scaleY = 0.6f

        animate()
            .rotationY(-90f)  // Rotate halfway (to -90 degrees) for the right-to-left flip
            .setDuration(100)
            .withEndAction {
                // After reaching -90 degrees, rotate back to 0 degrees
                rotationY = 90f
                animate()
                    .rotationY(0f)
                    .scaleX(1f)  // Scale back to normal
                    .scaleY(1f)  // Scale back to normal
                    .setDuration(100)
                    .start()
                contentView.visibility=View.GONE
            }
            .start()
    }
}


fun setAppLocale(context: Context, languageCode: String) {
    val locale = Locale(languageCode)
    Locale.setDefault(locale)

    val config = Configuration()
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        config.setLocale(locale)
    } else {
        config.locale = locale
    }
    context.resources.updateConfiguration(config, context.resources.displayMetrics)
}


fun generateUUID(): String {
    return UUID.randomUUID().toString()
}

fun String.encode(): String {
    return Base64.encodeToString(this.toByteArray(charset("UTF-8")), Base64.NO_WRAP)
}

fun String.decode(): String {
    return Base64.decode(this, Base64.NO_WRAP).toString(charset("UTF-8"))
}






class MyCustomProgressDialog(context: Context) : AppCompatDialog(context) {
    private val context: Context
    private var gifDrawable: GifDrawable? = null


    init {
        window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        this.context = context
        try {
//            gifDrawable = GifDrawable(context.resources, R.drawable.loading)
//            loadingGif.setImageDrawable(gifDrawable)
        } catch (e: IOException) {
            e.printStackTrace()
            // Handle the error appropriately
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

    }

    override fun show() {
        super.show()
    }

    override fun dismiss() {
        super.dismiss()
    }
}
