package linkan.a740362.testecommerceapp.ui.adapter.childNavigation

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import linkan.a740362.testecommerceapp.base.BaseRecyclerViewAdapter
import linkan.a740362.testecommerceapp.data.network.base.Result
import linkan.a740362.testecommerceapp.databinding.NavigationChildRowLayoutBinding

class ChildNavigationAdapter(private  val data: ArrayList<String>) :
    BaseRecyclerViewAdapter<String, ChildNavigationAdapter.ChildNavViewHolder>(data) {

    private val childCategoryLiveData: MutableLiveData<Result<String>>  by lazy { MutableLiveData<Result<String>>() }

    val mChildCategoryLiveData: MutableLiveData<Result<String>>
        get() = childCategoryLiveData

    override fun mOnCreateViewHolder(parent: ViewGroup, viewType: Int): ChildNavViewHolder {
        return ChildNavViewHolder(
            NavigationChildRowLayoutBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }


    inner class ChildNavViewHolder(private val mBinding: NavigationChildRowLayoutBinding) :
        BaseRecyclerViewAdapter<String, ChildNavViewHolder>.BaseViewHolder(mBinding.root) {

        override fun onBind(model: String?) {

            val viewModel1 = ChildNavAdapViewModel()

            mBinding.viewModel = viewModel1

            // Immediate Binding
            // When a variable or observable changes, the binding will be scheduled to change before
            // the next frame. There are times, however, when binding must be executed immediately.
            // To force execution, use the executePendingBindings() method.
            mBinding.executePendingBindings()

            mBinding.tvCategoryName.text = model

            itemView.setOnClickListener {
                /**
                 * posting the clicked Pojo model
                 */
                childCategoryLiveData.postValue(Result.Success(model ?: ""))

                Toast.makeText(itemView.context, "item clicked is $model", Toast.LENGTH_LONG).show()

            }
        }

        override fun viewDetachedFromWindow() {

        }

    }

}