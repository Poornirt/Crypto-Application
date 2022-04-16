package fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.cryptoapplication.R
import com.example.cryptoapplication.databinding.BidsAsksListLayoutBinding
import com.example.cryptoapplication.databinding.BidsRowLayoutBinding
import kotlinx.coroutines.launch


class BidsAsksFragment(var mList:ArrayList<ArrayList<String>>):Fragment() {

    private lateinit var mBidsRowLayoutBinding: BidsAsksListLayoutBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mBidsRowLayoutBinding =
            DataBindingUtil.inflate(inflater, R.layout.bids_asks_list_layout, container, false)
        return mBidsRowLayoutBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val quantityPriceList = ArrayList<String>()
        mList.forEach { it ->
            quantityPriceList.add("${it[0]}        ${it[1]}")
        }
        val adapter = ArrayAdapter(requireActivity(),R.layout.bids_row_layout,R.id.quantityPrice,quantityPriceList)
        mBidsRowLayoutBinding.quantityPriceLayout.adapter = adapter
    }


}