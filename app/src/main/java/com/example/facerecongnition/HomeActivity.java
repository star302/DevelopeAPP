package com.example.facerecongnition;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import com.chaychan.library.BottomBarLayout;
import com.example.facerecongnition.fragment.home.HomeFragment;
import com.example.facerecongnition.fragment.home.MeFragment;
import com.qmuiteam.qmui.widget.QMUITopBarLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class HomeActivity extends AppCompatActivity {

    @BindView(R.id.view_homepage)
    ViewPager mViewPager;
    @BindView(R.id.bottom_bar)
    BottomBarLayout mBottomBarLayout;
    @BindView(R.id.work_topbar)
    QMUITopBarLayout mTopBar;
    List<Fragment> pageLists;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View root = LayoutInflater.from(this).inflate(R.layout.layout_workpage, null);
        ButterKnife.bind(this, root);
        setContentView(root);
        initTopBar();
//        initData();
    }
    private void initTopBar() {
        mTopBar.addLeftBackImageButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                popBackStack();
            }
        });
    }
    private void initData() {
        pageLists=new ArrayList<>();//list里面fragment,
        pageLists.add(new HomeFragment());
        pageLists.add(new MeFragment());

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