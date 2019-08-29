package ke.co.basecode.widget

import android.content.Context
import androidx.appcompat.widget.AppCompatAutoCompleteTextView



class CustomAutoCompleteView(context: Context): AppCompatAutoCompleteTextView(context) {
    override fun performFiltering(text: CharSequence?, keyCode: Int) {
        val filterText = ""
        super.performFiltering(filterText, keyCode)
    }
}