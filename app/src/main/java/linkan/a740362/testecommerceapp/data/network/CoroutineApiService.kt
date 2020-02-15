package linkan.a740362.testecommerceapp.data.network

import kotlinx.coroutines.Deferred
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface CoroutineApiService {

    // declare method for api call

    @GET("json")
    fun fetchAvailableProductDetail(): Deferred<Response<ResponseBody>>

}
