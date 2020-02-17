package linkan.a740362.testecommerceapp.data.network

import androidx.lifecycle.LiveData
import linkan.a740362.testecommerceapp.data.entity.api.categoryResponseApi.ProductDetailResponse
import linkan.a740362.testecommerceapp.data.network.base.Result

interface ApiHelper {

    fun fetchProductData()

    fun getProductLiveData(): LiveData<Result<ProductDetailResponse>>

}
