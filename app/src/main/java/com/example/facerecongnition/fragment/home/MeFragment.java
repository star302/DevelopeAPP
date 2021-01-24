package com.example.facerecongnition.fragment.home;

import android.view.LayoutInflater;
import android.view.View;

import com.example.facerecongnition.R;
import com.qmuiteam.qmui.arch.QMUIFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MeFragment extends QMUIFragment {
    @Override
    protected View onCreateView() {
        View root = LayoutInflater.from(getActivity()).inflate(R.layout.me_fragment, null);
        ButterKnife.bind(this, root);
        return root;
    }
}
