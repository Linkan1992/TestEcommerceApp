package linkan.a740362.testecommerceapp.ui.activity.main

import android.annotation.SuppressLint
import linkan.a740362.testecommerceapp.BR
import linkan.a740362.testecommerceapp.R
import linkan.a740362.testecommerceapp.ViewModelProviderFactory
import linkan.a740362.testecommerceapp.base.BaseActivity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import linkan.a740362.testecommerceapp.databinding.ActivityMainBinding

import javax.inject.Inject
import androidx.core.view.GravityCompat
import linkan.a740362.testecommerceapp.ui.fragment.navigationMain.MainNavigationFragment


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
        onFragmentAdd(
            R.id.cl_drawer_container,
            MainNavigationFragment.newInstance(),
            MainNavigationFragment.TAG,
            R.anim.enter_from_left,
            R.anim.exit_to_left
        )

        initMainNavFragment()
        setUpCustomDrawer()
        subscribeLiveData()
        setUpRecyclerView()

    }


    private fun initMainNavFragment() {


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

            }

            override fun onDrawerOpened(drawerView: View) {

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
            super.onBackPressed()
        }
    }

    @SuppressLint("WrongConstant")
    private fun setUpRecyclerView() {


    }


    private fun subscribeLiveData() {

        mainViewModel.mProductLiveData.observe(this, Observer {

            showToast(it.toString())

            mainViewModel.setProductDataList(ArrayList<String>().apply {
                add("Mens Wear")
                add("Top wear")
                add("casuals")
                add("Electronics")
                add("Mens Wear")
                add("Top wear")
                add("casuals")
                add("Electronics")
                add("Mens Wear")
                add("Top wear")
                add("casuals")
                add("Electronics")
            })

        })

    }


}
