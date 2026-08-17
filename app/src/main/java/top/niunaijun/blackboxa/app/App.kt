package top.niunaijun.blackboxa.app

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.util.Log

class App : Application() {

    companion object {

        @SuppressLint("StaticFieldLeak")
        @Volatile
        private lateinit var mContext: Context

        @JvmStatic
        fun getContext(): Context {
            return mContext
        }
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        mContext = base!!
        val defaultHandler = Thread.getDefaultUncaughtExceptionHandler()
        Thread.setDefaultUncaughtExceptionHandler { thread, throwable ->
            Log.e("RooTBox_Crash", "Uncaught exception in thread ${thread.name}: ${throwable.message}", throwable)
            defaultHandler?.uncaughtException(thread, throwable)
        }
        AppManager.doAttachBaseContext(base)
    }

    override fun onCreate() {
        super.onCreate()
        AppManager.doOnCreate(mContext)
    }
}