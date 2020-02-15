package linkan.a740362.testecommerceapp.data.network.base


import retrofit2.Response


abstract class BaseRepository {

    protected suspend fun <T : Any> makeApiCall( call : suspend () -> Response<T>): Result<T> {
        try {
            val response = call()
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) return Result.Success(body)
            }
            return error(" ${response.code()} ${response.message()}")
        } catch (e: Exception) {
            return error(e.message ?: e.toString())
        }
    }

    private fun <T : Any> error(message: String): Result<T> {

        return Result.Error("Network call has failed for a following reason: $message")
    }



}
