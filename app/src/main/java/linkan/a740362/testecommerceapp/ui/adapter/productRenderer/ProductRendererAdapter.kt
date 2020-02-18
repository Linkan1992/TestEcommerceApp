package linkan.a740362.testecommerceapp.ui.adapter.productRenderer

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.MutableLiveData
import linkan.a740362.testecommerceapp.base.BaseRecyclerViewAdapter
import linkan.a740362.testecommerceapp.data.entity.api.categoryResponseApi.ProductResponse
import linkan.a740362.testecommerceapp.data.network.base.Result
import linkan.a740362.testecommerceapp.databinding.RenderProductRowLayoutBinding

class ProductRendererAdapter(data: MutableList<ProductResponse>) :
    BaseRecyclerViewAdapter<ProductResponse, ProductRendererAdapter.ProdRendererViewHolder>(data) {

    private val productDetailLiveData: MutableLiveData<Result<ProductResponse>>  by lazy { MutableLiveData<Result<ProductResponse>>() }

    val mProductDetailLiveData: MutableLiveData<Result<ProductResponse>>
        get() = productDetailLiveData

    override fun mOnCreateViewHolder(parent: ViewGroup, viewType: Int): ProdRendererViewHolder {
        return ProdRendererViewHolder(
            RenderProductRowLayoutBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }


    inner class ProdRendererViewHolder(private val mBinding: RenderProductRowLayoutBinding) :
        BaseRecyclerViewAdapter<ProductResponse, ProdRendererViewHolder>.BaseViewHolder(mBinding.root) {

        override fun onBind(model: ProductResponse?) {

            val viewModel = ProdRendererAdapViewModel(model)

            mBinding.viewModel = viewModel

            // Immediate Binding
            // When a variable or observable changes, the binding will be scheduled to change before
            // the next frame. There are times, however, when binding must be executed immediately.
            // To force execution, use the executePendingBindings() method.
            mBinding.executePendingBindings()


        //    mBinding.tvProductName.text = model?.name ?: itemView.context.resources.getString(R.string.empty)

            itemView.setOnClickListener {
                /**
                 * posting the clicked Pojo model
                 */

                model?.let { productDetailLiveData.postValue(Result.Success(it)) }


                // Toast.makeText(itemView.context, "item clicked is $model", Toast.LENGTH_LONG).show()

            }
        }

        override fun viewDetachedFromWindow() {

        }

    }

}