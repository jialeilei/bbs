package com.lei.bbs.activity;


import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.lei.bbs.R;
import com.lei.bbs.constant.Constants;
import com.lei.bbs.retrofit.RetrofitUtil;
import com.lei.bbs.util.Common;
import com.lei.bbs.util.MyLog;
import com.lei.bbs.util.MyToolBar;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;


public class UserInfoActivity extends BaseActivity implements View.OnClickListener{

    private RelativeLayout rlSex,rlLevel,rlHead;
    private Uri imageUri;
    private String TAG = "UserInfoActivity";
    private ImageView imgHead,imgTest;
    private TextView tvSex,tvLevel,tvName;
    private RetrofitUtil retrofitUtil = new RetrofitUtil();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);

        setToolBar();
        initView();
    }

    private void initView(){
        rlSex = (RelativeLayout) findViewById(R.id.rlSex);
        rlSex.setOnClickListener(this);
        rlHead = (RelativeLayout) findViewById(R.id.rlHeadContent);
        rlHead.setOnClickListener(this);
        rlLevel = (RelativeLayout) findViewById(R.id.rlLevel);
        rlLevel.setOnClickListener(this);
        imgHead = (ImageView) findViewById(R.id.imgHead);
       /* imgTest = (ImageView) findViewById(R.id.imgTest);
        imgTest.setOnClickListener(this);*/
        tvLevel = (TextView) findViewById(R.id.tvLevel);
        tvSex = (TextView) findViewById(R.id.tvSex);
        tvName = (TextView) findViewById(R.id.tvName);

        showUserInfo();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.rlHeadContent:
                chooseHeadDialog();
                break;
/*
            case R.id.imgTest:
                imgTest.setImageBitmap(getBitmapFromSharedPreferences());
                break;*/

            default:
                break;
        }
    }

    private void saveBitmapToSharedPreferences(Bitmap bitmap){
        //Bitmap bitmap=BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher);
        //第一步:将Bitmap压缩至字节数组输出流ByteArrayOutputStream
       /* ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 80, byteArrayOutputStream);
        //第二步:利用Base64将字节数组输出流中的数据转换成字符串String
        byte[] byteArray=byteArrayOutputStream.toByteArray();
        String imageString=new String(Base64.encodeToString(byteArray, Base64.DEFAULT));*/
        //第三步:将String保持至SharedPreferences

        Constants.avatar = Common.bitmapToString(bitmap);
        SharedPreferences sharedPreferences=getSharedPreferences(Constants.SHARE_USER_INFO, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putString(Constants.SHARE_AVATAR, Common.bitmapToString(bitmap));
        editor.commit();
    }


    /**
     * 从SharedPreferences中取出Bitmap
     */
    private Bitmap getBitmapFromSharedPreferences(){
        SharedPreferences sharedPreferences=getSharedPreferences(Constants.SHARE_USER_INFO, Context.MODE_PRIVATE);
        //第一步:取出字符串形式的Bitmap
        String imageString=sharedPreferences.getString(Constants.SHARE_AVATAR, "");
        //第二步:利用Base64将字符串转换为ByteArrayInputStream
       /* byte[] byteArray=Base64.decode(imageString, Base64.DEFAULT);
        ByteArrayInputStream byteArrayInputStream=new ByteArrayInputStream(byteArray);
        //第三步:利用ByteArrayInputStream生成Bitmap
        Bitmap bitmap=BitmapFactory.decodeStream(byteArrayInputStream);*/
        return Common.stringToBitMap(imageString);
    }

    private void showUserInfo(){
        SharedPreferences sharedPreferences = getSharedPreferences(Constants.SHARE_USER_INFO, Context.MODE_PRIVATE);
        String head = sharedPreferences.getString(Constants.SHARE_AVATAR, "");

        tvName.setText(Constants.userName);
        tvLevel.setText("LV."+Common.scoreToLevel(Constants.level));
        tvSex.setText(Constants.sex);

        if (!Constants.avatar.equals("")){
            imgHead.setImageBitmap(Common.stringToBitMap(Constants.avatar));
        }
        MyLog.i(TAG,"name "+Constants.userName+" sex "+Constants.sex);

    }

    private void setUserInfo(){

    }

    private void chooseHeadDialog() {
        View  view = getLayoutInflater().inflate(R.layout.photo_choose_dialog, null);
        final Dialog dialog = new Dialog(UserInfoActivity.this, R.style.transparentFrameWindowStyle);
        dialog.setContentView(view, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
        Window window = dialog.getWindow();
        window.setWindowAnimations(R.style.main_menu_animstyle);
        WindowManager.LayoutParams wl = window.getAttributes();
        wl.x = 0;
        //changed
        wl.y = getWindowManager().getDefaultDisplay().getHeight();
        wl.width = ViewGroup.LayoutParams.MATCH_PARENT;
        wl.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        dialog.onWindowAttributesChanged(wl);
        dialog.setCanceledOnTouchOutside(true);
        dialog.show();
        Button btnGallery=(Button)view.findViewById(R.id.btnGallery);
        btnGallery.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                choosePhotoFromGallery();
                dialog.cancel();
            }
        });

        Button btnCamera=(Button)view.findViewById(R.id.btnCamera);
        btnCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                takePhoto();
                dialog.cancel();
            }
        });
        Button btnCancel=(Button)view.findViewById(R.id.btnCancel);
        btnCancel.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                dialog.cancel();
            }
        });
    }


    private void takePhoto(){
        File outputImage = new File(Environment.getExternalStorageDirectory(),"output_image.jpg");
        try{
            if (outputImage.exists()){
                outputImage.delete();
            }
            outputImage.createNewFile();
        }catch (Exception e){
            e.printStackTrace();
        }
        imageUri = Uri.fromFile(outputImage);
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        intent.putExtra(MediaStore.EXTRA_OUTPUT,imageUri);
        startActivityForResult(intent,1);

    }

    private void choosePhotoFromGallery() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(intent, 3);
    }

    private Uri convertUri(Uri uri) {
        InputStream is = null;

        try {
            is = getContentResolver().openInputStream(uri);
            Bitmap bitmap = BitmapFactory.decodeStream(is);
            is.close();
            return saveBitmap(bitmap);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private Uri saveBitmap(Bitmap bitmap) {
        File outputImage = new File(Environment.getExternalStorageDirectory(), "output_image.jpg");
        try {
            if (outputImage.exists()) {
                outputImage.delete();
            }
            outputImage.createNewFile();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        try {
            FileOutputStream fos = new FileOutputStream(outputImage);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 85, fos);
            fos.flush();
            fos.close();
            return Uri.fromFile(outputImage);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode){
            case 1:
                if (resultCode==RESULT_OK){
                    Intent intent = new Intent("com.android.camera.action.CROP");
                    intent.setDataAndType(imageUri,"image/*");
                    intent.putExtra("scale", true);
                    intent.putExtra(MediaStore.EXTRA_OUTPUT,imageUri);
                    startActivityForResult(intent,2);
                }
                break;
            case 2:
                if (resultCode==RESULT_OK){
                    try {
                        MyLog.i(TAG, "img uri: " + imageUri);
                        Bitmap bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(imageUri));
                        saveBitmapToSharedPreferences(bitmap);
                        imgHead.setImageBitmap(bitmap);
                        sendHead2Server(Constants.userId, bitmap);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }

                break;
            case 3:
                if (resultCode == RESULT_OK) {
                    if (data == null) {
                        return;
                    } else {
                        Uri uri = data.getData();
                        //convertUri(uri);
                        imgHead.setImageURI(uri);

                        InputStream is = null;
                        try {
                            is = getContentResolver().openInputStream(uri);
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }
                        Bitmap bitmap = BitmapFactory.decodeStream(is);
                        try {
                            is.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        sendHead2Server(Constants.userId,bitmap);
                        saveBitmapToSharedPreferences(bitmap);

                    }

                }
                break;
            default:

                break;
        }
    }

    private void sendHead2Server(int uid,Bitmap bitmap){
        MyLog.i(TAG, "send head to server ");
        retrofitUtil.sendImage(uid, Common.bitmapToString(bitmap));

    }

    @Override
    public void setToolBar() {
        super.setToolBar();
        toolBar = (MyToolBar) findViewById(R.id.toolbar);
        toolBar.setBackgroundColor(getResources().getColor(R.color.title_blue));
        toolBar.setTitle(R.string.user_center);
        toolBar.disableRight();
        ImageButton btnBack = toolBar.getImgLeft();
        btnBack.setImageResource(R.mipmap.left);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserInfoActivity.this.finish();
            }
        });
    }

}
