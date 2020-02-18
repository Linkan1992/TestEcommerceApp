package linkan.a740362.testecommerceapp.ui.fragment.productDetail

import androidx.databinding.ObservableArrayList
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import linkan.a740362.testecommerceapp.base.BaseViewModel
import linkan.a740362.testecommerceapp.data.entity.api.categoryResponseApi.Category
import linkan.a740362.testecommerceapp.data.entity.api.categoryResponseApi.ProductResponse
import linkan.a740362.testecommerceapp.data.entity.api.categoryResponseApi.SizeVariant
import linkan.a740362.testecommerceapp.data.network.ApiHelper
import linkan.a740362.testecommerceapp.data.network.base.Result
import linkan.a740362.testecommerceapp.data.persistence.db.DbHelper
import linkan.a740362.testecommerceapp.data.persistence.pref.PrefHelper

class ProductDetailViewModel(
    private val dbHelper: DbHelper,
    private val apiHelper: ApiHelper,
    private val prefHelper: PrefHelper
) : BaseViewModel() {


    private val sizeVariantObservableList = ObservableArrayList<SizeVariant>()

    val mSizeVariantObservableList: ObservableArrayList<SizeVariant>
        get() = sizeVariantObservableList

    private val productLiveData: MutableLiveData<Result<ProductResponse>> by lazy { MutableLiveData<Result<ProductResponse>>() }

    val mProductLiveData: LiveData<Result<ProductResponse>>
        get() = productLiveData

    fun postVariantLiveData(product: ProductResponse) {
        productLiveData.postValue(Result.Success(product))
    }

    fun setVariantDataList(productList: List<SizeVariant>?) {

        sizeVariantObservableList.clear()

        sizeVariantObservableList.addAll(productList ?: ArrayList())

    }

}