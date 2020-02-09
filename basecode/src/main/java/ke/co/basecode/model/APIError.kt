package ke.co.basecode.model


data class APIError<T> (
    val status_code : Int? = -1,
    var message: String? = "Request failed",
    var success: Boolean? = false,
    var data: T? = null
)