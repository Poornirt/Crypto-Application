package com.example.cryptoapplication

import adapter.BidsAsksPagerAdapter
import adapter.TradeListAdapter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.cryptoapplication.databinding.ActivityBidsAndAsksBinding
import com.example.cryptoapplication.databinding.ActivityTradeListBinding
import constants.Constants
import data.Trade
import factory.CryptoViewModelFactory
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import service.ServiceProvider
import viewmodel.CryptoViewModel

class TradeListActivity : AppCompatActivity() {

    private lateinit var mCryptoViewModel: CryptoViewModel
    private lateinit var mCryptoViewModelFactory: CryptoViewModelFactory
    private lateinit var mBinding: ActivityTradeListBinding
    private lateinit var mTradeListAdapter: TradeListAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView<ActivityTradeListBinding>(
            this,
            R.layout.activity_trade_list
        )
        val lSymbol = getBundleData()
        val lApi = ServiceProvider.getInstance().getApi()
        mCryptoViewModelFactory = CryptoViewModelFactory(lApi, application)
        mCryptoViewModel =
            ViewModelProvider(this, mCryptoViewModelFactory)[CryptoViewModel::class.java]
        fetchTradeList(lSymbol)
    }

    private fun fetchTradeList(lSymbol: String) {
        lifecycleScope.launch {
            mCryptoViewModel.fetchTradeLists(lSymbol).enqueue(object : Callback<List<Trade>> {
                override fun onResponse(call: Call<List<Trade>>, response: Response<List<Trade>>) {
                    if (response.isSuccessful) {
                        val lTradeList = response.body()
                        setTradeAdapter(lTradeList as ArrayList<Trade>)
                    }
                }

                override fun onFailure(call: Call<List<Trade>>, t: Throwable) {
                    Toast.makeText(
                        this@TradeListActivity,
                        "Request Failed,Please tyr again",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            })
        }
    }

    private fun setTradeAdapter(pList: ArrayList<Trade>) {
        mTradeListAdapter = TradeListAdapter()
        mTradeListAdapter.updateList(pList)
        mBinding.quantityPriceLayout.adapter = mTradeListAdapter
    }

    private fun getBundleData(): String {
        val lBundle = intent.getBundleExtra(Constants.BUNDLE)
        var lSymbol = ""
        lBundle?.let {
            lSymbol = lBundle.getString(Constants.CRYPTO).toString()
        }
        return lSymbol
    }

}