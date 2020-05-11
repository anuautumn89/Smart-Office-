package com.dpitl.deuron.meetingroomt3g.my_classes.text_to_speech.user_sessions

import android.content.Context
import android.content.SharedPreferences
import com.deuron.teacoffeebackend.R

class UserSessionManager(internal var context: Context) {
    internal var sharedPref: SharedPreferences? = null
    internal var editor: SharedPreferences.Editor? = null

    var isUserLogin: Boolean
        get() = sharedPref!!.getBoolean("IS_USER_LOG_IN", false)
        set(`val`) {
            editor!!.putBoolean("IS_USER_LOG_IN", `val`)
            editor!!.commit()
        }

    var isOpeningActivity: Boolean
        get() = sharedPref!!.getBoolean("IS_OPENING_ACTIVITY", false)
        set(`val`) {
            editor!!.putBoolean("IS_OPENING_ACTIVITY", `val`)
            editor!!.commit()
        }
    var isKeepLoggin: Boolean
        get() = sharedPref!!.getBoolean("IS_KEEP_LOGIN",false)
        set(`val`){
            editor!!.putBoolean("IS_KEEP_LOGIN", `val`)
            editor!!.commit()
        }

    var uId: Int
        get() = sharedPref!!.getInt("U_ID",0)
        set(`val`){
            editor!!.putInt("U_ID", `val`)
            editor!!.commit()
        }

    init {
        sharedPref = this.context.getSharedPreferences(this.context.getString(R.string.app_name), Context.MODE_PRIVATE)
        editor = sharedPref!!.edit()
    }


}
