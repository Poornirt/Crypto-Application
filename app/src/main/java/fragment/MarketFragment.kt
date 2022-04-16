package fragment

import adapter.MarketStatsPagingAdapter
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.cryptoapplication.R
import com.example.cryptoapplication.SymbolViewActivity
import com.example.cryptoapplication.databinding.FragmentMarketBinding
import constants.Constants
import factory.CryptoViewModelFactory
import kotlinx.coroutines.launch
import service.ServiceProvider
import viewmodel.CryptoViewModel

class MarketFragment : Fragment() {

    private lateinit var mFragmentMarketBinding: FragmentMarketBinding
    private lateinit var mCryptoViewModel: CryptoViewModel
    private lateinit var mCryptoViewModelFactory: CryptoViewModelFactory
    private lateinit var mMarketStatsPagingAdapter: MarketStatsPagingAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mFragmentMarketBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_market, container, false)
        mFragmentMarketBinding.swipeRefreshLayout.setOnRefreshListener {
            lifecycleScope.launch {
                fetchMarketStats()
            }
        }
        return mFragmentMarketBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val lApi = ServiceProvider.getInstance().getApi()
        mCryptoViewModelFactory = CryptoViewModelFactory(lApi, requireActivity().application)
        mCryptoViewModel =
            ViewModelProvider(this, mCryptoViewModelFactory)[CryptoViewModel::class.java]
        initAdapter()
        lifecycleScope.launch {
            fetchMarketStats()
        }

    }

    private suspend fun fetchMarketStats() {
        mCryptoViewModel.fetchMarketStats().observe(viewLifecycleOwner) {
            if (it != null)
                mMarketStatsPagingAdapter.submitData(lifecycle, it)
            mFragmentMarketBinding.swipeRefreshLayout.setRefreshing(false);
            Log.d("MarketFragment", "size is ${mMarketStatsPagingAdapter.itemCount}")
        }
    }

    private fun initAdapter() {
        mMarketStatsPagingAdapter = MarketStatsPagingAdapter { crypto ->
            val lIntent = Intent(activity,SymbolViewActivity::class.java)
            val lBundle = Bundle()
            lBundle.putString(Constants.CRYPTO,crypto.symbol)
            lIntent.putExtra(Constants.BUNDLE,lBundle)
            startActivity(lIntent)
        }
        mFragmentMarketBinding.marketStatsRv.adapter = mMarketStatsPagingAdapter
    }


}