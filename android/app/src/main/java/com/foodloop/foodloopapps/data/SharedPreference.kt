package com.foodloop.foodloopapps.data

import android.content.Context
import android.content.SharedPreferences

class SharedPreference(val context: Context) {
    private val PREFS_NAME = "USERINFO"
    private val sharedPref: SharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    fun saveUser(username: String, email: String, name:String, isLogged: Boolean) {
        val editor: SharedPreferences.Editor = sharedPref.edit()
        editor.putString("USERNAME", username)
        editor.putString("EMAIL", email)
        editor.putString("NAME", name)
        editor.putBoolean("ISLOGGED", isLogged)
        editor.apply()
    }
    fun getStringPreference(KEY_NAME: String): String? {
        return sharedPref.getString(KEY_NAME, null)
    }

    fun getValueBoolean(KEY_NAME: String): Boolean {
        return sharedPref.getBoolean(KEY_NAME, false)
    }

    fun clearSharedPreference() {
        val editor: SharedPreferences.Editor = sharedPref.edit()
        editor.clear()
        editor.apply()
    }

    fun removeValue(KEY_NAME: String) {
        val editor: SharedPreferences.Editor = sharedPref.edit()
        editor.remove(KEY_NAME)
        editor.apply()
    }
}