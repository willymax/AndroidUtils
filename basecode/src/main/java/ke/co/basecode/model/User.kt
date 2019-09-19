package ke.co.basecode.model


/**
 * Created by William Makau on 18/06/2019.
 * Company: Think Synergy Limited
 * Email: williammakau070@gmail.com
 * Phone: +254706356815
 * Purpose:
 */
class User(var id: Long,
           var name: String,
           var emailVerifiedAt: String?,
           var phoneVerifiedAt: String?,
           var email: String,
           var avatar: String?,
           var token: String?,
           var phone: String?,
           var role: Role?,
           var updatedAt: String?,
           var createdAt: String?
) {
    override fun toString(): String {
        return "Name: $name token: $token"
    }
}
