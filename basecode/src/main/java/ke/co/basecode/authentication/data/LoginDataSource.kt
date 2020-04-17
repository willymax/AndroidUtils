package ke.co.basecode.authentication.data

import ke.co.basecode.BaseCodeInit
import ke.co.basecode.api.BaseApiService
import ke.co.basecode.api.changePassword
import ke.co.basecode.api.signIn
import ke.co.basecode.authentication.login.User
import ke.co.basecode.authentication.utilities.PrefUtils
import ke.co.basecode.extensions.executeAsync

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
class LoginDataSource() {
    fun login(
        username: String,
        password: String,
        apiService: BaseApiService,
        onLoginResponse: (results: Result<User>) -> Unit
        ) {
        signIn(username, password, apiService, { user ->
            onLoginResponse(Result.Success(user))
        }, { error ->
            onLoginResponse(Result.Error("Error logging in $error"))
        })
    }

    fun logout() {
        executeAsync {
            BaseCodeInit.getInstance().getPrefUtils().signOut()
        }
    }
    fun saveUser(user: User) {
        executeAsync {
            BaseCodeInit.getInstance().getPrefUtils().saveUser(user)
        }
    }

    fun changeUserPassword(
        currentPassword: String,
        newPassword: String,
        apiService: BaseApiService,
        onChangePasswordResponse: (results: Result<User>) -> Unit
    ) {
        changePassword(
            currentPassword,
            newPassword,
            apiService,
            { user ->
                onChangePasswordResponse(Result.Success(user))
            },
            { error ->
                onChangePasswordResponse(Result.Error("Error changing password: $error"))
            })
    }

    val isLoggedIn: Boolean
        get() = BaseCodeInit.getInstance().getPrefUtils().getUser() != null

    val getLoggedInUser: User?
        get() = BaseCodeInit.getInstance().getPrefUtils().getUser()
}

