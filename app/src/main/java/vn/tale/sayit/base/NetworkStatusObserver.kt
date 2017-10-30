package vn.tale.sayit.base

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LifecycleObserver
import android.arch.lifecycle.OnLifecycleEvent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.view.View
import vn.tale.sayit.R
import vn.tale.sayit.util.isConnected
import vn.tale.sayit.util.snackBar

class NetworkStatusObserver(private val activity: AppCompatActivity, func: (Boolean) -> Unit) : LifecycleObserver {

  private val offlineSnackBar by lazy {
    val msg = activity.getString(R.string.msg_you_are_offline)
    activity.snackBar(msg)
  }

  private val networkStatusReceiver = object : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
      val connected = context.isConnected()
      func(connected)
      if (!connected) {
        offlineSnackBar.show()
      } else if (offlineSnackBar.isShownOrQueued) {
        offlineSnackBar.dismiss()
      }
    }
  }

  @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
  fun startObserve() {
    activity.registerReceiver(
        networkStatusReceiver,
        IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION))
  }

  @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
  fun stopObserve() {
    activity.unregisterReceiver(networkStatusReceiver)
  }
}