package vn.tale.sayit.base

import android.app.Activity
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.support.design.widget.Snackbar
import android.view.View
import vn.tale.sayit.R
import vn.tale.sayit.di.ActivityModule
import vn.tale.sayit.util.isConnected

internal class ActivityDelegate {

  private var networkStatusObserver: NetworkStatusObserver? = null
  private var rootView: View? = null
  private var sbOfflineNotification: Snackbar? = null
  private val networkStatusReceiver = object : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
      val connected = context.isConnected()
      setNetworkStatus(connected)
    }
  }

  fun makeActivityModule(activity: Activity): Any {
    return ActivityModule(activity)
  }

  fun onPause(activity: Activity) {
    if (activity is NetworkStatusObserver) {
      activity.unregisterReceiver(networkStatusReceiver)
      networkStatusObserver = null
    }
  }

  fun onResume(activity: Activity) {
    if (activity is NetworkStatusObserver) {
      networkStatusObserver = activity
      if (rootView == null) {
        rootView = activity.findViewById(android.R.id.content)
      }
      activity.registerReceiver(
          networkStatusReceiver,
          IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION))
    }
  }

  private fun setNetworkStatus(connected: Boolean) {
    networkStatusObserver?.onNetworkStatusChanged(connected)
    if (connected) {
      sbOfflineNotification?.dismiss()
    } else {
      sbOfflineNotification = sbOfflineNotification ?: Snackbar.make(
          rootView!!,
          R.string.msg_you_are_offline,
          Snackbar.LENGTH_INDEFINITE)

      sbOfflineNotification!!.show()
    }
  }
}
