package vn.tale.sayit.util

import android.support.annotation.IdRes
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity
import android.view.View

fun FragmentActivity.setFragment(@IdRes id: Int, fragment: Fragment) {
  supportFragmentManager.beginTransaction()
      .replace(id, fragment)
      .commit()
}

fun FragmentActivity.snackBar(msg: String, duration: Int = Snackbar.LENGTH_INDEFINITE): Snackbar {
  return Snackbar.make(
      findViewById<View>(android.R.id.content),
      msg,
      duration)
}
