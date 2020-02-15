package linkan.a740362.testecommerceapp.data.network

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import linkan.a740362.testecommerceapp.data.network.base.BaseRepository
import linkan.a740362.testecommerceapp.data.network.base.Result
import linkan.a740362.testecommerceapp.data.persistence.pref.PrefHelper
import linkan.a740362.testecommerceapp.di.annotation.CoroutineScopeIO
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
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

            val geocodeResponse = makeApiCall(
                call = { coroutineApiService.fetchAvailableProductDetail().await() }
            )

            productLiveData.postValue(geocodeResponse)

        }

    }

    override fun getProductLiveData(): LiveData<Result<String>> {
        return productLiveData
    }


}