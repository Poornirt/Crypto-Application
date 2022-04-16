package retorfitbuilder

import constants.Constants
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import service.CryptoMarketService

object RetrofitBuilder {

    var retrofit = Retrofit.Builder().addConverterFactory(GsonConverterFactory.create())
        .baseUrl(Constants.BASE_URL).build()


    fun fetchMarketStats(): CryptoMarketService {
        return retrofit.create(CryptoMarketService::class.java)
    }

}