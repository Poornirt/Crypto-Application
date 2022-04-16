package fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.cryptoapplication.R
import com.example.cryptoapplication.databinding.BidsRowLayoutBinding
import kotlinx.coroutines.launch


class AsksFragment():Fragment() {

    private lateinit var mBidsRowLayoutBinding: BidsRowLayoutBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mBidsRowLayoutBinding =
            DataBindingUtil.inflate(inflater, R.layout.bids_row_layout, container, false)
        return mBidsRowLayoutBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lifecycleScope.launch {

        }
    }


}