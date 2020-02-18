package linkan.a740362.testecommerceapp.data.network

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.gson.GsonBuilder
import com.google.gson.JsonObject
import linkan.a740362.testecommerceapp.data.network.base.BaseRepository
import linkan.a740362.testecommerceapp.data.network.base.Result
import linkan.a740362.testecommerceapp.data.persistence.pref.PrefHelper
import linkan.a740362.testecommerceapp.di.annotation.CoroutineScopeIO
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import linkan.a740362.testecommerceapp.data.entity.api.categoryResponseApi.ProductDetailResponse
import linkan.a740362.testecommerceapp.util.UtilFunction
import javax.inject.Inject


class AppApiHelper @Inject
constructor(
    private val coroutineApiService: CoroutineApiService,
    @CoroutineScopeIO private val ioCoroutineScope: CoroutineScope,
    private val prefHelper: PrefHelper
) : BaseRepository(), ApiHelper {


    private val productLiveData: MutableLiveData<Result<ProductDetailResponse>> by lazy { MutableLiveData<Result<ProductDetailResponse>>() }


    override fun fetchProductData() {

        ioCoroutineScope.launch {

            productLiveData.postValue(Result.Loading())

            val productResponse = makeApiCall(
                call = { coroutineApiService.fetchAvailableProductDetail().await() }
            )

            when (productResponse) {
                is Result.Success -> {
                    //  UtilFunction.segregateProductCategory(productResponse.data?.string())
                    //  productLiveData.postValue(Result.Success(productResponse.data?.string()))

                    // json with product variant filter
                    val stringJsonResponse = UtilFunction.filterProductCategory(productResponse.data.string())

                    // json without product variant filter
                  //  val stringJsonResponse = UtilFunction.segregateProductCategory(productResponse.data.string())

                    val categoryResponse : ProductDetailResponse = GsonBuilder().create().fromJson(
                        stringJsonResponse,
                        ProductDetailResponse::class.java
                    )

                    productLiveData.postValue(
                        Result.Success(
                            categoryResponse
                        )
                    )
                }

                is Result.Error -> {
                    productLiveData.postValue(productResponse)
                }
            }

        }

    }

    override fun getProductLiveData(): LiveData<Result<ProductDetailResponse>> {
        return productLiveData
    }


}