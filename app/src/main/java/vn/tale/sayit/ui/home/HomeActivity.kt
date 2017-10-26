package vn.tale.sayit.ui.home

import android.content.Intent
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.speech.tts.TextToSpeech.Engine
import android.speech.tts.TextToSpeech.OnInitListener
import android.support.design.widget.Snackbar
import android.view.inputmethod.EditorInfo
import com.jakewharton.rxbinding2.view.RxView
import com.jakewharton.rxbinding2.widget.RxTextView
import io.reactivex.Observable
import kotlinx.android.synthetic.main.activity_home.etQuery
import kotlinx.android.synthetic.main.activity_home.ivClear
import kotlinx.android.synthetic.main.activity_home.toolbar
import vn.tale.sayit.R
import vn.tale.sayit.di.ActivityModule
import vn.tale.sayit.util.bindString
import vn.tale.sayit.util.gone
import vn.tale.sayit.util.restart
import vn.tale.sayit.util.visible
import vn.tiki.architecture.mvp.MvpActivity
import vn.tiki.daggers.ActivityInjector
import vn.tiki.daggers.Daggers
import javax.inject.Inject

class HomeActivity : MvpActivity<HomeView, HomePresenter>(), HomeView, ActivityInjector {

  private val msgInitError by bindString(R.string.home_msg_init_error)
  @Inject internal lateinit var presenter: HomePresenter

  override fun activityModule(): Any = ActivityModule(this)

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    Daggers.inject(this)

    setContentView(R.layout.activity_home)

    setSupportActionBar(toolbar)

    toolbar.setNavigationIcon(R.drawable.ic_arrow_back)
    toolbar.setNavigationOnClickListener { onBackPressed() }

    etQuery.setImeActionLabel("Say It", EditorInfo.IME_ACTION_GO)

    connect(presenter, this)

  }

  override fun showClearButton() {
    ivClear.visible()
  }

  override fun hideClearButton() {
    ivClear.gone()
  }

  override fun clearQueryInput() {
    etQuery.setText("")
  }

  override fun startTssDataCheckForResult(@Suppress("PARAMETER_NAME_CHANGED_ON_OVERRIDE") requestCode: Int) {
    startActivityForResult(Intent(TextToSpeech.Engine.ACTION_CHECK_TTS_DATA), requestCode)
  }

  override fun startTssDataInstall() {
    startActivity(Intent(Engine.ACTION_INSTALL_TTS_DATA))
  }

  override fun makeTts(onInitListener: OnInitListener): TextToSpeech {
    return TextToSpeech(this, onInitListener)
  }

  override fun showTtsInitError() {
    Snackbar.make(findViewById(android.R.id.content), msgInitError, Snackbar.LENGTH_INDEFINITE)
        .setAction(R.string.retry) { restart() }
        .show()
  }

  override fun onQueryInputs(): Observable<CharSequence> = RxTextView.textChanges(etQuery)

  override fun onClearClicks(): Observable<Any> = RxView.clicks(ivClear)

  override fun onSayItClicks(): Observable<Any> = RxTextView.editorActions(etQuery, { true })
      .filter { it == EditorInfo.IME_ACTION_SEARCH }
      .map { Any() }
}
