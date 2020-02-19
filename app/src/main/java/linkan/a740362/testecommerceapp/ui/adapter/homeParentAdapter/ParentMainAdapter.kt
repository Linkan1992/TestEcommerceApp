package linkan.a740362.testecommerceapp.ui.adapter.homeParentAdapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import linkan.a740362.testecommerceapp.base.BaseRecyclerViewAdapter
import linkan.a740362.testecommerceapp.data.entity.api.categoryResponseApi.ProductResponse
import linkan.a740362.testecommerceapp.data.entity.api.rankProductResponse.ProductRankCategory
import linkan.a740362.testecommerceapp.data.network.base.Result
import linkan.a740362.testecommerceapp.databinding.MainProductTypeRowBinding
import linkan.a740362.testecommerceapp.ui.adapter.productRenderer.ProductRendererAdapter

class ParentMainAdapter(data: MutableList<ProductRankCategory>) :
    BaseRecyclerViewAdapter<ProductRankCategory, ParentMainAdapter.ParentMainViewHolder>(data) {

    private val viewPool = RecyclerView.RecycledViewPool()

    private val rankingLiveData: MutableLiveData<Result<ProductRankCategory>>  by lazy { MutableLiveData<Result<ProductRankCategory>>() }

    val mRankingLiveData: LiveData<Result<ProductRankCategory>>
        get() = rankingLiveData

    private val rankingProductLiveData: MediatorLiveData<Result<ProductResponse>> = MediatorLiveData()

    val mRankingProductLiveData: LiveData<Result<ProductResponse>>
        get() = rankingProductLiveData

    override fun mOnCreateViewHolder(parent: ViewGroup, viewType: Int): ParentMainViewHolder {
        return ParentMainViewHolder(
            MainProductTypeRowBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }


    inner class ParentMainViewHolder(private val mBinding: MainProductTypeRowBinding) :
        BaseRecyclerViewAdapter<ProductRankCategory, ParentMainViewHolder>.BaseViewHolder(mBinding.root) {

        override fun onBind(model: ProductRankCategory?) {

            val viewModel = ParentAdapViewModel()

            mBinding.viewModel = viewModel

            // Immediate Binding
            // When a variable or observable changes, the binding will be scheduled to change before
            // the next frame. There are times, however, when binding must be executed immediately.
            // To force execution, use the executePendingBindings() method.
            mBinding.executePendingBindings()


            // hide button for all row for testing purpose
            if (adapterPosition > -1) {
                mBinding.imgActionIcon.visibility = View.GONE
            }


            mBinding.tvCategoryName.text = model?.ranking


            // Create Nested RecyclerView
            val mLayoutManager = LinearLayoutManager(itemView.context)
            mLayoutManager.orientation = LinearLayoutManager.HORIZONTAL
            mBinding.parentTyepRecyclerView.layoutManager = mLayoutManager
            mBinding.parentTyepRecyclerView.itemAnimator = DefaultItemAnimator()
            mBinding.parentTyepRecyclerView.adapter = ProductRendererAdapter(ArrayList<ProductResponse>().apply {
                addAll(model?.products!!)
            }).apply {
                /**
                 * Single source LiveData is binded to nested multiple child live data
                 * hence need to use Mediator Live Data since data will be observed
                 * if any one changes means clicked
                 */
                rankingProductLiveData.addSource(this.mProductDetailLiveData) {
                    rankingProductLiveData.postValue(it)
                }
            }

            mBinding.parentTyepRecyclerView.setRecycledViewPool(viewPool)


            mBinding.imgActionIcon.setOnClickListener {
                /**
                 * posting the clicked Pojo model
                 */

                model?.let { rankingLiveData.postValue(Result.ShowMore(it)) }


                // Toast.makeText(itemView.context, "item clicked is $model", Toast.LENGTH_LONG).show()

            }
        }

        override fun viewDetachedFromWindow() {

        }

    }

}