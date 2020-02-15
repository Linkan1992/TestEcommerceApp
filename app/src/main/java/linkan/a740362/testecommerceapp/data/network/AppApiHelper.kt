package linkan.a740362.testecommerceapp.data.network

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.gson.JsonObject
import linkan.a740362.testecommerceapp.data.network.base.BaseRepository
import linkan.a740362.testecommerceapp.data.network.base.Result
import linkan.a740362.testecommerceapp.data.persistence.pref.PrefHelper
import linkan.a740362.testecommerceapp.di.annotation.CoroutineScopeIO
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import linkan.a740362.testecommerceapp.util.UtilFunction
import javax.inject.Inject


class AppApiHelper @Inject
constructor(
    private val coroutineApiService: CoroutineApiService,
    @CoroutineScopeIO private val ioCoroutineScope: CoroutineScope,
    private val prefHelper: PrefHelper
) : BaseRepository(), ApiHelper {


    private val productLiveData: MutableLiveData<Result<String>> by lazy { MutableLiveData<Result<String>>() }


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
                    productLiveData.postValue(
                        Result.Success(
                            UtilFunction.segregateProductCategory(
                                productResponse.data?.string()
                            )
                        )
                    )
                }

                is Result.Error -> {
                    productLiveData.postValue(productResponse)
                }
            }

        }

    }

    override fun getProductLiveData(): LiveData<Result<String>> {
        return productLiveData
    }


}