package linkan.a740362.testecommerceapp.ui.fragment.navigationChild

import dagger.Module
import dagger.Provides
import linkan.a740362.testecommerceapp.ui.adapter.childNavigation.ChildNavigationAdapter

@Module
class ChildNavFragModule {

    // provide child fragment dependency

    @Provides
    fun provideChildNavAdapter(): ChildNavigationAdapter {
        return ChildNavigationAdapter(ArrayList())
    }

}