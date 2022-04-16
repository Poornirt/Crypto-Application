package com.example.cryptoapplication

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.cryptoapplication.databinding.ActivitySymbolViewBinding
import com.google.gson.Gson
import com.jjoe64.graphview.series.DataPoint
import com.jjoe64.graphview.series.LineGraphSeries
import constants.Constants
import data.Crypto
import factory.CryptoViewModelFactory
import helper.CommonHelper
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import service.ServiceProvider
import viewmodel.CryptoViewModel


enum class PRICE {
    HIGH_PRICE, LOW_PRICE, CURRENT_PRICE
}

class SymbolViewActivity : AppCompatActivity() {

    private lateinit var mCryptoViewModel: CryptoViewModel
    private lateinit var mCryptoViewModelFactory: CryptoViewModelFactory
    private lateinit var mBinding: ActivitySymbolViewBinding
    private lateinit var mSelectedPrice: PRICE
    private var mCount = 0
    private var mAddedToWatchList = false
    private lateinit var mSharedPreferences:SharedPreferences
    private lateinit var mSharedPreferenceEditor:SharedPreferences.Editor
    private var mWatchList = ArrayList<Crypto>()
    private lateinit var mCommonHelper:CommonHelper


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView<ActivitySymbolViewBinding>(
            this,
            R.layout.activity_symbol_view
        )
        mSharedPreferences = getSharedPreferences("sharedpreference", MODE_PRIVATE)
        mSharedPreferenceEditor = mSharedPreferences.edit()
        mCommonHelper = CommonHelper(this)
        val lList = mCommonHelper.jsonToGson()
        mWatchList =  if(lList.isNullOrEmpty()) ArrayList() else lList as ArrayList<Crypto>
        mBinding.price = PRICE.CURRENT_PRICE.name
        mSelectedPrice = PRICE.CURRENT_PRICE
        val lApi = ServiceProvider.getInstance().getApi()
        mCryptoViewModelFactory = CryptoViewModelFactory(lApi, application)
        mCryptoViewModel =
            ViewModelProvider(this, mCryptoViewModelFactory)[CryptoViewModel::class.java]
        val lSymbol = getBundleData()
        getIndividualSymbol(lSymbol)
        buyPriceTitleChange()
        mBinding.swipeRefreshLayout.setOnRefreshListener {
            getIndividualSymbol(lSymbol)
        }
        mBinding.bidsAndAsks.setOnClickListener {
            val lIntent = Intent(this, BidsAndAsksActivity::class.java)
            val lBundle = Bundle()
            lBundle.putString(Constants.CRYPTO, lSymbol)
            lIntent.putExtra(Constants.BUNDLE, lBundle)
            startActivity(lIntent)
        }
        mBinding.tradeList.setOnClickListener {
            val lIntent = Intent(this, TradeListActivity::class.java)
            val lBundle = Bundle()
            lBundle.putString(Constants.CRYPTO, lSymbol)
            lIntent.putExtra(Constants.BUNDLE, lBundle)
            startActivity(lIntent)
        }

        mBinding.addToWatchlist.setOnClickListener {
            addToWatchlist()
        }
    }

    private fun addToWatchlist() {
        if (!mAddedToWatchList) {
            mAddedToWatchList = true
            mBinding.addToWatchlist.setImageDrawable(
                resources.getDrawable(
                    R.drawable.ic_star_selected,
                    null
                )
            )
            mWatchList.add(mBinding.crypto!!)
            mCommonHelper.gsonToJson(mWatchList)
        } else {
            mAddedToWatchList = false
            mBinding.addToWatchlist.setImageDrawable(
                resources.getDrawable(
                    R.drawable.ic_star_unselected,
                    null
                )
            )
            mWatchList.remove(mBinding.crypto!!)
            mCommonHelper.gsonToJson(mWatchList)
        }
    }

    private fun buyPriceTitleChange() {
        mBinding.buyPrice.setOnClickListener {
            when {
                mCount == 0 -> {
                    mBinding.price = PRICE.HIGH_PRICE.name
                    mCount = 1
                }
                mCount > 0 -> {
                    mBinding.price = PRICE.LOW_PRICE.name
                    mCount = -1
                }
                mCount < 0 -> {
                    mBinding.price = PRICE.CURRENT_PRICE.name
                    mCount = 0
                }
            }
        }
    }

    private fun getIndividualSymbol(lSymbol: String) {
        if (lSymbol.isNotEmpty()) {
            lifecycleScope.launch {
                mCryptoViewModel.fetchIndividualSymbolStat(lSymbol)
                    .enqueue(object : Callback<Crypto> {
                        override fun onResponse(call: Call<Crypto>, response: Response<Crypto>) {
                            if (response.isSuccessful) {
                                mBinding.swipeRefreshLayout.setRefreshing(false);
                                Log.d("SymbolViewActivity", response.body().toString())
                                mBinding.crypto = response.body()
                                generateGraph()
                            }
                        }

                        override fun onFailure(call: Call<Crypto>, t: Throwable) {
                            mBinding.swipeRefreshLayout.setRefreshing(false);
                            Toast.makeText(
                                this@SymbolViewActivity,
                                "Request Failed,Please tyr again",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    })
            }
        }
    }

    private fun getBundleData(): String {
        val lBundle = intent.getBundleExtra(Constants.BUNDLE)
        var lSymbol = ""
        lBundle?.let {
            lSymbol = lBundle.getString(Constants.CRYPTO).toString()
        }
        return lSymbol
    }

    private fun generateGraph() {
        mBinding.graphView.removeAllSeries()
        val series = LineGraphSeries(
            arrayOf(
                DataPoint(0.toDouble(), mBinding.crypto?.lowPrice?.toDouble()!!),
                DataPoint(5.toDouble(), mBinding.crypto?.highPrice?.toDouble()!!),
                DataPoint(10.toDouble(), mBinding.crypto?.openPrice?.toDouble()!!),
                DataPoint(20.toDouble(), mBinding.crypto?.lastPrice?.toDouble()!!)
            )
        )
        mBinding.graphView.addSeries(series)
//        val staticLabelsFormatter = StaticLabelsFormatter(mBinding.graphView)
//        staticLabelsFormatter.setHorizontalLabels(
//            arrayOf(
//                mBinding.crypto?.lowPrice,
//                mBinding.crypto?.highPrice,
//                mBinding.crypto?.openPrice,
//                mBinding.crypto?.lastPrice
//            )
//        )
//        mBinding.graphView.gridLabelRenderer.labelFormatter = staticLabelsFormatter
//        mBinding.graphView.gridLabelRenderer.setHorizontalLabelsAngle(180)
//        mBinding.graphView.gridLabelRenderer.numHorizontalLabels = 4
//        mBinding.graphView.viewport.isXAxisBoundsManual = true
        mBinding.graphView.gridLabelRenderer.isVerticalLabelsVisible = false
        series.isDrawDataPoints = true
        series.dataPointsRadius = 15f
        series.setOnDataPointTapListener { series, dataPoint ->
            Toast.makeText(this, "Price: ${dataPoint.y}", Toast.LENGTH_SHORT).show();
        }
    }
}