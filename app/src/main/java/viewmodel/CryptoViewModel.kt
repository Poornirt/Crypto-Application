package viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import data.BidsAsks
import data.Crypto
import data.Trade
import repository.MarketStatRepo
import retrofit2.Call
import service.CryptoMarketService

class CryptoViewModel(mApi: CryptoMarketService, application: Application) :
    AndroidViewModel(application) {

    private var mMarketStatRepo = MarketStatRepo(mApi)

    fun fetchMarketStats(): LiveData<PagingData<Crypto>> {
        return mMarketStatRepo.fetchMarketStats().cachedIn(viewModelScope)
    }

    fun fetchIndividualSymbolStat(pSymbol:String): Call<Crypto> {
        return mMarketStatRepo.fetchIndividualStat(pSymbol)
    }

    fun fetchAsksAndBids(pSymbol:String): Call<BidsAsks> {
        return mMarketStatRepo.fetchAsksAndBids(pSymbol)
    }

    fun fetchTradeLists(pSymbol:String): Call<List<Trade>> {
        return mMarketStatRepo.fetchTradeLists(pSymbol)
    }

}