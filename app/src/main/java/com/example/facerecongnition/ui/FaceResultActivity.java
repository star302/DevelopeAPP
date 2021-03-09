package com.example.facerecongnition.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.facerecongnition.R;
import com.qmuiteam.qmui.widget.QMUITopBarLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FaceResultActivity extends AppCompatActivity {

    @BindView(R.id.iv_picture)
    ImageView ivPicture;
    @BindView(R.id.linear_btn_up)
    LinearLayout mLinerUp;
    @BindView(R.id.linear_btn_down)
    LinearLayout mLinerDown;
    @BindView(R.id.topbar)
    QMUITopBarLayout mTopBar;
    @BindView(R.id.tv_title_time)
    TextView textViewTitleTime;
    @BindView(R.id.tv_name)
    TextView textViewName;
    @BindView(R.id.tv_sex)
    TextView textViewSex;
    @BindView(R.id.tv_number)
    TextView textViewNumber;
    @BindView(R.id.tv_time)
    TextView textViewTime;
    @BindView(R.id.tv_place)
    TextView textViewPlace;
    @BindView(R.id.tv_bottom)
    TextView textViewBottom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);//remove title bar  即隐藏标题栏
        setContentView(R.layout.activity_face_result);
        ButterKnife.bind(this);
        init();
    }

    private void init(){
        mTopBar.addLeftBackImageButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(FaceResultActivity.this, "返回测试", Toast.LENGTH_SHORT).show();
            }
        });
        mTopBar.setTitle("智慧工地之人脸考勤");

        Intent intent=getIntent();
        if(intent!=null)
        {
            Uri imgurl = intent.getData();
            RequestOptions options =  new RequestOptions()
                    .skipMemoryCache(true)
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .centerCrop()
                    .override(150, 150);
            Glide.with(this).asBitmap().load(imgurl).apply(options).into(ivPicture);
        }

        textViewTitleTime.setText("时间");
        textViewName.setText("姓名：" + "");
        textViewSex.setText("性别：" + "");
        textViewNumber.setText("工号：" + "");
        textViewTime.setText("时间：" + "");
        textViewPlace.setText("地点：" + "");
        textViewBottom.setText("等待保存考勤信息");
    }

    private void changeVisibility(LinearLayout visible, LinearLayout invisible){
        visible.setVisibility(View.VISIBLE);
        invisible.setVisibility(View.INVISIBLE);
    }

    @OnClick(R.id.btn_save_result) void saveReslut(){
        changeVisibility(mLinerDown, mLinerUp);
        Drawable drawable = getResources().getDrawable(R.drawable.ic_yes);
        drawable.setBounds(0, 0,   drawable.getMinimumWidth(), drawable.getMinimumHeight());
        textViewBottom.setCompoundDrawablePadding(10);
        textViewBottom.setCompoundDrawables(drawable,null,null,null);
        textViewBottom.setText("已成功考勤");
    }

    @OnClick(R.id.btn_re_recognize) void reRecognize(){
        startActivity(new Intent(FaceResultActivity.this, RecognizeActivity.class));
    }

    @OnClick(R.id.btn_back_home) void backHome(){

    }

    @OnClick(R.id.btn_continue_recognize) void continueRecognize(){
        startActivity(new Intent(FaceResultActivity.this, RecognizeActivity.class));
    }

}