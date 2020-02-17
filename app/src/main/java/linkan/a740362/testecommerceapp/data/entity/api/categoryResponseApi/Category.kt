package linkan.a740362.testecommerceapp.data.entity.api.categoryResponseApi

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Category(

    @SerializedName("id")
    @Expose
    val id: Int,

    @SerializedName("name")
    @Expose
    val name: String,

    @SerializedName("products")
    @Expose
    val products: List<ProductResponse>?,

    @SerializedName("child_categories")
    @Expose
    val childCategories: List<Category>?

) : Serializable{

    var isExpanded : Boolean = false

    var isParent : Boolean = false

}