package linkan.a740362.testecommerceapp.ui.activity.main

import android.annotation.SuppressLint
import linkan.a740362.testecommerceapp.BR
import linkan.a740362.testecommerceapp.R
import linkan.a740362.testecommerceapp.ViewModelProviderFactory
import linkan.a740362.testecommerceapp.base.BaseActivity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import linkan.a740362.testecommerceapp.data.network.base.Result
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import linkan.a740362.testecommerceapp.databinding.ActivityMainBinding

import javax.inject.Inject
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.DefaultItemAnimator
import linkan.a740362.testecommerceapp.data.entity.api.categoryResponseApi.ProductDetailResponse
import linkan.a740362.testecommerceapp.data.entity.api.categoryResponseApi.ProductResponse
import linkan.a740362.testecommerceapp.data.entity.api.rankProductResponse.ProductRankCategory
import linkan.a740362.testecommerceapp.ui.adapter.homeParentAdapter.ParentMainAdapter
import linkan.a740362.testecommerceapp.ui.fragment.navigationMain.MainNavigationFragment
import linkan.a740362.testecommerceapp.ui.fragment.productDetail.FragProductDetail


class MainActivity : BaseActivity<ActivityMainBinding, MainViewModel>() {

    lateinit var drawerToggle: ActionBarDrawerToggle

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

    @Inject
    lateinit var parentAdapter: ParentMainAdapter

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

        disableNavigation()

        //.. To Hide the home back button
        // supportActionBar?.setDisplayHomeAsUpEnabled(false)
        // supportActionBar?.setDisplayShowHomeEnabled(false)

        initMainNavFragment()
        setUpCustomDrawer()
        subscribeLiveData()
        setUpRecyclerView()

    }


    private fun initMainNavFragment() {
        onFragmentAdd(
            R.id.cl_drawer_container,
            MainNavigationFragment.newInstance(),
            MainNavigationFragment.TAG,
            R.anim.enter_from_left,
            R.anim.exit_to_right
        )
    }

    private fun setUpCustomDrawer() {


        drawerToggle = object : ActionBarDrawerToggle(
            this@MainActivity,
            viewDataBinding.drawerLayout,
            viewDataBinding.includeAppBar.toolbar,
            R.string.drawer_open,
            R.string.drawer_close
        ) {
            override fun onDrawerClosed(view: View) {
                invalidateOptionsMenu()
            }

            override fun onDrawerOpened(drawerView: View) {
                invalidateOptionsMenu()
            }

            override fun onDrawerSlide(drawerView: View, slideOffset: Float) {

            }

            override fun onDrawerStateChanged(newState: Int) {

            }
        }


        //  viewDataBinding.drawerLayout.setDrawerListener(drawerToggle)

        viewDataBinding.drawerLayout.addDrawerListener(drawerToggle)

        drawerToggle.syncState()


    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // The action bar home/up action should open or close the drawer.
        // ActionBarDrawerToggle will take care of this.
        if (drawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        return super.onOptionsItemSelected(item);

    }

    override fun onBackPressed() {
        if (viewDataBinding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            viewDataBinding.drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            //Here we are clearing back stack fragment entries
            val backStackEntry = supportFragmentManager.backStackEntryCount

            /**
             * it implies only one fragment was in stack and on backstack main activity
             * page will get opened
             */
            if (backStackEntry == 1)
                drawerReleaseLockMode()

            if (backStackEntry > 0)
                supportFragmentManager.popBackStackImmediate()
            else
                super.onBackPressed()
        }
    }

    @SuppressLint("WrongConstant")
    private fun setUpRecyclerView() {

        val recyclerView = viewDataBinding
            .includeAppBar
            .includedContentMain
            .mainProductRecyclerView

        mLayoutManager.orientation = LinearLayoutManager.VERTICAL
        recyclerView.layoutManager = mLayoutManager
        recyclerView.itemAnimator = DefaultItemAnimator()
        recyclerView.adapter = parentAdapter

    }


    private fun subscribeLiveData() {

        /**
         * livedata to update parent list and navigation menu
         */
        mainViewModel.mProductLiveData.observe(this, Observer { result: Result<ProductDetailResponse> ->

            when (result) {
                is Result.Success -> {

                    /**
                     * populate home page data
                     * code is commented since Observable list is not emmiting data
                     * reason is not identified
                     */
                    // mainViewModel.setProdRankDataList(result.data.rankings)

                    enableNavigation()

                    result.data.rankings?.let {
                        parentAdapter.clearItems()
                        parentAdapter.addItems(it)
                    }

                }

                is Result.Error -> {
                    parentAdapter.setErrorUIVisibility(true)
                    /**
                     * Clearing ViewModel empty setEmptyList
                     */
                    parentAdapter.clearItems()
                    parentAdapter.addItems(ArrayList())
                    //  showToast(result.message ?: resources.getString(R.string.error_reason))
                    disableNavigation()
                }
            }

        })


        /**
         * livedata to update parentList item click
         */
        parentAdapter.mRankingLiveData.observe(this, Observer { result: Result<ProductRankCategory> ->

            when (result) {

                is Result.ShowMore -> {

                    //------------ implement functionality of show more ------------
                    // showToast(result.toString())
                }


                is Result.Error -> {

                    showToast(result.message ?: resources.getString(R.string.error_reason))

                }
            }

        })


        /**
         * livedata to update childList item click
         */
        parentAdapter.mRankingProductLiveData.observe(this, Observer { result: Result<ProductResponse> ->

            when (result) {
                is Result.Success -> {

                    /**
                     * populate product detail page data
                     */
                    onFragmentAddToBackStack(
                        R.id.include_app_bar,
                        FragProductDetail.newInstance(result.data),
                        FragProductDetail.TAG,
                        R.anim.enter_from_right,
                        R.anim.exit_to_right
                    )

                    /**
                     * close drawer and lock it at close position
                     */
                    customCloseDrawer()
                    drawerLockToClose()

                }

                is Result.Error -> {
                    showToast(result.message ?: resources.getString(R.string.error_reason))

                }
            }

        })


        parentAdapter.baseRetryLiveData.observe(this@MainActivity, Observer {

            when (it) {

                is Result.Retry -> {

                    mainViewModel.fetProductDataList()

                }

            }
        })


    }


    fun customCloseDrawer() {

        viewDataBinding
            .drawerLayout
            .closeDrawer(GravityCompat.START)
    }

    /**
     * lock open drawer does not allow to close
     */
    fun drawerLockToOpen() {

        viewDataBinding.drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_OPEN)
    }

    /**
     * lock close drawer does not allow to open
     */
    fun drawerLockToClose() {

        viewDataBinding.drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
    }

    /**
     * unlock drawer which was earlier locked in order to enable it open and close
     */
    fun drawerReleaseLockMode() {

        viewDataBinding.drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)
    }

    private fun disableNavigation() {

        drawerLockToClose()
    }


    fun enableNavigation() {

        drawerReleaseLockMode()
    }

}
