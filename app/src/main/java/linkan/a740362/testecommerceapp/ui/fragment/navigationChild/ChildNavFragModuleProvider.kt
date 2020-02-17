package linkan.a740362.testecommerceapp.ui.fragment.navigationChild

import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ChildNavFragModuleProvider {

    @ContributesAndroidInjector(modules = [ChildNavFragModule::class])
    abstract fun provideChildNavFragment() : ChildNavigationFragment

}