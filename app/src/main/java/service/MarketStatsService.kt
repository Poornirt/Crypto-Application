package service

import retorfitbuilder.RetrofitBuilder


class MarketStatsService : ServiceProvider {

    override fun getApi(): CryptoMarketService {
        return RetrofitBuilder.fetchMarketStats()
    }

}

