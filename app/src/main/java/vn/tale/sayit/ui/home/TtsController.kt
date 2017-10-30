package vn.tale.sayit.ui.home

import android.app.Activity
import android.arch.lifecycle.Lifecycle.Event.ON_CREATE
import android.arch.lifecycle.Lifecycle.Event.ON_DESTROY
import android.arch.lifecycle.LifecycleObserver
import android.arch.lifecycle.OnLifecycleEvent
import android.content.Intent
import android.os.Build.VERSION
import android.os.Build.VERSION_CODES
import android.speech.tts.TextToSpeech
import android.speech.tts.TextToSpeech.Engine
import vn.tale.sayit.BuildConfig
import vn.tale.sayit.R
import vn.tale.sayit.util.bindString
import vn.tale.sayit.util.restart
import vn.tale.sayit.util.snackBar
import java.util.Locale
import javax.inject.Inject

class TtsController @Inject constructor(private val activity: Activity) : LifecycleObserver {

  private val rcCheckTtsData = 2403
  private val msgInitError by activity.bindString(R.string.home_msg_init_error)
  private var tts: TextToSpeech? = null

  @OnLifecycleEvent(ON_CREATE)
  fun onCreate() {
    activity.startActivityForResult(Intent(Engine.ACTION_CHECK_TTS_DATA), rcCheckTtsData)
  }

  @OnLifecycleEvent(ON_DESTROY)
  fun onDestroy() {
    tts?.shutdown()
  }

  fun onActivityResult(requestCode: Int, resultCode: Int) {
    if (requestCode == rcCheckTtsData) {
      if (resultCode == TextToSpeech.Engine.CHECK_VOICE_DATA_PASS) {
        tts = TextToSpeech(activity, TextToSpeech.OnInitListener { status ->
          if (status == TextToSpeech.ERROR) {
            (activity as HomeActivity).snackBar(msgInitError)
                .setAction(R.string.retry) { activity.restart() }
                .show()
          } else {
            tts?.language = Locale.US
          }
        })
      } else {
        // missing data, install it
        activity.startActivity(Intent(Engine.ACTION_INSTALL_TTS_DATA))
      }
    }
  }

  fun speech(text: CharSequence) {
    if (text.isNotEmpty()) {
      if (VERSION.SDK_INT >= VERSION_CODES.LOLLIPOP) {
        tts?.speak(text, TextToSpeech.QUEUE_ADD, null, BuildConfig.APPLICATION_ID)
      } else {
        @Suppress("DEPRECATION")
        tts?.speak(text.toString(), TextToSpeech.QUEUE_ADD, null)
      }
    }

  }
}
