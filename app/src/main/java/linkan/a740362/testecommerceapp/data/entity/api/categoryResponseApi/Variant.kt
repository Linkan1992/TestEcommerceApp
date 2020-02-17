package linkan.a740362.testecommerceapp.data.entity.api.categoryResponseApi

import java.io.Serializable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


data class Variant(

    @SerializedName("id")
    @Expose
    private val id: Int,

    @SerializedName("color")
    @Expose
    private val color: String,

    @SerializedName("size")
    @Expose
    private val size: Int?,

    @SerializedName("price")
    @Expose
    private val price: Int

) : Serializable

