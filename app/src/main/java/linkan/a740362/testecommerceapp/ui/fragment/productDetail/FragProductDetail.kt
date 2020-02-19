package linkan.a740362.testecommerceapp.ui.fragment.productDetail

import android.annotation.SuppressLint
import android.graphics.Color
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
import android.widget.TextView
import linkan.a740362.testecommerceapp.data.entity.api.categoryResponseApi.ColorVariant
import linkan.a740362.testecommerceapp.util.UtilFunction
import java.util.*


class FragProductDetail : BaseFragment<FragProductDetailBinding, ProductDetailViewModel>() {

    private var intMaxNoOfChildViews = 0

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
        checkForSizeLabelVisibility()
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

                    //   (activity as BaseActivity<*, *>).showToast(result.data.toString())

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

                    changeChildView(result.data.colorVariant)

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


    private fun checkForSizeLabelVisibility() {
        viewDataBinding.tvSizeLabel.visibility =
            if (productObj.variants?.get(0)?.size == null) View.GONE else View.VISIBLE
    }


    private fun calMaxChildVariant(singleProduct: ProductResponse) {

        // Calculating Maximum Child Views Count

        for (index in 0 until singleProduct.variants?.size!!) {
            val intMaxSizeTemp = singleProduct.variants.get(index).colorVariant?.size
            if (intMaxSizeTemp!! > intMaxNoOfChildViews) intMaxNoOfChildViews = intMaxSizeTemp
        }

        // Creating Maximum Available Child Views
        for (indexView in 0 until intMaxNoOfChildViews) {
            val textView = TextView(context)
            textView.id = indexView
            textView.setPadding(0, 0, 0, 0)
            textView.gravity = Gravity.CENTER
            textView.background = ContextCompat.getDrawable(activity?.baseContext!!, R.drawable.image_placeholder)
            textView.visibility = View.GONE
            val layoutParams = LinearLayout.LayoutParams(
                resources.getDimension(R.dimen.dimes_24dp).toInt(),
                LinearLayout.LayoutParams.MATCH_PARENT
            )
            layoutParams.setMargins(
                resources.getDimension(R.dimen.dimes_4dp).toInt(),
                resources.getDimension(R.dimen.dimes_4dp).toInt(),
                resources.getDimension(R.dimen.dimes_4dp).toInt(),
                resources.getDimension(R.dimen.dimes_4dp).toInt()
            )
            viewDataBinding.llColorVariant.addView(textView, layoutParams)
        }
    }


    /**
     * making child color variant visible
     */
    private fun changeChildView(colorVariant: List<ColorVariant>?) {

        val noOfChild = colorVariant?.size ?: 0

        for (index in 0 until intMaxNoOfChildViews) {
            //First make all TextViews visible
            val currentTextView = viewDataBinding.llColorVariant.getChildAt(index) as TextView
            currentTextView.visibility = View.VISIBLE

            if (index < noOfChild) {

                if (index == 0) {
                    updateProductVarint(colorVariant?.get(0))
                }

                // if available sets color else set white color
                currentTextView.setBackgroundColor(
                    Color.parseColor(UtilFunction.getHexaDeciColor(colorVariant?.get(index)?.color))
                )

                currentTextView.setOnClickListener {

                    (activity as BaseActivity<*, *>).showToast(colorVariant.toString())

                    updateProductVarint(colorVariant?.get(index))

                }
            }
        }

        if (noOfChild < intMaxNoOfChildViews) {
            for (index in noOfChild until intMaxNoOfChildViews) {
                //Later hide all unnecessary TextViews
                val currentTextView = viewDataBinding.llColorVariant.getChildAt(index) as TextView
                currentTextView.visibility = View.GONE
            }
        }

    }

    /**
     * updating product price based on child color
     */
    private fun updateProductVarint(colorVariant: ColorVariant?) {
        colorVariant?.let {
            viewDataBinding.tvProdPrice.text = "${AppConstants.RUPEE_SYMBOL} ${it.price}"
        }
    }


}