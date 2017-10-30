package vn.tale.sayit.di

import android.app.Activity
import dagger.Module
import dagger.Provides

@Module
class ActivityModule(private val activity: Activity) {

  @LifecycleScope
  @Provides internal fun provideActivity(): Activity {
    return activity
  }
}
