package linkan.a740362.testecommerceapp.ui.fragment.productDetail

import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragProdDetailModuleProvider {

    @ContributesAndroidInjector(modules = [FragProdDetailModule::class])
    abstract fun provideFragProdDetail(): FragProductDetail

}