package linkan.a740362.testecommerceapp.ui.fragment.productRenderer

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import linkan.a740362.testecommerceapp.BR
import linkan.a740362.testecommerceapp.R
import linkan.a740362.testecommerceapp.ViewModelProviderFactory
import linkan.a740362.testecommerceapp.base.BaseActivity
import linkan.a740362.testecommerceapp.base.BaseFragment
import linkan.a740362.testecommerceapp.data.entity.api.categoryResponseApi.Category
import linkan.a740362.testecommerceapp.data.entity.api.categoryResponseApi.ProductResponse
import linkan.a740362.testecommerceapp.data.network.base.Result
import linkan.a740362.testecommerceapp.databinding.FragProductRendererBinding
import linkan.a740362.testecommerceapp.ui.activity.main.MainActivity
import linkan.a740362.testecommerceapp.ui.adapter.productRenderer.ProductRendererAdapter
import linkan.a740362.testecommerceapp.ui.fragment.productDetail.FragProductDetail
import linkan.a740362.testecommerceapp.util.AppConstants
import javax.inject.Inject

class FragProductRenderer : BaseFragment<FragProductRendererBinding, FragRendererViewModel>() {

    private lateinit var category: Category

    companion object {

        val TAG = FragProductRenderer::class.java.simpleName;

        fun newInstance(data: Category) = FragProductRenderer().apply {
            arguments = Bundle().apply {
                putSerializable(AppConstants.CATEGORY, data)
            }
        }

    }


    @Inject
    lateinit var viewModelProviderFactory: ViewModelProviderFactory

    @Inject
    lateinit var mLayoutManager: GridLayoutManager

    @Inject
    lateinit var rendererAdapter: ProductRendererAdapter


    private val rendererViewModel: FragRendererViewModel by lazy {
        ViewModelProviders.of(activity!!, viewModelProviderFactory).get(FragRendererViewModel::class.java)
    }

    override val layoutId: Int
        get() = R.layout.frag_product_renderer


    override val viewModel: FragRendererViewModel
        get() = rendererViewModel


    override val bindingVariable: Int
        get() = BR.viewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        category = arguments?.get(AppConstants.CATEGORY) as Category

        // setting data to livedata to simulate as API poll
        rendererViewModel.postRendererLiveData(category)

        subscribeLiveData()

    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpRecyclerView()
        homeBackPress()
    }


    @SuppressLint("WrongConstant")
    private fun setUpRecyclerView() {

        mLayoutManager.orientation = GridLayoutManager.VERTICAL
        viewDataBinding.rendererProductRecyclerView.layoutManager = mLayoutManager
        viewDataBinding.rendererProductRecyclerView.itemAnimator = DefaultItemAnimator()
        viewDataBinding.rendererProductRecyclerView.adapter = rendererAdapter

    }


    private fun subscribeLiveData() {

        rendererViewModel.mRendererLiveData.observe(this@FragProductRenderer, Observer { result: Result<Category> ->

            when (result) {
                is Result.Success -> {

                    viewDataBinding.includedBaseAppBar.title.text = result.data.name
                    // (activity as BaseActivity<*, *>).showToast(result.data.toString())

                    rendererViewModel.setRendererDataList(result.data.products)

                }
            }
        })


        // on Item Click Navigate to product Detail page
        rendererAdapter.mProductDetailLiveData.observe(
            this@FragProductRenderer, Observer { result: Result<ProductResponse> ->

                when (result) {
                    is Result.Success -> {

                        // (activity as BaseActivity<*, *>).showToast(result.data.toString())

                        (activity as BaseActivity<*, *>).onFragmentAddToBackStack(
                            R.id.include_app_bar,
                            FragProductDetail.newInstance(result.data),
                            FragProductDetail.TAG,
                            R.anim.enter_from_right,
                            R.anim.exit_to_right
                        )

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