package com.pradheep.roomdb.utils.localstorage

import android.content.Context
import android.content.SharedPreferences


class AppUtils(context: Context) {

    private lateinit var prefs: SharedPreferences
    init {
        getPrefs(context)
    }

    private fun getPrefs(context: Context): SharedPreferences {
        prefs =
            context.getSharedPreferences("Helloalfred",
                Context.MODE_PRIVATE
            )
        return context.getSharedPreferences("Helloalfred",
            Context.MODE_PRIVATE
        )
    }

    /**
     * Store integer value
     * */
    fun putInt(key: String, value: Int) {
        prefs.edit().putInt(key, value).apply()
    }

    /**
     * Retrieve integer value
     * */
    fun getInt(key: String): Int {
        return prefs.getInt(key, 0)
    }


    /**
     * Store string value
     * */
    fun putString(key: String, value: String?) {
        prefs.edit().putString(key, value).apply()
    }

    /**
     * Retrieve string value
     * */
    fun getString(key: String): String? {
        return prefs.getString(key, "")
    }

    /**
     * Store boolean value
     * */
    fun putBoolean(key: String, value: Boolean) {
        prefs.edit().putBoolean(key, value).apply()
    }

    /**
     * Retrieve boolean value
     * */
    fun getBoolean(key: String): Boolean {
        return prefs.getBoolean(key, false)
    }

    /**
     * Clear current session
     * */
    fun logout() {
        prefs.edit().clear().apply()
    }

    fun contains(key: String): Boolean {
        return prefs.contains(key)
    }

    fun remove(key:String){
        prefs.edit().remove(key)
    }

}