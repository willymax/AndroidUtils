package ke.co.basecode.william.adapter

import android.view.View

interface AdapterListener {
    fun listen(clickItem: AdapterClick, position: Int, view: View)
}

interface AdapterClick