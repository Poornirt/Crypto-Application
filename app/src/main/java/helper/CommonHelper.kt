package helper

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import constants.Constants
import data.Crypto
import java.lang.reflect.Type

class CommonHelper(mContext: Context) {

    private var mSharedPreferences = mContext.getSharedPreferences(
        Constants.SHAREDPREFERENCE,
        AppCompatActivity.MODE_PRIVATE
    )
    private var mSharedPreferenceEditor: SharedPreferences.Editor = mSharedPreferences.edit()
    private val mGson = Gson()

    fun jsonToGson(): List<Crypto> {
        val json = mSharedPreferences.getString(Constants.CRYPTOLIST, "")
        val type: Type = object : TypeToken<ArrayList<Crypto?>?>() {}.type
        return if (json?.isNotEmpty()!!) mGson.fromJson(json, type) else emptyList()
    }

    fun gsonToJson(pList: ArrayList<Crypto>) {
        val lCrypto = mGson.toJson(pList)
        mSharedPreferenceEditor.putString(Constants.CRYPTOLIST,lCrypto).commit()
    }
}