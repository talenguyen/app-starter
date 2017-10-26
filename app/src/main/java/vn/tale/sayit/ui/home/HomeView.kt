package vn.tale.sayit.ui.home

import android.speech.tts.TextToSpeech
import android.speech.tts.TextToSpeech.OnInitListener
import io.reactivex.Observable
import vn.tiki.architecture.mvp.Mvp.View

interface HomeView : View {

  fun showClearButton()

  fun hideClearButton()

  fun clearQueryInput()

  fun showTtsInitError()

  fun startTssDataCheckForResult(rC_CHECK_TTS_DATA: Int)

  fun startTssDataInstall()

  fun makeTts(onInitListener: OnInitListener): TextToSpeech

  fun onQueryInputs(): Observable<CharSequence>

  fun onClearClicks(): Observable<Any>

  fun onSayItClicks(): Observable<Any>
}
