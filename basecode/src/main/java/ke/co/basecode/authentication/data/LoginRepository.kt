package ke.co.basecode.authentication.data

import ke.co.basecode.api.BaseApiService
import ke.co.basecode.authentication.login.User

/**
 * Class that requests authentication and user information from the remote data source and
 * maintains an in-memory cache of login status and user credentials information.
 */

class LoginRepository(val dataSource: LoginDataSource, private val apiService: BaseApiService) {

    // in-memory cache of the loggedInUser object
    var user: User? = null
        private set

    val isLoggedIn: Boolean
        get() = user != null || dataSource.isLoggedIn

    init {
        // If user credentials will be cached in local storage, it is recommended it be encrypted
        // @see https://developer.android.com/training/articles/keystore
        user = dataSource.getLoggedInUser
    }

    fun logout() {
        user = null
        dataSource.logout()
    }

    fun login(username: String, password: String, onLoginResponse: (results: Result<User>) -> Unit) {
        // handle login
        dataSource.login(username, password, apiService) { result ->
            if (result is Result.Success) {
                setLoggedInUser(result.data)
            }
            onLoginResponse(result)
        }
    }

    private fun setLoggedInUser(loggedInUser: User) {
        this.user = loggedInUser
        // If user credentials will be cached in local storage, it is recommended it be encrypted
        // @see https://developer.android.com/training/articles/keystore
        dataSource.saveUser(loggedInUser)
    }

    fun changePassword(currentPassword: String, newPassword: String, onLoginResponse: (results: Result<User>) -> Unit) {
        // handle login
        dataSource.changeUserPassword(currentPassword, newPassword, apiService) { result ->
            if (result is Result.Success) {
                logout()
            }
            onLoginResponse(result)
        }
    }
}
