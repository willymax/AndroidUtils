package ke.co.basecode.activity

import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import com.google.android.material.snackbar.Snackbar
import com.rengwuxian.materialedittext.MaterialEditText
import ke.co.basecode.R
import ke.co.basecode.api.BaseAPI
import ke.co.basecode.logging.BeeLog
import ke.co.basecode.model.BaseResponseModel
import ke.co.basecode.model.User
import ke.co.basecode.network.BaseCallback
import ke.co.basecode.network.ServiceGenerator
import ke.co.basecode.utils.BaseUtils
import ke.co.basecode.utils.PrefUtils
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Response

class BaseSignInActivity : BaseActivity() {
    private var signInIdMET: MaterialEditText? = null
    private var passwordMET: MaterialEditText? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (!isLoggedIn) {
            setContentView(R.layout.activity_sign_in)
            signInIdMET = findViewById(R.id.signInIdMET)
            passwordMET = findViewById(R.id.passwordMET)
            (findViewById<Button>(R.id.signInBtn)).setOnClickListener { signIn() }
            signInIdMET?.addValidator(BaseUtils.createRequiredValidator(getString(R.string.error_field_required)))
            passwordMET?.addValidator(BaseUtils.createRequiredValidator(getString(R.string.error_field_required)))
            BeeLog.d("William", "PrefUtils ${PrefUtils.instance}")
            BaseUtils.cacheInput(signInIdMET!!, R.string.cache_sign_in_id, PrefUtils.instance!!)
            BaseUtils.cacheInput(passwordMET!!, R.string.cache_password, PrefUtils.instance!!)
            if (supportActionBar != null) {
                supportActionBar?.setDisplayHomeAsUpEnabled(false)
            }
        } else {
            startMainActivity()
        }
    }

    private fun signIn() {
        var errorMessage: String? = null
        when {
            signInIdMET?.validate() == false -> errorMessage = getString(R.string.hint_phone_or_email) + " is required"
            passwordMET?.validate()  == false -> errorMessage = getString(R.string.hint_password) + " is required"
            else -> {
                val userId = signInIdMET?.text?.toString()?.toRequestBody("text/plain".toMediaTypeOrNull())
                val password = passwordMET?.text?.toString()?.toRequestBody("text/plain".toMediaTypeOrNull())
                userId?.let {
                    password?.let { it1 ->
                        ServiceGenerator.createService(BaseAPI::class.java)
                            .signIn(it, it1)
                            .enqueue(object : BaseCallback<BaseResponseModel<User>>(this@BaseSignInActivity, "Signing in...") {
                                override fun onResponse(response: Response<BaseResponseModel<User>>) {
                                    onAuthSuccessful(response.body()?.data!!)
                                }

                                override fun onFailure(message: String) {
                                    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                                }
                            })
                    }
                }
            }
        }

        if (!TextUtils.isEmpty(errorMessage)) {
            Snackbar.make(signInIdMET!!, errorMessage!!, Snackbar.LENGTH_INDEFINITE)
                .setAction(android.R.string.ok) { }.show()
        }
    }

    companion object {

        @Suppress("unused")
        private const val TAG = "BaseSignInActivity"
    }
}

