package ke.co.basecode.model


data class APIError<T> (
    val status_code : Int? = -1,
    val meta : ErrorMeta?,
    var message: String? = "Request failed",
    var success: Boolean? = false
)
data class ErrorMeta(
    val code : String,
    val message: String
)