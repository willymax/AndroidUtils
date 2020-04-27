package ke.co.basecode.authentication.login


import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.FrameLayout
import android.widget.Toast
import androidx.activity.addCallback
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
import kotlinx.android.synthetic.main.fragment_login.*

/**
 * A simple [Fragment] subclass.
 */
class LoginFragment(private val apiService: BaseApiService) : BaseAppFragment<BaseResponseModel<User>>() {
    private lateinit var authenticationViewModel: AuthenticationViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requireActivity().onBackPressedDispatcher.addCallback(this) {
            activity!!.finish()
        }
    }

    override fun onSetUpContentView(container: FrameLayout) {
        super.onSetUpContentView(container)
        layoutInflater.inflate(R.layout.fragment_login, container, true)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        authenticationViewModel = ViewModelProviders.of(this, LoginViewModelFactory(apiService))
            .get(AuthenticationViewModel::class.java)

        val navController = findNavController()

        authenticationViewModel.loginFormState.observe(viewLifecycleOwner, Observer {
            val loginState = it ?: return@Observer

            // disable login button unless both username / password is valid
            login_button.isEnabled = loginState.isDataValid

            if (loginState.usernameError != null) {
                username_edit_text.error = getString(loginState.usernameError)
            }

            if (loginState.passwordError != null) {
                password_edit_text.error = getString(loginState.passwordError)
            }

        })

        authenticationViewModel.loginResult.observe(viewLifecycleOwner, Observer {

            val loginResult = it ?: return@Observer

            //loading_progress_bar.visibility = View.GONE
            onHideLoading()
            if (loginResult.error != null) {
                showLoginFailed(loginResult.error)
            }

            if (loginResult.success != null) {
                updateUiWithUser(loginResult.success)
                //navController.navigate(R.id.main_fragment)
            }
            //setResult(Activity.RESULT_OK)

            //Complete and destroy login activity once successful
            //finish()
        })

        username_edit_text.afterTextChanged {
            authenticationViewModel.loginDataChanged(
                username_edit_text.text.toString(),
                password_edit_text.text.toString()
            )
        }

        password_edit_text.apply {
            afterTextChanged {
                authenticationViewModel.loginDataChanged(
                    username_edit_text.text.toString(),
                    password_edit_text.text.toString()
                )
            }

            setOnEditorActionListener { _, actionId, _ ->
                when (actionId) {
                    EditorInfo.IME_ACTION_DONE ->
                        authenticationViewModel.login(
                            username_edit_text.text.toString(),
                            password_edit_text.text.toString()
                        )
                }
                false
            }

            login_button.setOnClickListener {
               // Utils.hideSoftKeyboard(activity!!, view)
                //loading_progress_bar.visibility = View.VISIBLE
                onShowLoading()
                authenticationViewModel.login(username_edit_text.text.toString(), password_edit_text.text.toString())
            }
        }

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner){
            authenticationViewModel.refuseAuthentication()
            requireActivity().finish()
            //navController.popBackStack(R.id.main_fragment, false)
        }
    }

    private fun updateUiWithUser(model: LoggedInUserView) {
        val welcome = getString(R.string.welcome)
        val displayName = model.displayName
        Toast.makeText(
            requireContext(),
            "$welcome $displayName",
            Toast.LENGTH_LONG
        ).show()
    }

    private fun showLoginFailed(@StringRes errorString: Int) {
        Toast.makeText(requireContext(), errorString, Toast.LENGTH_SHORT).show()
    }
}


/**
 * Extension function to simplify setting an afterTextChanged action to EditText components.
 */
fun EditText.afterTextChanged(afterTextChanged: (String) -> Unit) {
    this.addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(editable: Editable?) {
            afterTextChanged.invoke(editable.toString())
        }

        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
    })
}