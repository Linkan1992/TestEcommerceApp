package linkan.a740362.testecommerceapp.ui.fragment.productDetail

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import linkan.a740362.testecommerceapp.BR
import linkan.a740362.testecommerceapp.R
import linkan.a740362.testecommerceapp.ViewModelProviderFactory
import linkan.a740362.testecommerceapp.base.BaseActivity
import linkan.a740362.testecommerceapp.base.BaseFragment
import linkan.a740362.testecommerceapp.data.entity.api.categoryResponseApi.Category
import linkan.a740362.testecommerceapp.data.entity.api.categoryResponseApi.ProductResponse
import linkan.a740362.testecommerceapp.data.network.base.Result
import linkan.a740362.testecommerceapp.databinding.FragProductDetailBinding
import linkan.a740362.testecommerceapp.ui.activity.main.MainActivity
import linkan.a740362.testecommerceapp.ui.fragment.productRenderer.FragRendererViewModel
import linkan.a740362.testecommerceapp.util.AppConstants
import javax.inject.Inject

class FragProductDetail : BaseFragment<FragProductDetailBinding, FragRendererViewModel>() {

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

    //  @Inject lateinit var mainNavAdapter: MainNavigationAdapter


    private val detailViewModel: FragRendererViewModel by lazy {
        ViewModelProviders.of(activity!!, viewModelProviderFactory).get(FragRendererViewModel::class.java)
    }

    override val layoutId: Int
        get() = R.layout.frag_product_detail


    override val viewModel: FragRendererViewModel
        get() = detailViewModel


    override val bindingVariable: Int
        get() = BR.viewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        productObj = arguments?.get(AppConstants.PRODUCT) as ProductResponse

        // setting data to livedata to simulate as API poll
        // rendererViewModel.postRendererLiveData(category)

        subscribeLiveData()

    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        homeBackPress()

        viewDataBinding.includedBaseAppBar.title.text = productObj.name
    }


    private fun subscribeLiveData() {

        detailViewModel.mRendererLiveData.observe(this@FragProductDetail, Observer { result: Result<Category> ->

            when (result) {
                is Result.Success -> {

                    (activity as BaseActivity<*, *>).showToast(result.data.toString())

                    viewDataBinding.includedBaseAppBar.title.text = productObj.name
                    // (activity as BaseActivity<*, *>).showToast(result.data.toString())


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


}