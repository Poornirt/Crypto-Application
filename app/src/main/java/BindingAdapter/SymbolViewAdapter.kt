package adapter

import android.graphics.Color
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.example.cryptoapplication.PRICE
import com.example.cryptoapplication.R
import data.Crypto
import helper.CommonHelper


@BindingAdapter("app:changePriceTitleText")
fun changePriceTitleText(pPriceView: TextView, price: String) {
    pPriceView.text = when (price) {
        PRICE.CURRENT_PRICE.name -> "Buy Price"
        PRICE.HIGH_PRICE.name -> "High Price"
        PRICE.LOW_PRICE.name -> "Low Price"
        else -> "Buy Price"
    }
}

@BindingAdapter("app:addSymbol")
fun addSymbol(pSymbolView: TextView, symbol: String) {
    pSymbolView.text = CommonHelper(pSymbolView.context).setSymbolSpan(symbol)
}

@BindingAdapter("app:changeTitleText")
fun changeTitleText(pPriceView: TextView, price: String) {
    pPriceView.text = when (price) {
        PRICE.CURRENT_PRICE.name -> "Current Buy Price"
        PRICE.HIGH_PRICE.name -> "High Price"
        PRICE.LOW_PRICE.name -> "Low Price"
        else -> "Current Buy Price"
    }
}


@BindingAdapter("app:changePriceText","app:crypto")
fun changePriceText(pPriceView: TextView, price: String,crypto: Crypto?) {
    crypto?.let {
        pPriceView.text = when (price) {
            PRICE.CURRENT_PRICE.name -> crypto.lastPrice
            PRICE.HIGH_PRICE.name -> crypto.highPrice
            PRICE.LOW_PRICE.name -> crypto.lowPrice
            else -> crypto.lastPrice
        }
    }
}

