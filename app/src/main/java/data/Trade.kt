package data

import com.google.gson.annotations.SerializedName

data class Trade(
    @SerializedName("id") var id: Int? = null,
    @SerializedName("price") var price: String? = null,
    @SerializedName("qty") var qty: String? = null,
    @SerializedName("quoteQty") var quoteQty: String? = null,
    @SerializedName("time") var time: Long? = null,
    @SerializedName("isBuyerMaker") var isBuyerMaker: Boolean? = null

)