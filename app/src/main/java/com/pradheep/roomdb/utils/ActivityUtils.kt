package com.pradheep.roomdb.utils

import android.app.Activity
import android.content.Context
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDialog
import androidx.core.content.ContextCompat
import com.pradheep.roomdb.utils.common_methods.dismissProgressDialog
import com.pradheep.roomdb.utils.common_methods.getProgressDialog

abstract class ActivityBase : AppCompatActivity() {
    private var mProgressDialog: AppCompatDialog? = null

    // Function to close the keyboard
    fun closeKeyboard() {
        val inputMethodManager =
            getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(currentFocus?.windowToken, 0)
    }

    fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    fun showProgress() {
        if (mProgressDialog == null){
            mProgressDialog = getProgressDialog(this)
        }
        else{
            mProgressDialog!!.dismiss()
            mProgressDialog = getProgressDialog(this)
        }
        Log.e("isOpen",mProgressDialog.toString())
    }

    fun dismissProgress() {
        if (mProgressDialog != null) {
            dismissProgressDialog(mProgressDialog!!)
        }
    }

    fun Activity.hideSoftKeyboard() {
        currentFocus?.let {
            val inputMethodManager = ContextCompat.getSystemService(this, InputMethodManager::class.java)!!
            inputMethodManager.hideSoftInputFromWindow(it.windowToken, 0)
        }
    }

    // Set up touch listener for non-text box views to hide keyboard.
    open fun setupUI(view: View) {
        // Set up touch listener for non-text box views to hide keyboard.
        if (view !is EditText) {
            view.setOnTouchListener { v, event ->
                hideSoftKeyboard()
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



}