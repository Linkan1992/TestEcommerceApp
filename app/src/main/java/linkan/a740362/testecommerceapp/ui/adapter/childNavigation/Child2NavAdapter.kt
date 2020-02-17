package linkan.a740362.testecommerceapp.ui.adapter.childNavigation

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import linkan.a740362.testecommerceapp.R
import linkan.a740362.testecommerceapp.base.BaseRecyclerViewAdapter
import linkan.a740362.testecommerceapp.data.entity.api.categoryResponseApi.Category
import linkan.a740362.testecommerceapp.data.network.base.Result
import linkan.a740362.testecommerceapp.databinding.NavigationChildRowLayoutBinding

class Child2NavAdapter(private val dataList: MutableList<Category>) :
    BaseRecyclerViewAdapter<Category, Child2NavAdapter.Child2NavViewHolder>(dataList)  {



    private val child2CategoryLiveData: MutableLiveData<Result<Category>>  by lazy { MutableLiveData<Result<Category>>() }

    val mChild2CategoryLiveData: MutableLiveData<Result<Category>>
        get() = child2CategoryLiveData

    override fun mOnCreateViewHolder(parent: ViewGroup, viewType: Int): Child2NavViewHolder {
        return Child2NavViewHolder(
            NavigationChildRowLayoutBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }


    inner class Child2NavViewHolder(private val mBinding: NavigationChildRowLayoutBinding) :
        BaseRecyclerViewAdapter<Category, Child2NavViewHolder>.BaseViewHolder(mBinding.root) {

        @SuppressLint("WrongConstant")
        override fun onBind(model: Category?) {

            val viewModel1 = ChildNavAdapViewModel()

            mBinding.viewModel = viewModel1

            // Immediate Binding
            // When a variable or observable changes, the binding will be scheduled to change before
            // the next frame. There are times, however, when binding must be executed immediately.
            // To force execution, use the executePendingBindings() method.
            mBinding.executePendingBindings()


            mBinding.tvCategoryName.text = model?.name ?: itemView.context.resources.getString(R.string.empty)


            itemView.setOnClickListener {
                /**
                 * posting the clicked Pojo model
                 */
                 model?.let { category : Category ->
                     child2CategoryLiveData.postValue(Result.Success(category))
                 }


            }
        }

        override fun viewDetachedFromWindow() {

        }

    }


}