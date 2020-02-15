package linkan.a740362.testecommerceapp.base

import linkan.a740362.testecommerceapp.R
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.annotation.AnimRes
import androidx.annotation.AnimatorRes
import androidx.annotation.IdRes
import androidx.annotation.LayoutRes
import androidx.appcompat.widget.Toolbar
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import dagger.android.support.DaggerAppCompatActivity

abstract class BaseActivity<T : ViewDataBinding, V : BaseViewModel> : DaggerAppCompatActivity() {

    lateinit var viewDataBinding: T
        private set

    var mViewModel: V? = null;

    @get:LayoutRes
    abstract val layoutId: Int

    abstract val viewModel: V

    abstract val bindingVariable: Int

    abstract val toolbar: Toolbar?

    abstract fun initOnCreate(savedInstanceState: Bundle?)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        performDataBinding()
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        initOnCreate(savedInstanceState)
        title = resources.getString(R.string.empty)

    }

    private fun performDataBinding() {
        viewDataBinding = DataBindingUtil.setContentView(this, layoutId)
        mViewModel = mViewModel ?: viewModel
        viewDataBinding.setVariable(bindingVariable, mViewModel)
        viewDataBinding.executePendingBindings()
    }

    fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }


    fun onFragmentAttached(
        @IdRes container_view: Int,
        fragment: Fragment,
        TAG: String,
        @AnimatorRes @AnimRes EnterAnimation: Int,
        @AnimatorRes @AnimRes ExitAnimation: Int
    ) {
        supportFragmentManager
            .beginTransaction()
            .disallowAddToBackStack()
            .setCustomAnimations(EnterAnimation, ExitAnimation)
            .add(container_view, fragment, TAG)
            .commit()

    }


    fun onFragmentDetached(
        TAG: String,
        @AnimatorRes @AnimRes EnterAnimation: Int,
        @AnimatorRes @AnimRes ExitAnimation: Int
    ) {
        val fragmentManager = supportFragmentManager
        val fragment = fragmentManager.findFragmentByTag(TAG)
        fragment?.let {
            fragmentManager
                .beginTransaction()
                .disallowAddToBackStack()
                .setCustomAnimations(EnterAnimation, ExitAnimation)
                .remove(it)
                .commitNow()
        }
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.home -> {
                onBackPressed()
            }
            else -> onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }

}
