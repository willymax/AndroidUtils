package ke.co.basecode.authentication.login

import android.os.Parcelable
import androidx.annotation.Keep
import kotlinx.android.parcel.Parcelize

/**
 * Created by Willy on 26/02/2020
 * Email williammakau070@gmail.com
 * @author willi
 */

//user types/roles
const val DEPARTMENT_HEAD = "DEPARTMENT_HEAD"
const val ADMIN = "ADMIN"
const val PURCHASING_HEAD = "PURCHASING_HEAD"
const val CLIENT_ADMIN = "CLIENT_ADMIN"
const val CLIENT_ADMIN_AND_PURCHASING_HEAD = "CLIENT_ADMIN_AND_PURCHASING_HEAD"
const val DEPARTMENT_USER = "DEPARTMENT_USER"
const val RIDER = "RIDER"

@Parcelize
@Keep
data class Role(
    val id: Long,
    val name: String,
    val displayName: String
): Parcelable {
    fun isDepartmentHead() = name == DEPARTMENT_HEAD
    fun isAdmin() = name == ADMIN
    fun isPurchasingHead() = name == PURCHASING_HEAD
    fun isClientAdmin() = name == CLIENT_ADMIN
    fun isClientAdminAndPurchasingHead() = name == CLIENT_ADMIN_AND_PURCHASING_HEAD
    fun isDepartmentUser() = name == DEPARTMENT_USER
    fun isRider() = name == DEPARTMENT_USER
}