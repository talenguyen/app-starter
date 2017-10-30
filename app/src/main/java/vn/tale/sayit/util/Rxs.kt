package vn.tale.sayit.util

import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import vn.tale.sayit.rx.RxBasePresenter

fun <T> Observable<T>.subscribe(presenter: RxBasePresenter<*>, consumer: (T) -> Unit) {
  presenter.disposeOnDestroy(subscribe(consumer))
}

fun <T> Observable<T>.subscribe(disposable: CompositeDisposable, consumer: (T) -> Unit) {
  disposable.add(subscribe(consumer))
}

fun <T> Observable<T>.onUi(): Observable<T> = observeOn(AndroidSchedulers.mainThread())