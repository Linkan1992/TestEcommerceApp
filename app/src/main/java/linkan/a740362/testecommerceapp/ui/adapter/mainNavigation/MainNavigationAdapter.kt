package linkan.a740362.testecommerceapp.ui.adapter.mainNavigation

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import linkan.a740362.testecommerceapp.base.BaseRecyclerViewAdapter
import linkan.a740362.testecommerceapp.data.network.base.Result
import linkan.a740362.testecommerceapp.databinding.NavigationRowLayoutBinding


class MainNavigationAdapter(private val data: ArrayList<String>) :
    BaseRecyclerViewAdapter<String, MainNavigationAdapter.MainNavViewHolder>(data) {

    private val categoryLiveData: MutableLiveData<Result<String>>  by lazy { MutableLiveData<Result<String>>() }

    val mCategoryLiveData: MutableLiveData<Result<String>>
        get() = categoryLiveData

    override fun mOnCreateViewHolder(parent: ViewGroup, viewType: Int): MainNavViewHolder {
        return MainNavViewHolder(
            NavigationRowLayoutBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }


    inner class MainNavViewHolder(private val mBinding: NavigationRowLayoutBinding) :
        BaseRecyclerViewAdapter<String, MainNavViewHolder>.BaseViewHolder(mBinding.root) {

        override fun onBind(model: String?) {

            val viewModel = MainNavAdapViewModel()

            mBinding.viewModel = viewModel

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
                categoryLiveData.postValue(Result.Success(model ?: ""))

                Toast.makeText(itemView.context, "item clicked is $model", Toast.LENGTH_LONG).show()

            }
        }

        override fun viewDetachedFromWindow() {

        }

    }

}