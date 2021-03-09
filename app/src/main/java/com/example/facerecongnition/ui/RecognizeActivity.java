package com.example.facerecongnition.ui;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.ContentUris;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import com.alibaba.fastjson.JSONObject;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.facerecongnition.BuildConfig;
import com.example.facerecongnition.R;
import com.example.facerecongnition.constant.Constant;
import com.example.facerecongnition.ui.custmerView.MyImageView;
import com.example.facerecongnition.ui.home.HomeActivity;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.nanchen.compresshelper.CompressHelper;
import com.qmuiteam.qmui.widget.QMUITopBarLayout;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import es.dmoral.toasty.Toasty;

public class RecognizeActivity extends AppCompatActivity {
    public static final int TAKE_PHOTO = 1;
    public static final int CHOOSE_PHOTO = 2;
    private String outputImagePath = "";
    private Uri imageUri;
    String pictureName; // 服务器传回来的图片名
    String pictureType; // 服务器传回来的图片后缀
    @BindView(R.id.iv_picture)
    ImageView picture;
    @BindView(R.id.linear_btn_up)
    LinearLayout mLinerUp;
    @BindView(R.id.linear_btn_down)
    LinearLayout mLinerDown;
    @BindView(R.id.topbar)
    QMUITopBarLayout mTopBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);//remove title bar  即隐藏标题栏
        setContentView(R.layout.activity_recongnize);
        ButterKnife.bind(this);
        initTopBar();
    }

    private void initTopBar(){
        mTopBar.addLeftBackImageButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(RecognizeActivity.this, "返回测试", Toast.LENGTH_SHORT).show();
            }
        });
        mTopBar.setTitle("智慧工地之人脸考勤");
    }

    @OnClick(R.id.btn_take_photo)
    void takephoto() {
//    创建file对象，用于存储拍照后的图片；
//        textRecognize.setText("识别结果:");
        File outputImage = new File(getExternalCacheDir(), "output_image.jpg");
        outputImagePath = outputImage.getAbsolutePath();
        try {
            if (outputImage.exists()) {
                outputImage.delete();
            }
            outputImage.createNewFile();

        } catch (Exception e) {
            e.printStackTrace();
        }

        if (Build.VERSION.SDK_INT >= 24) {
            imageUri = FileProvider.getUriForFile(RecognizeActivity.this,
                    BuildConfig.APPLICATION_ID + ".fileProvider", outputImage);
        } else {
            imageUri = Uri.fromFile(outputImage);
        }

        //启动相机程序
        if (ContextCompat.checkSelfPermission(RecognizeActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            //如果没有请求权限在这里请求
            ActivityCompat.requestPermissions(RecognizeActivity.this, new String[]{Manifest.permission.CAMERA}, 1);
        }
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        startActivityForResult(intent, TAKE_PHOTO);
    }

    @OnClick(R.id.btn_choose_from_album)
    void choosealbum() {
//        textRecognize.setText("识别结果:");
        if (ContextCompat.checkSelfPermission(RecognizeActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(RecognizeActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        } else {
            openAlbum();
        }
    }

    @OnClick(R.id.btn_recognize)
    void Recognize() {
        File file = new File(outputImagePath);
        if (!file.exists()) {
            Toasty.error(getApplicationContext(), "请先上传图片！", Toast.LENGTH_SHORT, true).show();
            return;
        }
        if (!file.canRead()) {
            Toasty.error(getApplicationContext(), "请先上传图片！", Toast.LENGTH_SHORT, true).show();
            return;
        }
//        Toasty.success(getApplicationContext(), "识别中，请勿重复点击... ...", Toast.LENGTH_SHORT, true).show();

        // 图像压缩
        File newFile = new CompressHelper.Builder(this)
                .setQuality(30)    // 默认压缩质量为80
                .build()
                .compressToFile(file);

        // 上传图片到服务器
//        OkGo.<String>post(Constant.URL + "/picture/uploadRecognitionPicture")
//                .tag(this)
//                .params("file", newFile)
//                .isMultipart(true)
//                .execute(new StringCallback() {
//                    @Override
//                    public void onSuccess(Response<String> response) {
//                        String res = response.body();
//                        JSONObject jo_temp = JSONObject.parseObject(res);
//                        String body = jo_temp.getString("body");
//                        JSONObject jo = JSONObject.parseObject(body);
//                        String staffName = jo.getString("staffName");
//                        if (staffName == null) {
//                            staffName = "无法识别";
//                        }
////                        textRecognize.setText("识别结果：" + staffName);
////                        textRecognize.invalidate();
////                        getRecognizeResult();
//                    }
//
//                    @Override
//                    public void onError(Response<String> response) {
//                        Toasty.error(getApplicationContext(), "上传出错", Toast.LENGTH_SHORT, true).show();
//                    }
//                });
        Intent intent=new Intent(RecognizeActivity.this,FaceResultActivity.class);
        intent.setData(imageUri);
        startActivity(intent);
    }

    @OnClick(R.id.btn_giveup) void giveUp(){
        Glide.with(this).clear(picture);
        changeVisibility(mLinerUp, mLinerDown);
    }

    private void changeVisibility(LinearLayout visible, LinearLayout invisible){
        visible.setVisibility(View.VISIBLE);
        invisible.setVisibility(View.INVISIBLE);
    }

//    public void getRecognizeResult() {
//        if (pictureName.isEmpty() || pictureType.isEmpty()) {
//            Toasty.error(getApplicationContext(), "请先上传图片！", Toast.LENGTH_SHORT, true).show();
//            return;
//        }
//        OkGo.<String>post(Constant.URL + "/picture/uploadRecognitionPicture")
//                .tag(this)
//                .params("pictureName ", pictureName)
//                .params("pictureType", pictureType)
//                .isMultipart(true)
//                .execute(new StringCallback() {
//                    @Override
//                    public void onSuccess(Response<String> response) {
////                        String res = response.body();
////                        JSONObject jo_temp = JSONObject.parseObject(res);
////                        String body = jo_temp.getString("body");
////                        JSONObject jo = JSONObject.parseObject(body);
////                        picture.setImageURL("http://121.48.163.57:18080/FaceRecognition/picture/PreviewPictureRecordFile?pictureName=33ede0227d21432cb8f61ee4949d2f2012701&pictureType=png");
//                        String url = String.format("http://121.48.163.57:18080/FaceRecognition/picture/PreviewPictureRecordFile?pictureName=%s&pictureType=%s", pictureName, pictureType);
//                        picture.setImageURL(url);
//                        picture.invalidate();
////                        textRecognize.setText("识别结果：张三");
//                        pictureName = "";
//                        pictureType = "";
//
//                        Toasty.success(getApplicationContext(), "识别成功！", Toast.LENGTH_SHORT, true).show();
//                    }
//
//                    @Override
//                    public void onError(Response<String> response) {
//                        Toasty.error(getApplicationContext(), "识别出错", Toast.LENGTH_SHORT, true).show();
//                    }
//                });
//    }

    //打开相册
    private void openAlbum() {
        Intent intent = new Intent("android.intent.action.GET_CONTENT");
        intent.setType("image/*");
        startActivityForResult(intent, CHOOSE_PHOTO);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    openAlbum();
                } else {
                    Toast.makeText(this, "you denied the permission", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) { // 显示拍照图片
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case TAKE_PHOTO:
                if (resultCode == RESULT_OK) {
                    try {
                        RequestOptions options =  new RequestOptions().
                                skipMemoryCache(true).diskCacheStrategy(DiskCacheStrategy.NONE);
                        Glide.with(this).asBitmap().load(imageUri).apply(options).into(picture);
                        changeVisibility(mLinerDown, mLinerUp);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                break;
            case CHOOSE_PHOTO:
                if (resultCode == RESULT_OK) {
                    if (Build.VERSION.SDK_INT >= 19) {  //4.4及以上的系统使用这个方法处理图片；
                        try {
                            handleImageOnKitKat(data);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    } else {
                        try {
                            handleImageBeforeKitKat(data);  //4.4及以下的系统使用这个方法处理图片
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            default:
                break;
        }
    }

    // 4.4及以下的系统使用这个方法处理图片
    private void handleImageBeforeKitKat(Intent data) throws IOException {
        Uri uri = data.getData();
        String imagePath = getImagePath(uri, null);
        RequestOptions options =  new RequestOptions().
                skipMemoryCache(true).diskCacheStrategy(DiskCacheStrategy.NONE);
        Glide.with(this).asBitmap().load(imagePath).apply(options).into(picture);
        if (imagePath != null){
            changeVisibility(mLinerDown, mLinerUp);
        }
    }

    private String getImagePath(Uri uri, String selection) {
        String path = null;
        //通过Uri和selection来获取真实的图片路径
        Cursor cursor = getContentResolver().query(uri, null, selection, null, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            }
            cursor.close();
        }
        return path;
    }

    /**
     * 4.4及以上的系统使用这个方法处理图片
     *
     * @param data
     */
    // 相册中选择
    @TargetApi(19)
    private void handleImageOnKitKat(Intent data) throws IOException {
        String imagePath = null;
        Uri uri = data.getData();
        if (DocumentsContract.isDocumentUri(this, uri)) {
            //如果document类型的Uri,则通过document来处理
            String docID = DocumentsContract.getDocumentId(uri);
            if ("com.android.providers.media.documents".equals(uri.getAuthority())) {
                String id = docID.split(":")[1];     //解析出数字格式的id
                String selection = MediaStore.Images.Media._ID + "=" + id;

                imagePath = getImagePath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, selection);
            } else if ("com.android.providers.downloads.documents".equals(uri.getAuthority())) {
                Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/piblic_downloads"), Long.valueOf(docID));
                imagePath = getImagePath(contentUri, null);
            }

        } else if ("content".equalsIgnoreCase(uri.getScheme())) {
            //如果是content类型的uri，则使用普通方式使用
            imagePath = getImagePath(uri, null);
        } else if ("file".equalsIgnoreCase(uri.getScheme())) {
            //如果是file类型的uri，直接获取路径即可
            imagePath = uri.getPath();
        }
        outputImagePath = imagePath;
        RequestOptions options =  new RequestOptions().
                skipMemoryCache(true).diskCacheStrategy(DiskCacheStrategy.NONE);
        Glide.with(this).asBitmap().load(imagePath).apply(options).into(picture);
        if (imagePath != null){
            changeVisibility(mLinerDown, mLinerUp);
        }
    }
}
