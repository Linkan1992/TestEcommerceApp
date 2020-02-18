package linkan.a740362.testecommerceapp.util

import android.net.Uri
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.facebook.drawee.view.SimpleDraweeView
import linkan.a740362.testecommerceapp.data.entity.api.categoryResponseApi.Category
import linkan.a740362.testecommerceapp.data.entity.api.categoryResponseApi.ProductResponse
import linkan.a740362.testecommerceapp.data.entity.api.categoryResponseApi.SizeVariant
import linkan.a740362.testecommerceapp.ui.adapter.variantAdapter.ProductVariantAdapter
import linkan.a740362.testecommerceapp.ui.adapter.childNavigation.ChildNavigationAdapter
import linkan.a740362.testecommerceapp.ui.adapter.mainNavigation.MainNavigationAdapter
import linkan.a740362.testecommerceapp.ui.adapter.productRenderer.ProductRendererAdapter

@BindingAdapter("bind:imageUrl")
fun setImageUrl(draweeView: SimpleDraweeView, imageUrl: String?) {

    val uri = Uri.parse(imageUrl ?: "")
    draweeView.setImageURI(uri)
}


@BindingAdapter("parentAdapter")
fun bindParentAdapter(recyclerView: RecyclerView, parentDataList: List<String>) {


}


@BindingAdapter("childAdapter")
fun bindChildAdapter(recyclerView: RecyclerView, childDataList: List<String>) {


}


@BindingAdapter("mainNavAdapter")
fun bindMainNavigationAdapter(recyclerView: RecyclerView, dataList: List<Category>) {

    val adapter = recyclerView.adapter as MainNavigationAdapter?

    adapter?.let {
        it.clearItems()
        it.addItems(dataList)
    }

}


@BindingAdapter("childNavAdapter")
fun bindChildNavigationAdapter(recyclerView: RecyclerView, dataList: List<Category>) {

    val adapter = recyclerView.adapter as ChildNavigationAdapter?

    adapter?.let {
        it.clearItems()
        it.addItems(dataList)
    }

}


@BindingAdapter("prodRendererAdapter")
fun bindProdRendererAdapter(recyclerView: RecyclerView, dataList: List<ProductResponse>) {

    val adapter = recyclerView.adapter as ProductRendererAdapter?

    adapter?.let {
        it.clearItems()
        it.addItems(dataList)
    }

}


@BindingAdapter("sizeVariantAdapter")
fun bindVariantAdapter(recyclerView: RecyclerView, dataList: List<SizeVariant>) {

    val adapter = recyclerView.adapter as ProductVariantAdapter?

    adapter?.let {
        it.clearItems()
        it.addItems(dataList)
    }

}