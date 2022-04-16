package fragment

import adapter.WatchListAdapter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.cryptoapplication.R
import com.example.cryptoapplication.databinding.ActivityTradeListBinding
import data.Crypto
import helper.CommonHelper

class WatchListFragment:Fragment() {

    private lateinit var mBinding: ActivityTradeListBinding
    private var mWatchList = ArrayList<Crypto>()
    private lateinit var mCommonHelper: CommonHelper
    private lateinit var mWatchListAdapter: WatchListAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mBinding =
            DataBindingUtil.inflate(inflater, R.layout.activity_trade_list, container, false)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        mCommonHelper = CommonHelper(requireActivity())
        val lList = mCommonHelper.jsonToGson()
        mWatchList =  if(lList.isNullOrEmpty()) ArrayList() else lList as ArrayList<Crypto>
        setAdapter()
    }

    private fun setAdapter() {
        mWatchListAdapter = WatchListAdapter()
        mWatchListAdapter.updateList(mWatchList)
        mBinding.quantityPriceLayout.adapter = mWatchListAdapter
    }

}