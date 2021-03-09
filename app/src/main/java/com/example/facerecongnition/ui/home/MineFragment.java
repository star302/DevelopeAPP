package com.example.facerecongnition.ui.home;

import android.view.LayoutInflater;
import android.view.View;

import com.example.facerecongnition.R;
import com.qmuiteam.qmui.arch.QMUIFragment;
import com.qmuiteam.qmui.widget.QMUITopBarLayout;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MineFragment extends QMUIFragment {

    @BindView(R.id.home_topbar)
    QMUITopBarLayout mTopbar;

    @Override
    protected View onCreateView() {
        View root = LayoutInflater.from(getActivity()).inflate(R.layout.mine_fragment, null);
        ButterKnife.bind(this, root);

        mTopbar.setTitle("我的");
        return root;
    }
}
