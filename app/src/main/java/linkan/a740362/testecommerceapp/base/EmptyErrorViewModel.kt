package linkan.a740362.testecommerceapp.base

import  linkan.a740362.testecommerceapp.data.network.base.Result

import androidx.databinding.ObservableBoolean
import androidx.lifecycle.MutableLiveData

class EmptyErrorViewModel {

    val emptyErrorLiveData = MutableLiveData<Result<String>>()

    val isVisible = ObservableBoolean(false)

    fun onRetry() {
        /**
         * On click of button show grey background and hide the
         * layout
         */
        setIsVisible(false)
        emptyErrorLiveData.postValue(Result.Retry())
    }

    fun setIsVisible(value: Boolean) {
        this.isVisible.set(value)
    }

}
