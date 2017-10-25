package vn.tale.sayit.di

import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(AppModule::class))
interface AppComponent {

  operator fun plus(ignored: ActivityModule): ActivityComponent
}
