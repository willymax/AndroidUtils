package ke.co.basecode.authentication.login

import android.os.Parcelable
import androidx.annotation.Keep
import kotlinx.android.parcel.Parcelize

/**
 * Created by Willy on 10/03/2020
 * Email williammakau070@gmail.com
 * @author willi
 */
@Parcelize
@Keep
data class Department(
    val id: Long,
    val name: String
) : Parcelable