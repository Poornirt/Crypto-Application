package adapter

import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.example.cryptoapplication.PRICE
import data.Crypto


@BindingAdapter("app:changePriceTitleText")
fun changePriceTitleText(pPriceView: TextView, price: String) {
    pPriceView.text = when (price) {
        PRICE.CURRENT_PRICE.name -> "Buy Price"
        PRICE.HIGH_PRICE.name -> "High Price"
        PRICE.LOW_PRICE.name -> "Low Price"
        else -> "Buy Price"
    }
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

