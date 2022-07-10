package com.nawed.animefacts.utils

import android.content.Context
import android.net.ConnectivityManager
import android.widget.Toast
import com.nawed.animefacts.R
import com.nawed.animefacts.base.MyApplication

class AppUtils {

  companion object {

       fun isNetWorkAvailable(): Boolean {
          val connMgr = MyApplication.appContext!!
              .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

          val networkInfo = connMgr.activeNetworkInfo

          return if (networkInfo != null && networkInfo.isConnected) {
              true
          } else {
              Toast.makeText(
                  MyApplication.appContext,
                  MyApplication.appContext!!.getString(R.string.hint_networkError),
                  Toast.LENGTH_LONG
              ).show()
              false
          }
      }
  }
}