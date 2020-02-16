package linkan.a740362.testecommerceapp.ui.fragment.navigationChild

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import linkan.a740362.testecommerceapp.BR
import linkan.a740362.testecommerceapp.R
import linkan.a740362.testecommerceapp.ViewModelProviderFactory
import linkan.a740362.testecommerceapp.base.BaseActivity
import linkan.a740362.testecommerceapp.base.BaseFragment
import linkan.a740362.testecommerceapp.databinding.FragmentChildNavigationBinding
import linkan.a740362.testecommerceapp.ui.activity.main.MainViewModel
import linkan.a740362.testecommerceapp.ui.adapter.mainNavigation.MainNavigationAdapter
import linkan.a740362.testecommerceapp.ui.fragment.navigationMain.MainNavigationFragment
import javax.inject.Inject

class ChildNavigationFragment : BaseFragment<FragmentChildNavigationBinding, MainViewModel>() {


    companion object {

        val TAG = MainNavigationFragment::class.java.simpleName;

        fun newInstance() = MainNavigationFragment()

    }


    @Inject
    lateinit var viewModelProviderFactory: ViewModelProviderFactory

    @Inject
    lateinit var mLayoutManager: LinearLayoutManager

    @Inject
    lateinit var mainNavAdapter : MainNavigationAdapter


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

        //   onHomeBackPress()
    }


    private fun onHomeBackPress(){


    }


    @SuppressLint("WrongConstant")
    private fun setUpRecyclerView() {

        /*mLayoutManager.orientation = LinearLayoutManager.VERTICAL
        viewDataBinding.mainProductRecyclerView.layoutManager = mLayoutManager
        viewDataBinding.mainProductRecyclerView.itemAnimator = DefaultItemAnimator()
        viewDataBinding.mainProductRecyclerView.adapter = mainNavAdapter*/

    }


    private fun subscribeLiveData() {



    }

}