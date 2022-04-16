package data

import com.google.gson.annotations.SerializedName

data class BidsAsks(

    @SerializedName("timestamp") var timestamp: Long? = null,
    @SerializedName("asks") var asks: ArrayList<ArrayList<String>> = arrayListOf(),
    @SerializedName("bids") var bids: ArrayList<ArrayList<String>> = arrayListOf()

)