package vn.tale.sayit.util

import android.os.Build.VERSION

fun Any.apiAtLeast(targetApi: Int): Boolean = VERSION.SDK_INT >= targetApi