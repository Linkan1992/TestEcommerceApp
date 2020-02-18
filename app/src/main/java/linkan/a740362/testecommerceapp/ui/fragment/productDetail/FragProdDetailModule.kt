package linkan.a740362.testecommerceapp.ui.fragment.productDetail

import dagger.Module
import dagger.Provides
import linkan.a740362.testecommerceapp.ui.adapter.variantAdapter.ProductVariantAdapter

@Module
class FragProdDetailModule {

    // provide Fragment Product Detail Dependency


    @Provides
    fun provideProdVariantAdapter(): ProductVariantAdapter {
        return ProductVariantAdapter(ArrayList())
    }

}