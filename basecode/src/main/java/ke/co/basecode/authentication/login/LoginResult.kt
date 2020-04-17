package ke.co.basecode.authentication.login

/**
 * Authentication result : success (user details) or error message.
 */
data class LoginResult(
    val success: LoggedInUserView? = null,
    val error: Int? = null
)

data class ChangePasswordResult(
    val success: String? = null,
    val error: Int? = null
)

