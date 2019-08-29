package ke.co.basecode.utils

import com.rengwuxian.materialedittext.validation.METValidator


/**
 * Created by William Makau on 18/06/2019.
 * Company: Think Synergy Limited
 * Email: williammakau070@gmail.com
 * Phone: +254706356815
 * Purpose:
 */
class PhoneLengthValidator(errorMessage: String, private val maxLength: Int) : METValidator(errorMessage) {

    override fun isValid(text: CharSequence, isEmpty: Boolean): Boolean {
        return text.length != maxLength
    }
}