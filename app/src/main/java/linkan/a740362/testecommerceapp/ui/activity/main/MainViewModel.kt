package linkan.a740362.testecommerceapp.ui.activity.main

import androidx.databinding.ObservableArrayList
import linkan.a740362.testecommerceapp.base.BaseViewModel
import linkan.a740362.testecommerceapp.data.network.ApiHelper
import linkan.a740362.testecommerceapp.data.persistence.db.DbHelper
import linkan.a740362.testecommerceapp.data.persistence.pref.PrefHelper
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import linkan.a740362.testecommerceapp.data.entity.api.categoryResponseApi.Category
import linkan.a740362.testecommerceapp.data.entity.api.categoryResponseApi.ProductDetailResponse
import linkan.a740362.testecommerceapp.data.network.base.Result

class MainViewModel(
    private val dbHelper: DbHelper,
    private val apiHelper: ApiHelper,
    private val prefHelper: PrefHelper
) : BaseViewModel() {


    private val productLiveData: LiveData<Result<ProductDetailResponse>> =
        applyResultTransformation(apiHelper.getProductLiveData())

    val mProductLiveData: LiveData<Result<ProductDetailResponse>>
        get() = productLiveData


    init {
        apiHelper.fetchProductData()
    }


    //------------------- Main Navigation Code -----------------------


    private val productDataObservableList = ObservableArrayList<Category>()

    val mProductDataObservableList: ObservableArrayList<Category>
        get() = productDataObservableList


    fun setMainNavDataList(productList: List<Category>?) {

        productDataObservableList.clear()

        productDataObservableList.addAll(productList ?: ArrayList())

    }


    //------------------- Child Navigation Code -----------------------

    private val childNavDataObservableList = ObservableArrayList<Category>()

    val mChildNavDataObservableList: ObservableArrayList<Category>
        get() = childNavDataObservableList

    private val childNavLiveData: MutableLiveData<Result<Category>> by lazy { MutableLiveData<Result<Category>>() }

    val mChildNavLiveData: LiveData<Result<Category>>
        get() = childNavLiveData

    fun postChildLiveData(category: Category) {
        childNavLiveData.postValue(Result.Success(category))
    }

    fun setChildNavDataList(productList: List<Category>?) {

        childNavDataObservableList.clear()

        childNavDataObservableList.addAll(productList ?: ArrayList())

    }


}