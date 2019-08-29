package ke.co.basecode.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ke.co.basecode.R
import ke.co.basecode.listeners.RetryButtonClickListener
import ke.co.basecode.network.NetworkState

class NetworkStateItemViewHolder(itemView: View, listener: RetryButtonClickListener) :
    RecyclerView.ViewHolder(itemView) {
    private val progressBar: ProgressBar = itemView.findViewById(R.id.progress_bar)
    private val retry: Button = itemView.findViewById(R.id.retry_button)
    private val errorMsg: TextView = itemView.findViewById(R.id.error_msg)

    init {
        retry.setOnClickListener { listener.retry() }
    }

    fun bindView(networkState: NetworkState) {
        if (networkState.status == NetworkState.Status.RUNNING) {
            progressBar.visibility = View.VISIBLE
        } else {
            progressBar.visibility = View.GONE
        }
        if (networkState.status == NetworkState.Status.FAILED) {
            retry.visibility = View.VISIBLE
        } else {
            retry.visibility = View.GONE
        }
        errorMsg.text = networkState.msg
        errorMsg.visibility = View.VISIBLE
    }
    companion object {
        fun create(parent: ViewGroup, retryListener : RetryButtonClickListener): NetworkStateItemViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.network_state_item, parent, false)
            return NetworkStateItemViewHolder(view, object : RetryButtonClickListener {
                override fun retry() {
                    retryListener.retry()
                }
            })
        }
    }
}
