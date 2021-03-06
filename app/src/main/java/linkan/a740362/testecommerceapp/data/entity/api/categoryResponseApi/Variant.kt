package linkan.a740362.testecommerceapp.data.entity.api.categoryResponseApi

import java.io.Serializable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


data class Variant(

    @SerializedName("id")
    @Expose
    val id: Int,

    @SerializedName("color")
    @Expose
    val color: String,

    @SerializedName("size")
    @Expose
    val size: Int?,

    @SerializedName("price")
    @Expose
    val price: Int

) : Comparable<Variant>, Serializable {


    /**
     * Sorting product variant in ascending order by price
     */
    override fun compareTo(other: Variant): Int {

        return when {
            this.price > other.price -> 1
            this.price == other.price -> 0
            else -> -1
        }
    }


}

