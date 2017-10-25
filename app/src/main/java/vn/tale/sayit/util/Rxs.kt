package vn.tale.sayit.util

import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import vn.tale.sayit.rx.RxBasePresenter

fun <T> Observable<T>.subscribe(presenter: RxBasePresenter<*>, consumer: (T) -> Unit) {
  presenter.disposeOnDestroy(subscribe(consumer))
}

fun <T> Observable<T>.onUi(): Observable<T> = observeOn(AndroidSchedulers.mainThread())