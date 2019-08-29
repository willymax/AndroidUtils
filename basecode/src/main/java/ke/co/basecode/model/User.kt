package ke.co.basecode.model


/**
 * Created by William Makau on 18/06/2019.
 * Company: Think Synergy Limited
 * Email: williammakau070@gmail.com
 * Phone: +254706356815
 * Purpose:
 */
class User(val id: Long,
           val name: String,
           val emailVerifiedAt: String?,
           val phoneVerifiedAt: String?,
           val email: String,
           val avatar: String?,
           val token: String?,
           val phone: String?,
           val role: Role?,
           val updatedAt: String?,
           val createdAt: String?
) {
    override fun toString(): String {
        return "Name: $name token: $token"
    }
}
