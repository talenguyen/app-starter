package vn.tale.sayit.base

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.LifecycleRegistry
import android.support.v7.app.AppCompatActivity
import vn.tale.sayit.di.ActivityModule
import vn.tiki.daggers.ActivityInjector

abstract class BaseActivity : AppCompatActivity(), ActivityInjector, LifecycleOwner {

  private val registry by lazy { LifecycleRegistry(this) }

  override fun getLifecycle(): Lifecycle {
    return registry
  }

  override fun activityModule() = ActivityModule(this)
}
