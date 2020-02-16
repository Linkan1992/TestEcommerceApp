package linkan.a740362.testecommerceapp.ui.fragment.navigationMain

import dagger.Module
import dagger.Provides
import linkan.a740362.testecommerceapp.ui.adapter.mainNavigation.MainNavigationAdapter

@Module
class MainNavFragModule {

    // provide main fragment dependency

    @Provides
    fun provideMainNavAdapter(): MainNavigationAdapter {
        return MainNavigationAdapter(ArrayList())
    }

}


