package linkan.a740362.testecommerceapp.data.entity.api.rankProductResponse

import java.io.Serializable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


data class ProductRanking(

    @SerializedName("rankings")
    @Expose
    val rankings: List<ProductRankCategory>?

) : Serializable

