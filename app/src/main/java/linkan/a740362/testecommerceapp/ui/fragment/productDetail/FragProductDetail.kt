package linkan.a740362.testecommerceapp.ui.fragment.productDetail

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import linkan.a740362.testecommerceapp.BR
import linkan.a740362.testecommerceapp.R
import linkan.a740362.testecommerceapp.ViewModelProviderFactory
import linkan.a740362.testecommerceapp.base.BaseActivity
import linkan.a740362.testecommerceapp.base.BaseFragment
import linkan.a740362.testecommerceapp.data.entity.api.categoryResponseApi.ProductResponse
import linkan.a740362.testecommerceapp.data.entity.api.categoryResponseApi.SizeVariant
import linkan.a740362.testecommerceapp.data.network.base.Result
import linkan.a740362.testecommerceapp.databinding.FragProductDetailBinding
import linkan.a740362.testecommerceapp.ui.activity.main.MainActivity
import linkan.a740362.testecommerceapp.ui.adapter.variantAdapter.ProductVariantAdapter
import linkan.a740362.testecommerceapp.util.AppConstants
import javax.inject.Inject
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import android.view.Gravity
import androidx.databinding.adapters.ViewBindingAdapter.setPadding
import android.widget.TextView
import java.util.*


class FragProductDetail : BaseFragment<FragProductDetailBinding, ProductDetailViewModel>() {

    private lateinit var productObj: ProductResponse

    companion object {

        val TAG = FragProductDetail::class.java.simpleName;

        fun newInstance(data: ProductResponse) = FragProductDetail().apply {
            arguments = Bundle().apply {
                putSerializable(AppConstants.PRODUCT, data)
            }
        }

    }


    @Inject
    lateinit var viewModelProviderFactory: ViewModelProviderFactory

    @Inject
    lateinit var mLayoutManager: LinearLayoutManager

    @Inject
    lateinit var variantAdapter: ProductVariantAdapter


    // creating view model with fragment scope
    private val detailViewModel: ProductDetailViewModel by lazy {
        ViewModelProviders.of(this@FragProductDetail, viewModelProviderFactory).get(ProductDetailViewModel::class.java)
    }

    override val layoutId: Int
        get() = R.layout.frag_product_detail


    override val viewModel: ProductDetailViewModel
        get() = detailViewModel


    override val bindingVariable: Int
        get() = BR.viewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        productObj = arguments?.get(AppConstants.PRODUCT) as ProductResponse

        // setting data to livedata to simulate as API poll
        detailViewModel.postVariantLiveData(productObj)

        subscribeLiveData()

    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpRecyclerView()
        homeBackPress()
        viewDataBinding.includedBaseAppBar.title.text = productObj.name

        calMaxChildVariant(productObj)
    }

    @SuppressLint("WrongConstant")
    private fun setUpRecyclerView() {

        mLayoutManager.orientation = LinearLayoutManager.HORIZONTAL
        viewDataBinding.rendererSizeRecyclerView.layoutManager = mLayoutManager
        viewDataBinding.rendererSizeRecyclerView.itemAnimator = DefaultItemAnimator()
        viewDataBinding.rendererSizeRecyclerView.adapter = variantAdapter

    }

    private fun subscribeLiveData() {

        detailViewModel.mProductLiveData.observe(this@FragProductDetail, Observer { result: Result<ProductResponse> ->

            when (result) {
                is Result.Success -> {

                    (activity as BaseActivity<*, *>).showToast(result.data.toString())

                    if (result.data.variants?.get(0)?.size != null)
                        Collections.sort(result.data.variants)

                    detailViewModel.setVariantDataList(result.data.variants)

                    viewDataBinding.includedBaseAppBar.title.text = productObj.name
                    viewDataBinding.tvProductName.text = result.data.name
                    viewDataBinding.tvProdPrice.text =
                        "${AppConstants.RUPEE_SYMBOL} ${result.data.variants?.get(0)?.colorVariant?.get(0)?.price.toString()}"
                }
            }
        })


        variantAdapter.mVariantLiveData.observe(this@FragProductDetail, Observer { result: Result<SizeVariant> ->

            when (result) {
                is Result.Success -> {

                    (activity as BaseActivity<*, *>).showToast(result.data.toString())

                }
            }
        })

    }


    private fun homeBackPress() {
        viewDataBinding.includedBaseAppBar.imgArrowBack.setOnClickListener {

            if (activity is MainActivity)
                (activity as MainActivity).onBackPressed()
            else
                (activity as BaseActivity<*, *>).onBackPressed()

        }

    }


    private fun calMaxChildVariant(singleProduct: ProductResponse) {

        var intMaxNoOfChild = 0
        for (index in 0 until singleProduct.variants?.size!!) {
            val intMaxSizeTemp = singleProduct.variants.get(index).colorVariant?.size
            if (intMaxSizeTemp!! > intMaxNoOfChild) intMaxNoOfChild = intMaxSizeTemp
        }


        for (indexView in 0 until intMaxNoOfChild) {
            val textView = TextView(context)
            textView.id = indexView
            textView.setPadding(0, 0, 0, 0)
            textView.gravity = Gravity.CENTER
            textView.background = ContextCompat.getDrawable(activity?.baseContext!!, R.drawable.image_placeholder)
            val layoutParams = LinearLayout.LayoutParams(
                80,
                LinearLayout.LayoutParams.MATCH_PARENT
            )
            layoutParams.setMargins(0, 0, 20, 0)
            viewDataBinding.llColorVariant.addView(textView, layoutParams)
        }
    }


}