package linkan.a740362.testecommerceapp.data.entity.api.categoryResponseApi

import java.io.Serializable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


data class ProductResponse(

    @SerializedName("id")
    @Expose
    val id: Int,

    @SerializedName("name")
    @Expose
    val name: String,

    @SerializedName("date_added")
    @Expose
    val dateAdded: String?,

    @SerializedName("variants")
    @Expose val variants: List<Variant>?,

    @SerializedName("tax")
    @Expose
    val tax: Tax?,

    @SerializedName("view_count")
    @Expose
    val viewCount: Int?,

    @SerializedName("order_count")
    @Expose
    val order_count: Int? = null,

    @SerializedName("shares")
    @Expose
    val shares: Int

) : Serializable


