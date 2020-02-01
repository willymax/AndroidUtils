package ke.co.basecode.william.adapter

import androidx.annotation.Keep
import kotlinx.android.parcel.Parcelize

@Parcelize
@Keep
class LoadItem: RecyclerItem, AdapterClick {

    override val id: Long
        get() = 1

    override fun equals(other: Any?): Boolean {
        if (other != null && other is LoadItem) {
            if (id == other.id) {
                return true
            }
        }
        return false
    }

}