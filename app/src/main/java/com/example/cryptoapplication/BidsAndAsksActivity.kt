package com.example.cryptoapplication

import adapter.BidsAsksPagerAdapter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.cryptoapplication.databinding.ActivityBidsAndAsksBinding
import com.google.android.material.tabs.TabLayout
import constants.Constants
import data.BidsAsks
import factory.CryptoViewModelFactory
import fragment.AsksFragment
import fragment.BidsAsksFragment
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import service.ServiceProvider
import viewmodel.CryptoViewModel

class BidsAndAsksActivity : AppCompatActivity() {

    private lateinit var mCryptoViewModel: CryptoViewModel
    private lateinit var mCryptoViewModelFactory: CryptoViewModelFactory
    private lateinit var mBinding: ActivityBidsAndAsksBinding
    private var mPagerAdapter: BidsAsksPagerAdapter? = null
    private var mFragments = ArrayList<HashMap<String, Any>>()
    private lateinit var mBidsFragment: Fragment
    private lateinit var mAsksFragment: Fragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView<ActivityBidsAndAsksBinding>(
            this,
            R.layout.activity_bids_and_asks
        )
        val lSymbol = getBundleData()
        val lApi = ServiceProvider.getInstance().getApi()
        mCryptoViewModelFactory = CryptoViewModelFactory(lApi, application)
        mCryptoViewModel =
            ViewModelProvider(this, mCryptoViewModelFactory)[CryptoViewModel::class.java]

        mBinding.tabLayout.addTab(mBinding.tabLayout.newTab().setText("Bids"))
        mBinding.tabLayout.addTab(mBinding.tabLayout.newTab().setText("Asks"))
        mBinding.tabLayout.tabGravity = TabLayout.GRAVITY_FILL

        lifecycleScope.launch {
            mCryptoViewModel.fetchAsksAndBids(lSymbol).enqueue(object : Callback<BidsAsks> {
                override fun onResponse(call: Call<BidsAsks>, response: Response<BidsAsks>) {
                    if (response.isSuccessful) {
                        val lHashMap = HashMap<String, Any>()
                        mBidsFragment = BidsAsksFragment(response.body()?.bids!!)
                        mAsksFragment = BidsAsksFragment(response.body()?.asks!!)

                        lHashMap["fragment"] = mBidsFragment
                        lHashMap["title"] = "Bids"
                        mFragments.add(lHashMap)

                        val lHashMap1 = HashMap<String, Any>()

                        lHashMap1["fragment"] = mAsksFragment
                        lHashMap1["title"] = "Asks"
                        mFragments.add(lHashMap1)
                        setPagerAdapter()

                        Log.d("BidsAndAsksActivity", response.body().toString())
                    }
                }

                override fun onFailure(call: Call<BidsAsks>, t: Throwable) {
                    Toast.makeText(
                        this@BidsAndAsksActivity,
                        "Request Failed,Please tyr again",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            })
        }
    }

    private fun setPagerAdapter() {
        mPagerAdapter = BidsAsksPagerAdapter(supportFragmentManager, mFragments)
        mBinding.viewPager.adapter = mPagerAdapter
        mBinding.viewPager.offscreenPageLimit = 2
        mBinding.viewPager.currentItem = 0
        mBinding.tabLayout.setupWithViewPager(mBinding.viewPager)
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