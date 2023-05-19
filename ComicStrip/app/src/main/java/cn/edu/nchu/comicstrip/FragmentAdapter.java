package cn.edu.nchu.comicstrip;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.util.List;

import cn.edu.nchu.comicstrip.fragment.ContentFragment;
import cn.edu.nchu.comicstrip.fragment.EndFragment;
import cn.edu.nchu.comicstrip.fragment.WelcomeFragment;

public class FragmentAdapter extends FragmentStateAdapter {
    private List<Fragment> fragments;

    public FragmentAdapter(@NonNull FragmentActivity fragmentActivity, List<Fragment> fragments ) {
        super(fragmentActivity);
        this.fragments = fragments;
    }

    public FragmentAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
//        System.out.println("position"+position);
        System.out.println("fragmentsize:"+fragments.size());
        if(position >= 1 && position <= 34){
          return ContentFragment.newInstance(String.valueOf(position),String.valueOf(position-1));
        }else if(position == 0){
            return WelcomeFragment.newInstance(String.valueOf(position),String.valueOf(position-1));
        }else {
            return EndFragment.newInstance(String.valueOf(position),String.valueOf(position-1));
        }
    }

    @Override
    public int getItemCount() {
        return fragments.size();
    }
}
