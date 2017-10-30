package vn.tale.sayit.ui.home

import android.app.Activity
import android.arch.lifecycle.Lifecycle.Event.ON_PAUSE
import android.arch.lifecycle.Lifecycle.Event.ON_RESUME
import android.arch.lifecycle.LifecycleObserver
import android.arch.lifecycle.OnLifecycleEvent
import android.view.inputmethod.EditorInfo
import com.jakewharton.rxbinding2.view.RxView
import com.jakewharton.rxbinding2.widget.RxTextView
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.activity_home.etQuery
import kotlinx.android.synthetic.main.activity_home.ivClear
import vn.tale.sayit.util.gone
import vn.tale.sayit.util.onUi
import vn.tale.sayit.util.subscribe
import vn.tale.sayit.util.visible
import javax.inject.Inject

class InputController @Inject constructor(private val activity: Activity) : LifecycleObserver {
  private val disposable = CompositeDisposable()
  var onSayItClicks: (CharSequence) -> Unit = {}

  @OnLifecycleEvent(ON_RESUME)
  fun startBinding(): Unit = with(activity) {
    RxTextView.textChanges(etQuery)
        .skipInitialValue()
        .onUi()
        .subscribe(disposable) {
          if (it.isEmpty()) {
            ivClear.gone()
          } else {
            ivClear.visible()
          }
        }

    onClearClicks()
        .subscribe(disposable) { etQuery.setText("") }

    onSayItClicks()
        .subscribe(disposable) { onSayItClicks(etQuery.text) }
  }

  @OnLifecycleEvent(ON_PAUSE)
  fun stopBinding() {
    disposable.clear()
  }

  private fun onClearClicks(): Observable<Any> = RxView.clicks(activity.ivClear)

  private fun onSayItClicks(): Observable<Any> = RxTextView.editorActions(activity.etQuery, { true }).
      filter { it == EditorInfo.IME_ACTION_SEARCH }.
      map { Any() }
}