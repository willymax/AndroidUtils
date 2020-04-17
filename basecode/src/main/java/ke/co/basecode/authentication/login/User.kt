package ke.co.basecode.authentication.login

import android.os.Parcelable
import androidx.annotation.Keep
import androidx.recyclerview.widget.DiffUtil
import kotlinx.android.parcel.Parcelize


//@Entity(tableName = "users")
@Parcelize
@Keep
data class User(
    //@PrimaryKey @ColumnInfo(name = "id") var id: Long,
    val id: Long,
    val avatar: String,
    val name: String,
    val email: String,
    val phone: String,
    val token: String?,
    val department: Department?,
    val lastKnownLocation: String,
    val role: Role,
    val client: Client
) : Parcelable {
    companion object {
        val DIFF_UTIL_ITEM_CALLBACK = object : DiffUtil.ItemCallback<User>() {

            override fun areItemsTheSame(oldItem: User, newItem: User): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: User, newItem: User): Boolean {
                return oldItem == newItem
            }

        }
    }

    override fun toString(): String {
        return "User(id=$id, name='$name')"
    }

}