package ke.co.basecode.william.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ke.co.basecode.R

object LoadingCell : Cell<RecyclerItem>() {
    override fun belongsTo(item: RecyclerItem?): Boolean {
        return item is LoadItem
    }

    override fun type(): Int {
        return R.layout.item_loading_small
    }

    override fun holder(parent: ViewGroup): RecyclerView.ViewHolder {
        return LoadingItemViewHolder(parent.viewOf(type()))
    }

    override fun bind(
        holder: RecyclerView.ViewHolder,
        item: RecyclerItem?,
        listener: AdapterListener?
    ) {
        if (holder is LoadingItemViewHolder && item is LoadItem) {
            //
        }
    }
}