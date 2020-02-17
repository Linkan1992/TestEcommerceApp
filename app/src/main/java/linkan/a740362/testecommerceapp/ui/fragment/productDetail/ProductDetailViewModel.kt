package linkan.a740362.testecommerceapp.ui.fragment.productDetail

import linkan.a740362.testecommerceapp.base.BaseViewModel
import linkan.a740362.testecommerceapp.data.network.ApiHelper
import linkan.a740362.testecommerceapp.data.persistence.db.DbHelper
import linkan.a740362.testecommerceapp.data.persistence.pref.PrefHelper

class ProductDetailViewModel(
    private val dbHelper: DbHelper,
    private val apiHelper: ApiHelper,
    private val prefHelper: PrefHelper
)  : BaseViewModel(){


}