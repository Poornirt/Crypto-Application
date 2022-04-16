package repository

import androidx.lifecycle.LiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import data.BidsAsks
import data.Crypto
import data.Trade
import pagingsource.MarketStatsPagingSource
import retrofit2.Call
import service.CryptoMarketService
import service.MarketStatsService

class MarketStatRepo(var mApi: CryptoMarketService) {


//    companion object {
//        private var mInstance: MarketStatRepo? = null
//        fun getInstance(): MarketStatRepo {
//            synchronized(this) {
//                if (mInstance == null)
//                    mInstance = MarketStatRepo(mApi)
//                return mInstance as MarketStatRepo
//            }
//        }
//    }

    fun fetchIndividualStat(pSymbol:String): Call<Crypto> {
        val lHashMap = HashMap<String,String>()
        lHashMap["symbol"] = pSymbol
        return mApi.fetchIndividualSymbolView(lHashMap)
    }

    fun fetchAsksAndBids(pSymbol:String): Call<BidsAsks> {
        val lHashMap = HashMap<String,String>()
        lHashMap["symbol"] = pSymbol
        lHashMap["limit"] = "50"
        return mApi.fetchBidsAndAsks(lHashMap)
    }

    fun fetchTradeLists(pSymbol:String): Call<List<Trade>> {
        val lHashMap = HashMap<String,String>()
        lHashMap["symbol"] = pSymbol
        lHashMap["limit"] = "50"
        return mApi.fetchTradeLists(lHashMap)
    }


    fun fetchMarketStats(): LiveData<PagingData<Crypto>> {
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                prefetchDistance = 20,
                enablePlaceholders = false
            ), pagingSourceFactory = { MarketStatsPagingSource(mApi) }
        ).liveData
    }
}