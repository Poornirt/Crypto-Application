package pagingsource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import data.Crypto
import service.CryptoMarketService
import java.lang.Exception

private const val NETWORK_PAGE_SIZE = 50
private const val INITIAL_LOAD_SIZE = 1

class MarketStatsPagingSource(var mApi: CryptoMarketService) : PagingSource<Int, Crypto>() {

    override fun getRefreshKey(state: PagingState<Int, Crypto>): Int? {
        return null
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Crypto> {
        val position = params.key ?: INITIAL_LOAD_SIZE
        return try {
            val lList: List<Crypto> = mApi.fetchMarketStatistics()
            val nextKey = if (lList.isEmpty())
                null
            else
                position + (params.loadSize / NETWORK_PAGE_SIZE)
            LoadResult.Page(data = lList, prevKey = null, nextKey = nextKey)
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override val keyReuseSupported: Boolean
        get() = true
}