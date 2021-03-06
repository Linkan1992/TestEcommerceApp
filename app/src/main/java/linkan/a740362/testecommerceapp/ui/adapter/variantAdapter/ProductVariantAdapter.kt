package linkan.a740362.testecommerceapp.ui.adapter.variantAdapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.TextViewCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import linkan.a740362.testecommerceapp.base.BaseRecyclerViewAdapter
import linkan.a740362.testecommerceapp.data.entity.api.categoryResponseApi.SizeVariant
import linkan.a740362.testecommerceapp.data.network.base.Result
import linkan.a740362.testecommerceapp.databinding.AvailableVariantRowLayoutBinding
import androidx.recyclerview.widget.RecyclerView
import linkan.a740362.testecommerceapp.R


class ProductVariantAdapter(data: MutableList<SizeVariant>) :
    BaseRecyclerViewAdapter<SizeVariant, ProductVariantAdapter.ProdVariantViewHolder>(data) {

    private var previousActivatedPosition = -1
    private var mActivatedPosition = 0

    private val variantLiveData: MutableLiveData<Result<SizeVariant>>  by lazy { MutableLiveData<Result<SizeVariant>>() }

    val mVariantLiveData: LiveData<Result<SizeVariant>>
        get() = variantLiveData

    override fun mOnCreateViewHolder(parent: ViewGroup, viewType: Int): ProdVariantViewHolder {
        return ProdVariantViewHolder(
            AvailableVariantRowLayoutBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }


    inner class ProdVariantViewHolder(private val mBinding: AvailableVariantRowLayoutBinding) :
        BaseRecyclerViewAdapter<SizeVariant, ProdVariantViewHolder>.BaseViewHolder(mBinding.root) {

        override fun onBind(model: SizeVariant?) {

            val viewModel = ProdVariantViewModel()

            mBinding.viewModel = viewModel

            // Immediate Binding
            // When a variable or observable changes, the binding will be scheduled to change before
            // the next frame. There are times, however, when binding must be executed immediately.
            // To force execution, use the executePendingBindings() method.
            mBinding.executePendingBindings()

            //--------------------------------------------


            /**
             * Code to change visibility of itemView for
             * those category which do not has size
             */
            if (model?.size == null) {
                itemView.setVisibility(View.GONE)
                itemView.setLayoutParams(RecyclerView.LayoutParams(0, 0))
            }


            mBinding.tvSizeLabel.text = model?.size.toString()

            // set up for hide unhide
            val position = getAdapterPosition()

            val isActivated = position == mActivatedPosition

            if (isActivated) {
                // Activate Selection
                TextViewCompat.setTextAppearance(mBinding.tvSizeLabel, R.style.TextStyle_SelectedSize)
                mBinding.tvSizeLabel.setBackgroundResource(R.drawable.size_variant_selected_background)

                model?.let { variantLiveData.postValue(Result.Success(it)) }

            } else {
                // Deactivate Selection
                TextViewCompat.setTextAppearance(mBinding.tvSizeLabel, R.style.TextStyle_AvailableSize)
                mBinding.tvSizeLabel.setBackgroundResource(R.drawable.size_variant_background)
            }

            itemView.setActivated(isActivated)

            if (isActivated)
                previousActivatedPosition = position


            itemView.setOnClickListener {

                /*  mActivatedPosition = if (isActivated) position else position
                  notifyItemChanged(previousActivatedPosition)
                  notifyItemChanged(position)*/

                if (!isActivated) {
                    mActivatedPosition = position
                    notifyItemChanged(previousActivatedPosition)
                    notifyItemChanged(position)
                }

                //   model?.let { variantLiveData.postValue(Result.Success(it)) }

                // Toast.makeText(itemView.context, "item clicked is $model", Toast.LENGTH_LONG).show()

            }


            //----------------------------------------------


        }

        override fun viewDetachedFromWindow() {

        }

    }

}