package vn.tale.sayit.ui.home

import android.os.Build.VERSION
import android.os.Build.VERSION_CODES
import android.speech.tts.TextToSpeech
import android.speech.tts.TextToSpeech.OnInitListener
import vn.tale.sayit.BuildConfig
import vn.tale.sayit.di.ActivityScope
import vn.tale.sayit.rx.RxBasePresenter
import vn.tale.sayit.util.onUi
import vn.tale.sayit.util.subscribe
import vn.tiki.architecture.mvp.ActivityResult
import vn.tiki.architecture.mvp.ActivityResultDelegate
import java.util.Locale
import javax.inject.Inject


@ActivityScope
class HomePresenter @Inject constructor() : RxBasePresenter<HomeView>(), ActivityResultDelegate {

  @Suppress("PrivatePropertyName")
  private val RC_CHECK_TTS_DATA = 2403

  private var query: CharSequence? = null

  private lateinit var tts: TextToSpeech

  override fun onActivityResult(activityResult: ActivityResult) {
    if (activityResult.isRequestCode(RC_CHECK_TTS_DATA)) {
      if (activityResult.isResultCode(TextToSpeech.Engine.CHECK_VOICE_DATA_PASS)) {
        tts = viewOrThrow.makeTts(OnInitListener { status ->
          if (status == TextToSpeech.ERROR) {
            viewOrThrow.showTtsInitError()
          } else {
            tts.language = Locale.US
          }
        })
      } else {
        // missing data, install it }
        viewOrThrow.startTssDataInstall()
      }
    }
  }

  override fun attach(view: HomeView) {
    super.attach(view)

    view.onQueryInputs()
        .onUi()
        .subscribe(this) {
          query = it
          if (it.isEmpty()) {
            viewOrThrow.hideClearButton()
          } else {
            viewOrThrow.showClearButton()
          }
        }

    view.onClearClicks()
        .subscribe(this) {
          viewOrThrow.clearQueryInput()
        }

    view.onSayItClicks()
        .filter { query.isNullOrBlank().not() }
        .subscribe(this) {
          if (VERSION.SDK_INT >= VERSION_CODES.LOLLIPOP) {
            tts.speak(query, TextToSpeech.QUEUE_ADD, null, BuildConfig.APPLICATION_ID)
          } else {
            tts.speak(query.toString(), TextToSpeech.QUEUE_ADD, null)
          }
        }

    view.startTssDataCheckForResult(RC_CHECK_TTS_DATA)
  }

  override fun destroy() {
    super.destroy()
    tts.shutdown()
  }
}
