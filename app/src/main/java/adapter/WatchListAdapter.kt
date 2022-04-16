package adapter

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.cryptoapplication.R
import com.example.cryptoapplication.databinding.MarketStatRowBinding
import data.Crypto
import java.security.AccessController.getContext


class WatchListAdapter : RecyclerView.Adapter<WatchListAdapter.ViewHolder>() {

    private var mList = ArrayList<Crypto>()
    private lateinit var mContext:Context

    inner class ViewHolder(var binding: MarketStatRowBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("UseCompatLoadingForDrawables")
        fun bind(pCrypto: Crypto) {
            with(binding) {
                crypto = pCrypto
                moreIcon.setOnClickListener {
                    if (moreLayoutView.root.isVisible) {
                        moreLayoutView.root.visibility = View.GONE
                        divider.visibility = View.GONE
                    }
                    else {
                        moreLayoutView.root.visibility = View.VISIBLE
                        divider.visibility = View.VISIBLE
                    }
                }
            }
        }
    }

    fun updateList(pList: ArrayList<Crypto>) {
        mList = pList
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = DataBindingUtil.inflate<MarketStatRowBinding>(
            LayoutInflater.from(parent.context),
            R.layout.market_stat_row,
            parent,
            false
        )
        mContext = parent.context
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val lCrypto = mList[position]
        holder.bind(lCrypto)
    }

    override fun getItemCount(): Int {
        return mList.size
    }


}