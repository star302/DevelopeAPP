package com.example.facerecongnition.ui.home;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import com.example.facerecongnition.R;
import com.example.facerecongnition.ui.RecognizeActivity;
import com.qmuiteam.qmui.arch.QMUIFragment;
import com.qmuiteam.qmui.widget.QMUITopBarLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class HomeFragment extends QMUIFragment {

    @BindView(R.id.home_topbar)
    QMUITopBarLayout mTopbar;

    @Override
    protected View onCreateView() {
        View root = LayoutInflater.from(getActivity()).inflate(R.layout.home_fragment, null);
        ButterKnife.bind(this, root);

        mTopbar.setTitle("首页");
        return root;
    }

    @OnClick(R.id.btn_home_recognize)
    void startRecognizeActivity() {
        startActivity(new Intent(getActivity(), RecognizeActivity.class));
    }
}