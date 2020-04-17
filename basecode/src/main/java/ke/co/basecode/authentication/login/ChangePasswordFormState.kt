package ke.co.basecode.authentication.login

/**
 * Created by Willy on 17/03/2020
 * Email williammakau070@gmail.com
 * @author willi
 */
data class ChangePasswordFormState(
    val currentPasswordError: Int? = null,
    val newPasswordError: Int? = null,
    val confirmNewPasswordError: Int? = null,
    val isDataValid: Boolean = false
)