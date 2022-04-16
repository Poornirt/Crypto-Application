package com.example.cryptoapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.cryptoapplication.databinding.ActivityMainBinding
import fragment.MarketFragment
import fragment.WatchListFragment


class MainActivity : AppCompatActivity() {

    private lateinit var mBinding: ActivityMainBinding
    private lateinit var mMarketFragment: MarketFragment
    private lateinit var mWatchListFragment: WatchListFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        mMarketFragment = MarketFragment()
        mWatchListFragment = WatchListFragment()
        setFragmentView(mMarketFragment)
        mBinding.bottomNavigation.setOnItemSelectedListener {
            when(it.itemId){
                R.id.market_icon -> {
                    setFragmentView(mMarketFragment)
                    return@setOnItemSelectedListener true
                }
                R.id.watch_list_icon -> {
                    setFragmentView(mWatchListFragment)
                    return@setOnItemSelectedListener true
                }
            }
            false
        }
    }

    private fun setFragmentView(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment, "").commit()
    }
}