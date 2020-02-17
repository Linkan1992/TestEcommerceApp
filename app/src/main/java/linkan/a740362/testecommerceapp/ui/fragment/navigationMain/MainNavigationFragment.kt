package linkan.a740362.testecommerceapp.ui.fragment.navigationMain

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import linkan.a740362.testecommerceapp.data.network.base.Result
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import linkan.a740362.testecommerceapp.BR
import linkan.a740362.testecommerceapp.R
import linkan.a740362.testecommerceapp.ViewModelProviderFactory
import linkan.a740362.testecommerceapp.base.BaseActivity
import linkan.a740362.testecommerceapp.base.BaseFragment
import linkan.a740362.testecommerceapp.data.entity.api.categoryResponseApi.Category
import linkan.a740362.testecommerceapp.data.entity.api.categoryResponseApi.ProductDetailResponse
import linkan.a740362.testecommerceapp.databinding.FragmentHomeNavigationBinding
import linkan.a740362.testecommerceapp.ui.activity.main.MainViewModel
import linkan.a740362.testecommerceapp.ui.adapter.mainNavigation.MainNavigationAdapter
import linkan.a740362.testecommerceapp.ui.fragment.navigationChild.ChildNavigationFragment
import javax.inject.Inject

class MainNavigationFragment : BaseFragment<FragmentHomeNavigationBinding, MainViewModel>() {


    companion object {

        val TAG = MainNavigationFragment::class.java.simpleName;

        fun newInstance() = MainNavigationFragment()

    }


    @Inject
    lateinit var viewModelProviderFactory: ViewModelProviderFactory

    @Inject
    lateinit var mLayoutManager: LinearLayoutManager

    @Inject
    lateinit var mainNavAdapter: MainNavigationAdapter


    private val mainViewModel: MainViewModel by lazy {
        ViewModelProviders.of(activity!!, viewModelProviderFactory).get(MainViewModel::class.java)
    }

    override val layoutId: Int
        get() = R.layout.fragment_home_navigation


    override val viewModel: MainViewModel
        get() = mainViewModel


    override val bindingVariable: Int
        get() = BR.viewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        subscribeLiveData()

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpRecyclerView()

        //   onHomeBackPress()
    }


    private fun onHomeBackPress() {

        viewDataBinding.heading1.setOnClickListener {

            (activity as BaseActivity<*, *>).onFragmentRemove(
                MainNavigationFragment.TAG,
                R.anim.enter_from_left,
                R.anim.exit_to_left
            )
        }

    }


    @SuppressLint("WrongConstant")
    private fun setUpRecyclerView() {

        mLayoutManager.orientation = LinearLayoutManager.VERTICAL
        viewDataBinding.mainProductRecyclerView.layoutManager = mLayoutManager
        viewDataBinding.mainProductRecyclerView.itemAnimator = DefaultItemAnimator()
        viewDataBinding.mainProductRecyclerView.adapter = mainNavAdapter

    }


    private fun subscribeLiveData() {

        // main menu nav data
        mainViewModel.mProductLiveData.observe(this, Observer { result: Result<ProductDetailResponse> ->

            when (result) {
                is Result.Success -> {

                  //  (activity as BaseActivity<*, *>).showToast(result.toString())

                    mainViewModel.setMainNavDataList(result.data.categories)

                }

                is Result.Error -> {

                    (activity as BaseActivity<*, *>).showToast(result.message ?: resources.getString(R.string.error_reason))

                }
            }

        })

        mainNavAdapter.mCategoryLiveData.observe(this@MainNavigationFragment, Observer { result: Result<Category> ->

            when (result) {
                is Result.Success -> {

                    /**
                     * replace parent nav menu with child nav menu
                     */
                 /*   (activity as BaseActivity<*, *>).onFragmentAdd(
                        R.id.cl_drawer_container,
                        ChildNavigationFragment.newInstance(),
                        ChildNavigationFragment.TAG,
                        R.anim.enter_from_right,
                        R.anim.exit_to_left
                    )
*/

                    (activity as BaseActivity<*, *>).onFragmentReplace(
                        R.id.cl_drawer_container,
                        ChildNavigationFragment.newInstance(),
                        ChildNavigationFragment.TAG,
                        R.anim.enter_from_right,
                        R.anim.exit_to_left
                    )

                    mainViewModel.postChildLiveData(result.data)

                }
            }
        })

    }

}