package linkan.a740362.testecommerceapp.data.entity.api.categoryResponseApi

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable


data class SizeVariant(

    @SerializedName("size")
    @Expose
    val size: Int? = null,

    @SerializedName("color_variant")
    @Expose
    val colorVariant: List<ColorVariant>?

) : Comparable<SizeVariant>, Serializable {


    /**
     * Sorting product variant in ascending order by price
     */
    override fun compareTo(other: SizeVariant): Int {

        return when {
            this.size!! > other.size!! -> 1
            this.size == other.size -> 0
            else -> -1
        }
    }


}


