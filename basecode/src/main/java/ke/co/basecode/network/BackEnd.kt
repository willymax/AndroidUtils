package ke.co.basecode.network


/**
 * Created by William Makau on 18/06/2019.
 * Company: Think Synergy Limited
 * Email: williammakau070@gmail.com
 * Phone: +254706356815
 * Purpose:
 */
class BackEnd {
    companion object {
        const val BASE_URL = "https://2133601f.ngrok.io/citylyf_1/public/api/"
        //const val BASE_URL = "http://citylyf.programminghelpers.com/public/api/"
        // const val BASE_STORAGE_URL = "http://35.184.187.70/storage/"
        const val BASE_STORAGE_URL = "http://127.0.0.1:8000/storage/app/public/"

        fun imageUrl(path: String): String {
            return BASE_STORAGE_URL + path
        }
    }

    class Params {
        companion object {
            const val SIGN_IN_ID = "username"
            const val ID_TOKEN = "idToken"
            const val PASSWORD = "password"
            const val FILTER = "filter"
            const val NAME = "name"
            const val EMAIL = "email"
            const val PHONE = "phone"
            const val OLD_PASSWORD = "old_password"
            const val NEW_PASSWORD = "new_password"
            const val NEW_PASSWORD_CONFIRMATION = "new_password_confirmation"
            const val PLACES_KEY = "AIzaSyBh3_7IMfwxQt9s_gAqURHnZtY8sLmVgXs"
            const val BUSINESS_NAME = "businessName"
            const val COUNTRY_ID = "countryId"
            const val CATEGORY_ID = "categoryId"
            const val ADDRESS_ONE = "addressOne"
            const val ADDRESS_TWO = "addressTwo"
            const val CITY = "city"
            const val STATE = "state"
            const val ZIP = "zip"
            const val LOCATION_NAME = "locationName"
            const val LATITUDE = "latitude"
            const val LONGITUDE = "longitude"
            const val GOOGLE_ADDRESS = "googleAddress"
            const val WEB_ADDRESS = "webAddress"
            const val COVER_PHOTO = "coverPhoto"
            const val DESCRIPTION = "description"
            const val HOURS = "hours"
        }
    }

    class EndPoints {
        companion object {
            const val DEVICE_TOKEN = "/deviceToken"
            const val SIGN_IN = "login"
            const val GOOGLE_SIGN_IN = "googleSignIn"
            const val FACEBOOK_SIGN_IN = "facebookSignIn"
            const val REGISTER = "register"
            const val PASSWORD_RESET_EMAIL = "password/reset/email"
            const val PASSWORD_RESET = "password/reset"
            const val CHANGE_PASSWORD = "change/password"
            const val BUSINESSES = "businesses"
            const val MY_BUSINESSES = "my/business"
            const val TOKEN = "/token"
        }
    }
}