package com.example.facerecongnition.ui.home;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import com.chaychan.library.BottomBarLayout;
import com.example.facerecongnition.R;
import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;
public class HomeActivity extends AppCompatActivity {

    @BindView(R.id.view_homepage)
    ViewPager mViewPager;
    @BindView(R.id.bottom_bar)
    BottomBarLayout mBottomBarLayout;
    List<Fragment> pageLists;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View root = LayoutInflater.from(this).inflate(R.layout.activity_home, null);
        ButterKnife.bind(this, root);
        setContentView(root);
        initData();
    }

    private void initData() {
        pageLists=new ArrayList<>();//list里面fragment,
        pageLists.add(new HomeFragment());
        pageLists.add(new MineFragment());
        mViewPager.setAdapter(new fragmentAdapter(getSupportFragmentManager()));
        mBottomBarLayout.setViewPager(mViewPager);//底部bottombar;
    }

    public class fragmentAdapter extends FragmentPagerAdapter {
        public fragmentAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return pageLists.get(position);
        }

        @Override
        public int getCount() {
            return pageLists.size();
        }
    }
}