package ke.co.basecode.authentication.login

import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ke.co.basecode.authentication.data.LoginRepository
import ke.co.basecode.authentication.data.Result
import ke.co.basecode.R

class AuthenticationViewModel(private val loginRepository: LoginRepository) : ViewModel() {
    enum class AuthenticationState {
        UNAUTHENTICATED,        // Initial state, the user needs to authenticate
        AUTHENTICATED,        // The user has authenticated successfully
        INVALID_AUTHENTICATION  // Authentication failed
    }

    enum class ChangePasswordState {
        CHANGED,
        FAILED,
    }

    val authenticationState = MutableLiveData<AuthenticationState>()
    private val changePasswordState = MutableLiveData<ChangePasswordState>()
    var user: User?

    init {
        authenticationState.value =
            if (loginRepository.isLoggedIn) AuthenticationState.AUTHENTICATED else AuthenticationState.UNAUTHENTICATED
        user = loginRepository.user
        changePasswordState.value = ChangePasswordState.FAILED
    }

    private val _loginForm = MutableLiveData<LoginFormState>()
    private val _changePasswordForm = MutableLiveData<ChangePasswordFormState>()
    val loginFormState: LiveData<LoginFormState> = _loginForm
    val changePasswordFormState: LiveData<ChangePasswordFormState> = _changePasswordForm

    private val _loginResult = MutableLiveData<LoginResult>()
    private val _changePasswordResult = MutableLiveData<ChangePasswordResult>()
    val loginResult: LiveData<LoginResult> = _loginResult
    val changePasswordResult: LiveData<ChangePasswordResult> = _changePasswordResult

    fun login(username: String, password: String) {
        // can be launched in a separate asynchronous job
        loginRepository.login(username, password) { result ->
            if (result is Result.Success) {
                //
                authenticationState.value = AuthenticationState.AUTHENTICATED
                _loginResult.value =
                    LoginResult(success = LoggedInUserView(displayName = result.data.name))
            } else {
                authenticationState.value = AuthenticationState.INVALID_AUTHENTICATION
                _loginResult.value = LoginResult(error = R.string.login_failed)
            }
        }
    }

    fun changePassword(currentPassword: String, newPassword: String) {
        // can be launched in a separate asynchronous job
        loginRepository.changePassword(currentPassword, newPassword) { result ->
            if (result is Result.Success) {
                //
                changePasswordState.value = ChangePasswordState.CHANGED
                _changePasswordResult.value =
                    ChangePasswordResult(success = "Password changed")
                authenticationState.value = AuthenticationState.UNAUTHENTICATED
            } else {
                changePasswordState.value = ChangePasswordState.FAILED
                _changePasswordResult.value =
                    ChangePasswordResult(error = R.string.change_password_failed)
            }
        }
    }

    fun refuseAuthentication() {
        authenticationState.value = AuthenticationState.UNAUTHENTICATED
    }

    fun loginDataChanged(username: String, password: String) {
        if (!isUserNameValid(username)) {
            _loginForm.value = LoginFormState(usernameError = R.string.invalid_email)
        } else if (!isPasswordValid(password)) {
            _loginForm.value = LoginFormState(passwordError = R.string.invalid_password)
        } else {
            _loginForm.value = LoginFormState(isDataValid = true)
        }
    }

    fun changePasswordDataChanged(
        currentPassword: String,
        newPassword: String,
        confirmNewPassword: String
    ) {
        if (!isPasswordValid(currentPassword)) {
            _changePasswordForm.value =
                ChangePasswordFormState(currentPasswordError = R.string.invalid_password)
        } else if (!isPasswordValid(newPassword)) {
            _changePasswordForm.value =
                ChangePasswordFormState(newPasswordError = R.string.invalid_password)
        } else if (!isConfirmPasswordValid(newPassword, confirmNewPassword)) {
            _changePasswordForm.value =
                ChangePasswordFormState(confirmNewPasswordError = R.string.passwords_dont_match)
        } else {
            _changePasswordForm.value = ChangePasswordFormState(isDataValid = true)
        }
    }

    // A placeholder username validation check
    private fun isUserNameValid(username: String): Boolean {
        return username.isNotBlank() && Patterns.EMAIL_ADDRESS.matcher(username).matches()
    }

    // A placeholder password validation check
    private fun isPasswordValid(@Suppress("UNUSED_PARAMETER") password: String): Boolean {
        //return password.length > 5
        return password.isNotEmpty()
    }

    // A placeholder password validation check
    private fun isConfirmPasswordValid(password: String, confirmPassword: String): Boolean {
        //return password.length > 5
        return password.equals(confirmPassword, false)
    }

    fun logout() {
        loginRepository.logout()
        refuseAuthentication()
    }
}
