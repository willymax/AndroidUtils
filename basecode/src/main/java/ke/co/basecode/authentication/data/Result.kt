package ke.co.basecode.authentication.data

/**
 * A generic class that holds a value with its loading status.
 * @param <T>
 */
sealed class Result<out T : Any> {

    data class Success<out T : Any>(val data: T) : Result<T>()
    data class Error(val errorMessage: String) : Result<Nothing>()

    override fun toString(): String {
        return when (this) {
            is Success<*> -> "Success[data = $data]"
            is Error -> "Error[exception = $errorMessage]"
        }
    }
}
