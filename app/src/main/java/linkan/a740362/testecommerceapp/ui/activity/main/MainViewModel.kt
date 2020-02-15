package linkan.a740362.testecommerceapp.ui.activity.main

import androidx.databinding.ObservableArrayList
import linkan.a740362.testecommerceapp.base.BaseViewModel
import linkan.a740362.testecommerceapp.data.network.ApiHelper
import linkan.a740362.testecommerceapp.data.persistence.db.DbHelper
import linkan.a740362.testecommerceapp.data.persistence.pref.PrefHelper
import androidx.lifecycle.LiveData
import linkan.a740362.testecommerceapp.data.network.base.Result

class MainViewModel(
    private val dbHelper: DbHelper,
    private val apiHelper: ApiHelper,
    private val prefHelper: PrefHelper
) : BaseViewModel() {


    private val productDataObservableList = ObservableArrayList<String>()

    val mProductDataObservableList: ObservableArrayList<String>
        get() = productDataObservableList

    private val productLiveData: LiveData<Result<String>> =
        applyResultTransformation(apiHelper.getProductLiveData())

    val mProductLiveData: LiveData<Result<String>>
        get() = productLiveData



    init {
        apiHelper.fetchProductData()
    }

    fun setRestaurantList(productList: List<String>) {

        productDataObservableList.clear()

        productDataObservableList.addAll(productList)

    }


}