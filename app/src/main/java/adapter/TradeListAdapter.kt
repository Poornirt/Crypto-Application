package adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.cryptoapplication.R
import com.example.cryptoapplication.databinding.TradeListRowBinding
import data.Trade

class TradeListAdapter : RecyclerView.Adapter<TradeListAdapter.ViewHolder>() {

    var mList = ArrayList<Trade>()

    inner class ViewHolder(var binding: TradeListRowBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(pTrade: Trade) {
            with(binding) {
                trade = pTrade
            }
        }
    }

    fun updateList(pList:ArrayList<Trade>) {
        mList = pList
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = DataBindingUtil.inflate<TradeListRowBinding>(
            LayoutInflater.from(parent.context),
            R.layout.trade_list_row,
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val lTrade = mList[position]
        holder.bind(lTrade)
    }

    override fun getItemCount(): Int {
        return mList.size
    }


}