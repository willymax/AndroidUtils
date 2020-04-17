package ke.co.basecode.authentication.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ke.co.basecode.api.BaseApiService
import ke.co.basecode.authentication.data.LoginDataSource
import ke.co.basecode.authentication.data.LoginRepository
import ke.co.basecode.authentication.utilities.PrefUtils

/**
 * ViewModel provider factory to instantiate LoginViewModel.
 * Required given LoginViewModel has a non-empty constructor
 */
class LoginViewModelFactory(private val apiService: BaseApiService) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AuthenticationViewModel::class.java)) {
            return AuthenticationViewModel(
                loginRepository = LoginRepository(
                    dataSource = LoginDataSource(),
                    apiService = apiService
                )
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
