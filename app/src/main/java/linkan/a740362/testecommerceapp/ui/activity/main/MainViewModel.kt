package linkan.a740362.testecommerceapp.ui.activity.main

import androidx.databinding.ObservableArrayList
import linkan.a740362.testecommerceapp.base.BaseViewModel
import linkan.a740362.testecommerceapp.data.network.ApiHelper
import linkan.a740362.testecommerceapp.data.persistence.db.DbHelper
import linkan.a740362.testecommerceapp.data.persistence.pref.PrefHelper
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import linkan.a740362.testecommerceapp.data.network.base.Result

class MainViewModel(
    private val dbHelper: DbHelper,
    private val apiHelper: ApiHelper,
    private val prefHelper: PrefHelper
) : BaseViewModel() {


    private val productLiveData: LiveData<Result<String>> =
        applyResultTransformation(apiHelper.getProductLiveData())

    val mProductLiveData: LiveData<Result<String>>
        get() = productLiveData


    init {
        apiHelper.fetchProductData()
    }


    //------------------- Main Navigation Code -----------------------


    private val productDataObservableList = ObservableArrayList<String>()

    val mProductDataObservableList: ObservableArrayList<String>
        get() = productDataObservableList


    fun setMainNavDataList(productList: List<String>) {

        productDataObservableList.clear()

        productDataObservableList.addAll(productList)

    }


    //------------------- Child Navigation Code -----------------------

    private val childNavDataObservableList = ObservableArrayList<String>()

    val mChildNavDataObservableList: ObservableArrayList<String>
        get() = childNavDataObservableList

    private val childNavLiveData: MutableLiveData<Result<List<String>>> by lazy { MutableLiveData<Result<List<String>>>() }

    val mChildNavLiveData: LiveData<Result<List<String>>>
        get() = childNavLiveData

    fun postChildLiveData(productList: List<String>) {
        childNavLiveData.postValue(Result.Success(productList))
    }

    fun setChildNavDataList(productList: List<String>) {

        childNavDataObservableList.clear()

        childNavDataObservableList.addAll(productList)

    }


}