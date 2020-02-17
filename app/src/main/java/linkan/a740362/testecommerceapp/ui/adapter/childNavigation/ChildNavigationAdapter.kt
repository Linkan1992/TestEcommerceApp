package linkan.a740362.testecommerceapp.ui.adapter.childNavigation

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import linkan.a740362.testecommerceapp.R
import linkan.a740362.testecommerceapp.base.BaseRecyclerViewAdapter
import linkan.a740362.testecommerceapp.data.entity.api.categoryResponseApi.Category
import linkan.a740362.testecommerceapp.data.network.base.Result
import linkan.a740362.testecommerceapp.databinding.NavigationChildRowLayoutBinding

class ChildNavigationAdapter(private val dataList: MutableList<Category>) :
    BaseRecyclerViewAdapter<Category, ChildNavigationAdapter.ChildNavViewHolder>(dataList) {

    private var previousExpandedPosition = -1
    private var mExpandedPosition = -1

    private val viewPool = RecyclerView.RecycledViewPool()

    private var childCategoryLiveData: MediatorLiveData<Result<Category>> = MediatorLiveData()

    val mChildCategoryLiveData: LiveData<Result<Category>>
        get() = childCategoryLiveData

    override fun mOnCreateViewHolder(parent: ViewGroup, viewType: Int): ChildNavViewHolder {
        return ChildNavViewHolder(
            NavigationChildRowLayoutBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }


    inner class ChildNavViewHolder(private val mBinding: NavigationChildRowLayoutBinding) :
        BaseRecyclerViewAdapter<Category, ChildNavViewHolder>.BaseViewHolder(mBinding.root) {

        @SuppressLint("WrongConstant")
        override fun onBind(model: Category?) {

            val viewModel1 = ChildNavAdapViewModel()

            mBinding.viewModel = viewModel1

            // Immediate Binding
            // When a variable or observable changes, the binding will be scheduled to change before
            // the next frame. There are times, however, when binding must be executed immediately.
            // To force execution, use the executePendingBindings() method.
            mBinding.executePendingBindings()

            // set up for hide unhide
            val position = getAdapterPosition()

            val isExpanded = position == mExpandedPosition

            if (isExpanded) {
                // Expanded RecyclerView
                //    mBinding.greySeperator.visibility = View.GONE
                mBinding.childNavrecyclerView.visibility = View.VISIBLE

                mBinding.imgActionIcon.setImageResource(R.drawable.ic_expand_arrow_24dp)

            } else {
                // Collapse RecyclerView
                //   mBinding.greySeperator.visibility = View.VISIBLE
                mBinding.childNavrecyclerView.visibility = View.GONE

                mBinding.imgActionIcon.setImageResource(R.drawable.ic_collapse_arrow_24dp)

            }

            itemView.setActivated(isExpanded)

            if (isExpanded)
                previousExpandedPosition = position



            mBinding.tvCategoryName.text = model?.name ?: itemView.context.resources.getString(R.string.empty)


            // Create Nested RecyclerView
            val mLayoutManager = LinearLayoutManager(itemView.context)
            mLayoutManager.orientation = LinearLayoutManager.VERTICAL
            mBinding.childNavrecyclerView.layoutManager = mLayoutManager
            mBinding.childNavrecyclerView.itemAnimator = DefaultItemAnimator()
            mBinding.childNavrecyclerView.adapter = Child2NavAdapter(ArrayList<Category>().apply {
                addAll(model?.childCategories!!)
            }).apply {
                /**
                 * Single source LiveData is binded to nested multiple child live data
                 * hence need to use Mediator Live Data since all will be observed
                 */
                childCategoryLiveData.addSource(this.mChild2CategoryLiveData) {
                    childCategoryLiveData.postValue(it)
                }
            }
            mBinding.childNavrecyclerView.setRecycledViewPool(viewPool)



            itemView.setOnClickListener {


                mExpandedPosition = if (isExpanded) -1 else position
                notifyItemChanged(previousExpandedPosition)
                notifyItemChanged(position)

            }
        }

        override fun viewDetachedFromWindow() {

        }

    }


}