package service


import androidx.lifecycle.LiveData
import data.BidsAsks
import data.Crypto
import data.Trade
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.QueryMap


interface CryptoMarketService {

    @GET("sapi/v1/tickers/24hr")
    suspend fun fetchMarketStatistics(): List<Crypto>


    @GET("sapi/v1/ticker/24hr")
    fun fetchIndividualSymbolView(@QueryMap pQueryMap: HashMap<String,String>): Call<Crypto>


    @GET("sapi/v1/depth")
    fun fetchBidsAndAsks(@QueryMap pQueryMap: HashMap<String,String>): Call<BidsAsks>


    @GET("sapi/v1/trades")
    fun fetchTradeLists(@QueryMap pQueryMap: HashMap<String,String>): Call<List<Trade>>




    
}