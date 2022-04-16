package service


interface ServiceProvider {

    fun getApi(): CryptoMarketService

    companion object {
        private var mInstance: MarketStatsService? = null
        fun getInstance(): MarketStatsService {
            synchronized(this) {
                if (mInstance == null)
                    mInstance = MarketStatsService()
                return mInstance as MarketStatsService
            }
        }
    }

}
