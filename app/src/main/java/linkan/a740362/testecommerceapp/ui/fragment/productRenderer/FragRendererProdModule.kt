package linkan.a740362.testecommerceapp.ui.fragment.productRenderer

import dagger.Module
import dagger.Provides
import linkan.a740362.testecommerceapp.ui.adapter.productRenderer.ProductRendererAdapter

@Module
class FragRendererProdModule {

    // provide dependency of Product Render Fragment

    @Provides
    fun provideProdRendererAdapter(): ProductRendererAdapter {
        return ProductRendererAdapter(ArrayList())
    }

}