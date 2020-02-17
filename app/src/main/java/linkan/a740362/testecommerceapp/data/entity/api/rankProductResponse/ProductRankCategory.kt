package linkan.a740362.testecommerceapp.data.entity.api.rankProductResponse

import java.io.Serializable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import linkan.a740362.testecommerceapp.data.entity.api.categoryResponseApi.ProductResponse


data class ProductRankCategory(

    @SerializedName("ranking")
    @Expose
    val ranking: String? = null,

    @SerializedName("products")
    @Expose
    val products: List<ProductResponse>?

) : Serializable
