package linkan.a740362.testecommerceapp.ui.fragment.productRenderer

import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragRendererModuleProvider {

    @ContributesAndroidInjector(modules = [FragRendererProdModule::class])
    abstract fun provideFragRenderer(): FragProductRenderer
}