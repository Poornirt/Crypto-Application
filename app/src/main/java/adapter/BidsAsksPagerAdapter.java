package adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

/**
 * Created by madhav on 22/11/17.
 */

public class BidsAsksPagerAdapter extends FragmentStatePagerAdapter {

    private ArrayList<HashMap<String, Object>> mFragmentList;

    public BidsAsksPagerAdapter(FragmentManager fm, ArrayList<HashMap<String, Object>> pList) {
        super(fm);
        mFragmentList = pList;
    }

    @Override
    public Fragment getItem(int position) {
        return (Fragment) Objects.requireNonNull(mFragmentList.get(position).get("fragment"));
    }


    @Override
    public int getItemPosition(@NonNull Object object) {
        return POSITION_NONE;
    }


    @Override
    public int getCount() {
        return mFragmentList.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return (CharSequence) mFragmentList.get(position).get("title");
    }
}
