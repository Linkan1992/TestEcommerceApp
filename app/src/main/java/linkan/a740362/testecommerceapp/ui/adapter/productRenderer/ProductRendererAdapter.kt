package linkan.a740362.testecommerceapp.ui.adapter.productRenderer

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.ViewGroup.LayoutParams
import linkan.a740362.testecommerceapp.R
import linkan.a740362.testecommerceapp.base.BaseRecyclerViewAdapter
import linkan.a740362.testecommerceapp.data.entity.api.categoryResponseApi.ProductResponse
import linkan.a740362.testecommerceapp.data.network.base.Result
import linkan.a740362.testecommerceapp.databinding.RenderProductRowLayoutBinding

class ProductRendererAdapter(data: MutableList<ProductResponse>) :
    BaseRecyclerViewAdapter<ProductResponse, ProductRendererAdapter.ProdRendererViewHolder>(data) {

    private var isLinearLayoutManager = false

    private val productDetailLiveData: MutableLiveData<Result<ProductResponse>>  by lazy { MutableLiveData<Result<ProductResponse>>() }

    val mProductDetailLiveData: LiveData<Result<ProductResponse>>
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

            if (isLinearLayoutManager) {
                val layoutParam = mBinding.clRootRenderRow.layoutParams
                layoutParam.width = itemView.context.resources.getDimension(R.dimen.dimes_200dp).toInt()
                mBinding.clRootRenderRow.layoutParams = layoutParam
            }/* else {
                // future reference code
                val layoutParam = mBinding.clRootRenderRow.layoutParams
                layoutParam.width = LayoutParams.MATCH_PARENT
                mBinding.clRootRenderRow.layoutParams = layoutParam
            }*/

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


    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)

        /**
         * Note GridLayoutManager is Child of LinearLayoutManager
         */
        isLinearLayoutManager = when (recyclerView.layoutManager) {
            is GridLayoutManager -> false
            is LinearLayoutManager -> true
            else -> false
        }
    }

}