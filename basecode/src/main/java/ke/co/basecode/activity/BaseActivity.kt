package ke.co.basecode.activity

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.text.TextUtils
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import ke.co.basecode.R
import ke.co.basecode.logging.BeeLog
import ke.co.basecode.model.User
import ke.co.basecode.tasks.SignOutTask
import ke.co.basecode.utils.BaseProgressDialog
import ke.co.basecode.utils.BaseUtils
import ke.co.basecode.utils.PrefUtils
import ke.co.basecode.utils.Utils

open class BaseActivity : AppCompatActivity() {
    private val TAG = "BaseActivity"

    private var sessionDepth = 0


    protected var mProgressDialog: BaseProgressDialog? = null
    private var toolbar: Toolbar? = null


    protected fun getSessionDepth(): Int {
        return sessionDepth
    }

    override fun onStart() {
        super.onStart()
        sessionDepth++
    }

    override fun onPause() {
        try {
            if (mProgressDialog != null && mProgressDialog?.isShowing == true) {
                hideProgressDialog()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

        super.onPause()
    }

    override fun onStop() {
        super.onStop()
        if (sessionDepth > 0) {
            sessionDepth--
        }
    }

    override fun setContentView(@LayoutRes layoutResID: Int) {
        super.setContentView(layoutResID)
        setupToolbar()
        setUpTitleFromLabel()
    }

    private fun setUpTitleFromLabel() {
        var label: String? = null
        try {
            label = resources.getString(
                packageManager.getActivityInfo(componentName, 0).labelRes
            )
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }

        Log.d(TAG, "Activity Label: $label")
        if (getToolbar() != null && !TextUtils.isEmpty(label)) {
            getToolbar()?.title = label
        }
    }
    fun startFragment(container: Int, theFragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction().apply {
            // Replace whatever is in the fragment_container view with this fragment,
            // and add the transaction to the back stack so the user can navigate back
            replace(container, theFragment)
            //addToBackStack(null)
        }
        // Commit the transaction
        transaction.commit()
    }

    override fun startActivity(intent: Intent) {
        super.startActivity(intent)
        overridePendingTransition(getStartEnterAnim(), getStartExitAnim())
    }

    fun startNewTaskActivity(intent: Intent) {
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
    }

    fun getThis(): AppCompatActivity {
        return this
    }

    protected fun setupToolbar() {
        toolbar = findViewById(R.id.toolbar)
        if (toolbar != null) {
            setSupportActionBar(toolbar)
            val actionBar = supportActionBar
            // actionBar?.setDisplayHomeAsUpEnabled(true)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        BaseUtils.tintMenu(this, menu, Color.WHITE)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return if (item.itemId == android.R.id.home) {
            onBackPressed()
            true
        } else {
            super.onOptionsItemSelected(item)
        }
    }

    fun hideKeyboardFrom(view: View) {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }

    fun getToolbar(): Toolbar? {
        return toolbar
    }

    fun showProgressDialog() {
        showProgressDialog(getString(R.string.message_waiting))
    }

    fun showProgressDialog(message: String) {
        if (mProgressDialog == null) {
            mProgressDialog = BaseProgressDialog(this)
                .setMessage(message)
                .setCancelable(false)
        }
        mProgressDialog?.show()
    }

    fun hideProgressDialog() {
        if (mProgressDialog != null && mProgressDialog?.isShowing == true) {
            mProgressDialog?.dismiss()
            mProgressDialog = null
        }
    }

    fun updateProgressDialogMessage(newMessage: String) {
        if (mProgressDialog != null && mProgressDialog?.isShowing == true) {
            mProgressDialog?.setMessage(newMessage)
        }
    }

    fun toast(message: Any) {
        try {
            Toast.makeText(this, message.toString(), Toast.LENGTH_SHORT).show()
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    fun toastDebug(msg: Any) {
        if (BeeLog.DEBUG) {
            toast(msg)
        }
    }

    fun toast(@StringRes string: Int) {
        toast(getString(string))
    }

    override fun onBackPressed() {
        super.onBackPressed()
        if (sessionDepth > 1) {
            overridePendingTransition(getLeaveEnterAnim(), getLeaveExitAnim())
        }
    }

    protected fun getStartEnterAnim(): Int {
        return R.anim.slide_left_in
    }

    protected fun getStartExitAnim(): Int {
        return R.anim.hold
    }

    protected fun getLeaveEnterAnim(): Int {
        return R.anim.hold
    }

    protected fun getLeaveExitAnim(): Int {
        return R.anim.slide_right_out
    }
    val isLoggedIn: Boolean
        get() = user != null

    protected val user: User?
        get() = PrefUtils.instance?.getUser()

    fun onAuthSuccessful(user: User?) {
        PrefUtils.instance?.saveUser(user)
        startMainActivity()
        // toast(meta!!.message)
        this.finish()
    }

    open fun signOut() {
        SignOutTask(object : SignOutTask.TaskListener {
            override val activity: Activity
                get() = this@BaseActivity
            override fun onComplete() {
                startNewTaskActivity(Intent(this@BaseActivity, BaseSignInActivity::class.java))
                this@BaseActivity.finish()
            }
        }).execute()
    }

    fun startMainActivity() {
        try {
            val ai = packageManager
                .getApplicationInfo(packageName, PackageManager.GET_META_DATA)
            val bundle = ai.metaData
            val mainActivityCategory = bundle.getString(Utils.META_NAME_MAIN_ACTIVITY_CATEGORY)
            startNewTaskActivity(Intent(Utils.ACTION_MAIN_ACTIVITY).addCategory(mainActivityCategory))
        } catch (e: PackageManager.NameNotFoundException) {
            throw IllegalArgumentException("Failed to load meta-data " + Utils.META_NAME_MAIN_ACTIVITY_CATEGORY)
        } catch (e: NullPointerException) {
            throw IllegalArgumentException("Failed to load meta-data " + Utils.META_NAME_MAIN_ACTIVITY_CATEGORY)
        }

    }

    companion object {
        private val TAG = BaseActivity::class.java.simpleName
    }
}
