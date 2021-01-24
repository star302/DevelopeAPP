package com.example.facerecongnition.ui.home;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import com.example.facerecongnition.R;
import com.example.facerecongnition.ui.RecognizeActivity;
import com.qmuiteam.qmui.arch.QMUIFragment;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class HomeFragment extends QMUIFragment {

    @Override
    protected View onCreateView() {
        View root = LayoutInflater.from(getActivity()).inflate(R.layout.home_fragment, null);
        ButterKnife.bind(this, root);
        return root;
    }

    @OnClick(R.id.btn_home_recognize)
    void startRecognizeActivity() {
        startActivity(new Intent(getActivity(), RecognizeActivity.class));
    }
}