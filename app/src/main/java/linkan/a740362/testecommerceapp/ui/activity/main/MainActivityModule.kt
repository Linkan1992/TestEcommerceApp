package linkan.a740362.testecommerceapp.ui.activity.main

import androidx.recyclerview.widget.LinearLayoutManager
import dagger.Module
import dagger.Provides

@Module
class MainActivityModule {

    // provide main activity level dependency

    @Provides
    fun provideLayoutManager(activity: MainActivity): LinearLayoutManager {
        return LinearLayoutManager(activity)
    }


}