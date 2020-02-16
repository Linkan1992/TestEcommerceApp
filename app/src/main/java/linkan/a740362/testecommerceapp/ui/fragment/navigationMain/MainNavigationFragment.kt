package linkan.a740362.testecommerceapp.ui.fragment.navigationMain

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import linkan.a740362.testecommerceapp.BR
import linkan.a740362.testecommerceapp.R
import linkan.a740362.testecommerceapp.ViewModelProviderFactory
import linkan.a740362.testecommerceapp.base.BaseActivity
import linkan.a740362.testecommerceapp.base.BaseFragment
import linkan.a740362.testecommerceapp.databinding.FragmentHomeNavigationBinding
import linkan.a740362.testecommerceapp.ui.activity.main.MainViewModel
import linkan.a740362.testecommerceapp.ui.adapter.mainNavigation.MainNavigationAdapter
import javax.inject.Inject

class MainNavigationFragment : BaseFragment<FragmentHomeNavigationBinding, MainViewModel>() {


    companion object {

        val TAG = MainNavigationFragment::class.java.simpleName;

        fun newInstance() = MainNavigationFragment()

    }


    @Inject lateinit var viewModelProviderFactory: ViewModelProviderFactory

    @Inject lateinit var mLayoutManager: LinearLayoutManager

    @Inject lateinit var mainNavAdapter : MainNavigationAdapter


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


    private fun onHomeBackPress(){

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



    }

}