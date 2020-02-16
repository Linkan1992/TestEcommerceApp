package linkan.a740362.testecommerceapp.ui.fragment.navigationMain

import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class MainNavFragModuleProvider {

    @ContributesAndroidInjector(modules = [MainNavFragModule::class])
    abstract fun provideMainNavFragment() : MainNavigationFragment

}