package linkan.a740362.testecommerceapp.di.builderClass

import linkan.a740362.testecommerceapp.ui.activity.main.MainActivity
import linkan.a740362.testecommerceapp.ui.activity.main.MainActivityModule
import linkan.a740362.testecommerceapp.ui.activity.splash.SplashActivity
import linkan.a740362.testecommerceapp.ui.activity.splash.SplashActivityModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBuilder {

    @ContributesAndroidInjector(modules = [SplashActivityModule::class])
    abstract fun provideSplashActivity(): SplashActivity

    @ContributesAndroidInjector(modules = [MainActivityModule::class])
    abstract fun provideMainActivity(): MainActivity

}
