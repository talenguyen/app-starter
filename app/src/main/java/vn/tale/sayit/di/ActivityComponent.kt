package vn.tale.sayit.di

import dagger.Subcomponent
import vn.tale.sayit.ui.home.HomeActivity

@ActivityScope
@Subcomponent(modules = arrayOf(ActivityModule::class))
interface ActivityComponent {

  fun inject(ignored: HomeActivity)
}
