package linkan.a740362.testecommerceapp.ui.activity.main

import android.annotation.SuppressLint
import linkan.a740362.testecommerceapp.BR
import linkan.a740362.testecommerceapp.R
import linkan.a740362.testecommerceapp.ViewModelProviderFactory
import linkan.a740362.testecommerceapp.base.BaseActivity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import linkan.a740362.testecommerceapp.databinding.ActivityMainBinding

import javax.inject.Inject

class MainActivity : BaseActivity<ActivityMainBinding, MainViewModel>(){


    companion object {

        fun newIntent(context: Context) {
            val intent = Intent(context, MainActivity::class.java)
            context.startActivity(intent)
        }

    }


    @Inject
    lateinit var viewModelProviderFactory: ViewModelProviderFactory

    @Inject
    lateinit var mLayoutManager: LinearLayoutManager

    private val mainViewModel: MainViewModel by lazy {
        ViewModelProviders.of(this, viewModelProviderFactory).get(MainViewModel::class.java)
    }

    override val layoutId: Int
        get() = R.layout.activity_main


    override val viewModel: MainViewModel
        get() = mainViewModel


    override val bindingVariable: Int
        get() = BR.viewModel


    override val toolbar: Toolbar?
        get() = viewDataBinding.includeAppBar.toolbar


    override fun initOnCreate(savedInstanceState: Bundle?) {

        //.. To Hide the home back button
       // supportActionBar?.setDisplayHomeAsUpEnabled(false)
       // supportActionBar?.setDisplayShowHomeEnabled(false)

        subscribeLiveData()
        setUpRecyclerView()
    }




    @SuppressLint("WrongConstant")
    private fun setUpRecyclerView() {


    }


    private fun subscribeLiveData() {

        mainViewModel.mProductLiveData.observe(this, Observer {

            showToast(it.toString())

        })

    }






}
