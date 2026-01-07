package com.pradheep.roomdb.utils.custom_methods

import android.graphics.Color
import android.os.Build
import android.view.View
import android.view.Window
import android.view.WindowManager
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

object FullScreenUtil {
    // show screen fully
    fun applyWindowInsetsListener(view: View,isVisible: Boolean?=false) {
//        ViewCompat.setOnApplyWindowInsetsListener(view) { v, insets ->
//            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
//            insets
//        }
        showTvAboveKeyboard(view,isVisible)
    }

    // while use chat bot showing text view above keyboard
    fun showTvAboveKeyboard(view:View,isVisible:Boolean? =false){
        ViewCompat.setOnApplyWindowInsetsListener(view) { view, insets ->
            val imeVisible = insets.isVisible(WindowInsetsCompat.Type.ime())
            val imeHeight = insets.getInsets(WindowInsetsCompat.Type.ime()).bottom

            if (imeVisible && (isVisible==false)) {
                // Add padding to the bottom of the view to make it visible above the keyboard
                view.setPadding(0, 0, 0, imeHeight)
            } else {
                // Remove padding when the keyboard is hidden
                val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
                view.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            }

            insets
        }
    }
}