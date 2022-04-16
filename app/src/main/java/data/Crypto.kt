package data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Crypto(
    var symbol: String,
    var baseAsset: String,
    var quoteAsset: String,
    var openPrice: String,
    var lowPrice: String,
    var highPrice: String,
    var lastPrice: String,
    var volume: String,
    var bidPrice: String,
    var askPrice: String,
    var at: Long


):Parcelable {
    override fun equals(other: Any?): Boolean {
        if (this.symbol == (other as Crypto).symbol) return true
        return false
    }
}

