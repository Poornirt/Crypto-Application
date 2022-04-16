package factory

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import service.CryptoMarketService
import viewmodel.CryptoViewModel

class CryptoViewModelFactory(var mApi:CryptoMarketService,var mApplication: Application):ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CryptoViewModel::class.java))
            return CryptoViewModel(mApi,mApplication) as T
        throw IllegalArgumentException("CryptoViewModel view model is not found")
    }
}