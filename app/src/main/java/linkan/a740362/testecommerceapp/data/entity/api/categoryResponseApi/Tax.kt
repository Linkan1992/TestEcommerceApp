package linkan.a740362.testecommerceapp.data.entity.api.categoryResponseApi

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable


data class Tax(

    @SerializedName("name")
    @Expose
    private val name: String,

    @SerializedName("value")
    @Expose
    private val value: Double

) : Serializable
