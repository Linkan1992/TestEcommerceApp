package linkan.a740362.testecommerceapp.data.entity.api.categoryResponseApi

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import linkan.a740362.testecommerceapp.data.entity.api.rankProductResponse.ProductRankCategory
import java.io.Serializable

data class ProductDetailResponse(

    @SerializedName("categories")
    @Expose
    val categories: List<Category>?,

    @SerializedName("rankings")
    @Expose
    val rankings: List<ProductRankCategory>?

) : Serializable
