package adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.cryptoapplication.R
import com.example.cryptoapplication.databinding.MarketStatRowBinding
import data.Crypto

class MarketStatsPagingAdapter(var mListener: (Crypto) -> Unit) :
    PagingDataAdapter<Crypto, MarketStatsPagingAdapter.ViewHolder>(DiffUtilCallback) {


    inner class ViewHolder(var binding: MarketStatRowBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(pCrypto: Crypto) {
            with(binding) {
                crypto = pCrypto
                moreIcon.setOnClickListener {
                    if (moreLayoutView.root.isVisible) {
                        moreLayoutView.root.visibility = View.GONE
                        divider.visibility = View.GONE
                    } else {
                        moreLayoutView.root.visibility = View.VISIBLE
                        divider.visibility = View.VISIBLE
                    }
                }
                symbolCardView.setOnClickListener {
                    mListener.invoke(pCrypto)
                }
            }
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        getItem(position).let {
            it?.let { crypto -> holder.bind(crypto) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = DataBindingUtil.inflate<MarketStatRowBinding>(
            LayoutInflater.from(parent.context),
            R.layout.market_stat_row,
            parent,
            false
        )
        return ViewHolder(binding)
    }


    object DiffUtilCallback : DiffUtil.ItemCallback<Crypto>() {
        override fun areItemsTheSame(oldItem: Crypto, newItem: Crypto): Boolean {
            return oldItem.symbol == newItem.symbol
        }

        override fun areContentsTheSame(oldItem: Crypto, newItem: Crypto): Boolean {
            return oldItem == newItem
        }

    }

}