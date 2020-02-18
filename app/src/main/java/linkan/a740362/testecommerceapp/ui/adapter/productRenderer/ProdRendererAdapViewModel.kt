package linkan.a740362.testecommerceapp.ui.adapter.productRenderer

import androidx.databinding.ObservableField
import linkan.a740362.testecommerceapp.data.entity.api.categoryResponseApi.ProductResponse
import linkan.a740362.testecommerceapp.data.entity.api.categoryResponseApi.Variant
import linkan.a740362.testecommerceapp.util.AppConstants
import java.util.*
import kotlin.collections.ArrayList

class ProdRendererAdapViewModel(model: ProductResponse?) {

    // implement non UI code here to isolate

    val prodPrice: ObservableField<String>

    val prodName: ObservableField<String>

    val prodImageUrl: ObservableField<String>

    init {

        // sorting product based on color variant price
        // Collections.sort(model?.variants)

        prodPrice =
            ObservableField("${AppConstants.RUPEE_SYMBOL} ${model?.variants?.get(0)?.colorVariant?.get(0)?.price.toString()}")

        prodName = ObservableField(model?.name ?: "")

        prodImageUrl = ObservableField("")  // add your model image url
    }


    fun submit() {


    }

}