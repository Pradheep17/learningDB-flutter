package com.pradheep.roomdb.utils.custom_methods

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle

class IntentUtils {

    companion object {
        inline fun <reified T : Any> redirectToNextActivity(context: Context, data: Bundle? = null,isFinished:Boolean?=null) {
            val introActivity = Intent(context, T::class.java)
            introActivity.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP)
            introActivity.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            if (data != null) {
                introActivity.putExtras(data)
            }
            context.startActivity(introActivity)
            if (context is Activity && isFinished !=false ) {
                context.finish()
            }
        }
    }
}

