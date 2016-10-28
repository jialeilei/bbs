package com.lei.bbs.imageSelector;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.lei.bbs.R;
import com.lei.bbs.util.MyLog;
import com.lei.bbs.util.MyToast;
import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class SelectorActivity extends AppCompatActivity {

    private GridView mGridView;
    private List<String> mImgs;
    private ImageGridAdapter mImageGridAdapter;

    private RelativeLayout mBottom;
    private TextView mDirName;
    private TextView mDirCount;

    private File mCurrentDir;
    private int mMaxCount;
    private List<FolderBean> mFolderBeans = new ArrayList<FolderBean>();
    private ProgressDialog mProgressDialog;
    private ListImagePopupWindow mPopupWindow;

    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            mProgressDialog.dismiss();
            data2View();
            initPopupWindow();
        }
    };

    private void initPopupWindow() {
        mPopupWindow = new ListImagePopupWindow(this,mFolderBeans);
        MyLog.i("lei","size: "+mFolderBeans.size());
        mPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                lightOn();
            }
        });
        mPopupWindow.setOnDirSelectedListener(new ListImagePopupWindow.OnDirSelectedListener() {
            @Override
            public void onSelected(FolderBean folderBean) {
                mCurrentDir = new File(folderBean.getDir());
                mImgs = Arrays.asList(mCurrentDir.list(new FilenameFilter(){
                    @Override
                    public boolean accept(File dir, String filename) {
                        if (filename.endsWith(".jpg")||filename.endsWith(".jpeg")||filename.endsWith(".png"))
                            return true;
                        return false;
                    }
                }));
                mImageGridAdapter = new ImageGridAdapter(SelectorActivity.this,mImgs,mCurrentDir.getAbsolutePath());
                mGridView.setAdapter(mImageGridAdapter);
                mDirName.setText(folderBean.getName());
                mDirCount.setText(mImgs.size()+"");
                mPopupWindow.dismiss();

            }
        });
    }

    /**
     *light on the screen
     */
    private void lightOn() {

        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = 1.0f;
        getWindow().setAttributes(lp);
    }

    private void lightOff() {

        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = .3f;
        getWindow().setAttributes(lp);
    }

    private void data2View() {
        if (mCurrentDir == null){
            return;
        }

        mImgs = Arrays.asList(mCurrentDir.list());
        mImageGridAdapter = new ImageGridAdapter(this,mImgs,mCurrentDir.getAbsolutePath());
        mGridView.setAdapter(mImageGridAdapter);
        mDirName.setText(mCurrentDir.getName());
        mDirCount.setText(mMaxCount+"");

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selector);

        initView();
        initData();
        initEvent();
    }

    private void initView() {
        mGridView = (GridView) findViewById(R.id.gv);
        mBottom = (RelativeLayout) findViewById(R.id.rlBottom);
        mDirName = (TextView) findViewById(R.id.tvDirName);
        mDirCount = (TextView) findViewById(R.id.tvDirCount);
    }

    private void initEvent() {

        mBottom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPopupWindow.setAnimationStyle(R.style.dir_popup_window);
                mPopupWindow.showAsDropDown(mBottom,0,0);
                lightOff();
            }
        });



    }

    /**
     * contentProvider 扫描手机中所有图片
     */
    private void initData() {

        if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
            MyToast.showShort(this,"存储卡不可用");
            return;
        }

        mProgressDialog = ProgressDialog.show(this,null,"loading ...");
        new Thread(){
            @Override
            public void run() {

                Uri mImgUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                ContentResolver cr = SelectorActivity.this.getContentResolver();
                Cursor cursor = cr.query(mImgUri, null,
                        MediaStore.Images.Media.MIME_TYPE + "=? or "
                                + MediaStore.Images.Media.MIME_TYPE + "=?",
                        new String[]{"image/jpeg", "image/png"},
                        MediaStore.Images.Media.DATE_MODIFIED);


                Set<String> mDirPath = new HashSet<String>();//扫描完成后自动回收
                while (cursor.moveToNext()){
                    //image path
                    String path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
                    File parentFile = new File(path).getParentFile();
                    if (parentFile == null)continue;

                    FolderBean folderBean ;
                    String dirPath = parentFile.getAbsolutePath();

                    //判断是否包含文件
                    if (mDirPath.contains(dirPath)){
                        continue;
                    }else {
                        mDirPath.add(dirPath);
                        folderBean = new FolderBean();
                        folderBean.setDir(dirPath);
                        folderBean.setmFirstImgDir(path);
                    }

                    if (parentFile.list() == null)continue;

                    int imageSize = parentFile.list(new FilenameFilter() {//过滤器
                        @Override
                        public boolean accept(File dir, String filename) {
                            if (filename.endsWith(".jpg")||filename.endsWith(".jpeg")||filename.endsWith(".png"))
                                return true;

                            return false;
                        }
                    }).length;
                    folderBean.setCount(imageSize);

                    mFolderBeans.add(folderBean);
                    if (imageSize > mMaxCount){
                        mMaxCount = imageSize;
                        mCurrentDir = parentFile;
                    }

                }
                cursor.close();
                //通知handler扫描完成
                mHandler.sendEmptyMessage(0x110);

            }
        }.start();

    }


}
