package linkan.a740362.testecommerceapp.ui.activity.main

import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.Module
import dagger.Provides
import linkan.a740362.testecommerceapp.ui.adapter.homeParentAdapter.ParentMainAdapter
import linkan.a740362.testecommerceapp.util.AppConstants

@Module
class MainActivityModule {

    // provide main activity level dependency

    @Provides
    fun provideLayoutManager(activity: MainActivity): LinearLayoutManager {
        return LinearLayoutManager(activity)
    }

    @Provides
    fun provideGridLayoutManager(activity: MainActivity): GridLayoutManager {
        return GridLayoutManager(activity, AppConstants.RENDERER_GRID_ROW_COUNT)
    }

    @Provides
    fun provideParentMainAdapter(): ParentMainAdapter {
        return ParentMainAdapter(ArrayList())
    }

}