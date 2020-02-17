package linkan.a740362.testecommerceapp.base

import androidx.databinding.ObservableBoolean
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import linkan.a740362.testecommerceapp.data.network.base.Result

abstract class BaseViewModel : ViewModel() {

    protected val loading = ObservableBoolean(false);

    protected val refreshLoader = ObservableBoolean(false);

    /**
     * Method Returns value as Result Wrapper
     */
    protected fun<T : Any> applyResultTransformation(apiLiveData: LiveData<Result<T>>) : LiveData<Result<T>>{

        return Transformations.map(apiLiveData) { createResultData(result = it)}
    }

    private fun<T : Any> createResultData(result : Result<T>) : Result<T>{

        var data : Result<T> = result

        when (result) {
            is Result.Loading -> { if (result.isRefreshed) setRefreshed(true)  else setLoading(true) }
            is Result.Success -> {
                data = Result.Success(result.data)
                setRefreshed(false)
                setLoading(false)
            }
            is Result.Error ->{
                setRefreshed(false)
                setLoading(false)
            }
        }

        return data;
    }

    fun setLoading(isLoading: Boolean) {
        loading.set(isLoading)
    }

    fun setRefreshed(isRefreshed: Boolean) {
        refreshLoader.set(isRefreshed)
    }

    fun getAppLoading(): ObservableBoolean {
        return loading;
    }


}
