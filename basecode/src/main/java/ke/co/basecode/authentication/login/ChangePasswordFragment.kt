package ke.co.basecode.authentication.login

import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.FrameLayout
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import ke.co.basecode.R
import ke.co.basecode.api.BaseApiService
import ke.co.basecode.app.BaseAppFragment
import ke.co.basecode.logging.BeeLog
import ke.co.basecode.model.BaseResponseModel
import kotlinx.android.synthetic.main.fragment_change_password.*

/**
 * A simple [Fragment] subclass.
 */
class ChangePasswordFragment(private val apiService: BaseApiService) : BaseAppFragment<BaseResponseModel<User>>() {
    private lateinit var authenticationViewModel: AuthenticationViewModel

    override fun onSetUpContentView(container: FrameLayout) {
        super.onSetUpContentView(container)
        layoutInflater.inflate(R.layout.fragment_change_password, container, true)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        authenticationViewModel = ViewModelProviders.of(this, LoginViewModelFactory(apiService))
                .get(AuthenticationViewModel::class.java)

        val navController = findNavController()

        authenticationViewModel.changePasswordFormState.observe(viewLifecycleOwner, Observer {
            val changeState = it ?: return@Observer

            // disable login button unless both username / password is valid
            change_button.isEnabled = changeState.isDataValid

            if (changeState.currentPasswordError != null) {
                current_password_edit_text.error = getString(changeState.currentPasswordError)
            }

            if (changeState.newPasswordError != null) {
                new_password_edit_text.error = getString(changeState.newPasswordError)
            }

            if (changeState.confirmNewPasswordError != null) {
                confirm_new_password_edit_text.error =
                        getString(changeState.confirmNewPasswordError)
            }

        })

        authenticationViewModel.changePasswordResult.observe(viewLifecycleOwner, Observer {

            val changePasswordResult = it ?: return@Observer

            //loading_progress_bar.visibility = View.GONE
            onHideLoading()
            BeeLog.d("William", "Called loginResult")
            if (changePasswordResult.error != null) {
                showChangePasswordFailed(changePasswordResult.error)
            }

            if (changePasswordResult.success != null) {
                showChangePasswordFailed(R.string.password_changed_successfully)
                //navController.navigate(R.id.login_fragment)
            }
            //setResult(Activity.RESULT_OK)

            //Complete and destroy login activity once successful
            //finish()
        })

        current_password_edit_text.afterTextChanged {
            authenticationViewModel.changePasswordDataChanged(
                    current_password_edit_text.text.toString(),
                    new_password_edit_text.text.toString(),
                    confirm_new_password_edit_text.text.toString()
            )
        }

        new_password_edit_text.afterTextChanged {
            authenticationViewModel.changePasswordDataChanged(
                    current_password_edit_text.text.toString(),
                    new_password_edit_text.text.toString(),
                    confirm_new_password_edit_text.text.toString()
            )
        }

        confirm_new_password_edit_text.apply {
            afterTextChanged {
                authenticationViewModel.changePasswordDataChanged(
                        current_password_edit_text.text.toString(),
                        new_password_edit_text.text.toString(),
                        confirm_new_password_edit_text.text.toString()
                )
            }

            setOnEditorActionListener { _, actionId, _ ->
                when (actionId) {
                    EditorInfo.IME_ACTION_DONE ->
                        authenticationViewModel.changePassword(
                                current_password_edit_text.text.toString(),
                                new_password_edit_text.text.toString()
                        )
                }
                false
            }

            change_button.setOnClickListener {
                //Utils.hideSoftKeyboard(activity!!, view)
                //loading_progress_bar.visibility = View.VISIBLE
                onShowLoading()
                authenticationViewModel.changePassword(
                        current_password_edit_text.text.toString(),
                        new_password_edit_text.text.toString()
                )
            }
        }
    }

    private fun showChangePasswordFailed(@StringRes errorString: Int) {
        Toast.makeText(requireContext(), errorString, Toast.LENGTH_SHORT).show()
    }

}
