package linkan.a740362.testecommerceapp.data.network.base


sealed class Result<out T : Any> {

    data class Loading(val isRefreshed : Boolean = false) : Result<Nothing>()

    data class Success<out T : Any>(val data : T) : Result<T>()

    data class Error(val message : String?) : Result<Nothing>()

    data class Retry(val isRetry : Boolean = true) : Result<Nothing>()

}
