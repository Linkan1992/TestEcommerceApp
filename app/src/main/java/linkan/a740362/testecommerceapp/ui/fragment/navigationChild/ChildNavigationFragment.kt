package linkan.a740362.testecommerceapp.ui.fragment.navigationChild

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
import linkan.a740362.testecommerceapp.data.network.base.Result
import linkan.a740362.testecommerceapp.databinding.FragmentChildNavigationBinding
import linkan.a740362.testecommerceapp.ui.activity.main.MainViewModel
import linkan.a740362.testecommerceapp.ui.adapter.childNavigation.ChildNavigationAdapter
import linkan.a740362.testecommerceapp.ui.fragment.navigationMain.MainNavigationFragment
import javax.inject.Inject

class ChildNavigationFragment : BaseFragment<FragmentChildNavigationBinding, MainViewModel>() {


    companion object {

        val TAG = ChildNavigationFragment::class.java.simpleName;

        fun newInstance() = ChildNavigationFragment()

    }


    @Inject
    lateinit var viewModelProviderFactory: ViewModelProviderFactory

    @Inject
    lateinit var mLayoutManager: LinearLayoutManager

    @Inject
    lateinit var childNavAdapter: ChildNavigationAdapter


    private val mainViewModel: MainViewModel by lazy {
        ViewModelProviders.of(activity!!, viewModelProviderFactory).get(MainViewModel::class.java)
    }

    override val layoutId: Int
        get() = R.layout.fragment_child_navigation


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

        onHomeBackPress()
    }


    private fun onHomeBackPress() {

        viewDataBinding.includedBaseAppBar.imgArrowBack.setOnClickListener {

            /**
             * replace child nav menu with parent nav menu
             */
            /*(activity as BaseActivity<*, *>).onFragmentReplace(
                R.id.cl_drawer_container,
                MainNavigationFragment.newInstance(),
                MainNavigationFragment.TAG,
                R.anim.enter_from_left,
                R.anim.exit_to_right
            )*/

            replaceMainNavFragment()

        }

    }


    @SuppressLint("WrongConstant")
    private fun setUpRecyclerView() {

        mLayoutManager.orientation = LinearLayoutManager.VERTICAL
        viewDataBinding.childProductRecyclerView.layoutManager = mLayoutManager
        viewDataBinding.childProductRecyclerView.itemAnimator = DefaultItemAnimator()
        viewDataBinding.childProductRecyclerView.adapter = childNavAdapter

    }


    private fun subscribeLiveData() {


        mainViewModel.mChildNavLiveData.observe(this@ChildNavigationFragment, Observer { result: Result<List<String>> ->

            when (result) {
                is Result.Success -> {

                    mainViewModel.setChildNavDataList(result.data)

                }
            }
        })



        childNavAdapter.mChildCategoryLiveData.observe(
            this@ChildNavigationFragment,
            Observer { result: Result<String> ->

                when (result) {
                    is Result.Success -> {

                        replaceMainNavFragment()
                    }
                }
            })

    }


    private fun replaceMainNavFragment() {

        /**
         * replace parent nav menu with child nav menu
         */
        (activity as BaseActivity<*, *>).onFragmentReplace(
            R.id.cl_drawer_container,
            MainNavigationFragment.newInstance(),
            MainNavigationFragment.TAG,
            R.anim.enter_from_left,
            R.anim.exit_to_right
        )

    }

}