package linkan.a740362.testecommerceapp.ui.fragment.productRenderer

import androidx.databinding.ObservableArrayList
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import linkan.a740362.testecommerceapp.base.BaseViewModel
import linkan.a740362.testecommerceapp.data.entity.api.categoryResponseApi.Category
import linkan.a740362.testecommerceapp.data.entity.api.categoryResponseApi.ProductResponse
import linkan.a740362.testecommerceapp.data.network.ApiHelper
import linkan.a740362.testecommerceapp.data.network.base.Result
import linkan.a740362.testecommerceapp.data.persistence.db.DbHelper
import linkan.a740362.testecommerceapp.data.persistence.pref.PrefHelper

class FragRendererViewModel(
    private val dbHelper: DbHelper,
    private val apiHelper: ApiHelper,
    private val prefHelper: PrefHelper
) : BaseViewModel() {

    private val rendererDataObservableList = ObservableArrayList<ProductResponse>()

    val mRendererDataObservableList: ObservableArrayList<ProductResponse>
        get() = rendererDataObservableList

    private val rendererLiveData: MutableLiveData<Result<Category>> by lazy { MutableLiveData<Result<Category>>() }

    // LiveData to simulate API Call response
    val mRendererLiveData: LiveData<Result<Category>>
        get() = rendererLiveData

    fun postRendererLiveData(category: Category) {
        rendererLiveData.postValue(Result.Success(category))
    }


    // update list from LiveData
    fun setRendererDataList(productList: List<ProductResponse>?) {

        rendererDataObservableList.clear()

        rendererDataObservableList.addAll(productList ?: ArrayList())

    }

}