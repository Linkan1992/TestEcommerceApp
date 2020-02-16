package linkan.a740362.testecommerceapp.util

import android.net.Uri
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.facebook.drawee.view.SimpleDraweeView
import linkan.a740362.testecommerceapp.ui.adapter.mainNavigation.MainNavigationAdapter

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
fun bindMainNavigationAdapter(recyclerView: RecyclerView, dataList: List<String>) {

    val adapter = recyclerView.adapter as MainNavigationAdapter?

    adapter?.let {
        it.clearItems()
        it.addItems(dataList)
    }

}