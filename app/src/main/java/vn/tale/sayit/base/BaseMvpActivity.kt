package vn.tale.sayit.base

import vn.tale.sayit.di.ActivityModule
import vn.tiki.architecture.mvp.Mvp
import vn.tiki.architecture.mvp.MvpActivity
import vn.tiki.daggers.ActivityInjector

abstract class BaseMvpActivity<V : Mvp.View, P : Mvp.Presenter<V>> : MvpActivity<V, P>(), ActivityInjector {

  override fun activityModule(): Any {
    return ActivityModule(this)
  }
}
