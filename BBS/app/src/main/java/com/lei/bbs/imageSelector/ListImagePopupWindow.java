package com.lei.bbs.imageSelector;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import com.lei.bbs.R;
import java.util.List;

/**
 * Created by lei on 2016/10/27.
 */
public class ListImagePopupWindow extends PopupWindow {

    private int mWidth;
    private int mHeight;
    private View mConvertView;
    private ListView mListView;
    private List<FolderBean> mDatas;
    private ListDirAdapter mListDirAdapter;
    public interface OnDirSelectedListener{
        void onSelected(FolderBean folderBean);
    }

    private OnDirSelectedListener mSelectedListener;

    public void setOnDirSelectedListener(OnDirSelectedListener mSelectedListener){
        this.mSelectedListener = mSelectedListener;
    }

    public ListImagePopupWindow(Context context,List<FolderBean> mDatas) {

        calWidthAndHeight(context);
        mConvertView = LayoutInflater.from(context).inflate(R.layout.popup_main,null);
        setContentView(mConvertView);
        this.mDatas = mDatas;

        setWidth(mWidth);
        setHeight(mHeight);
        setFocusable(true);
        setTouchable(true);
        setOutsideTouchable(true);
        setBackgroundDrawable(new BitmapDrawable());


        setTouchInterceptor(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if (event.getAction() == MotionEvent.ACTION_OUTSIDE) {
                    dismiss();
                    return true;
                }

                return false;
            }
        });

        initView(context);
        initEvent();
    }

    private void initEvent() {

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if (mSelectedListener != null){
                    mSelectedListener.onSelected(mDatas.get(position));
                }
            }
        });

    }

    private void initView(Context context) {
        mListView = (ListView) mConvertView.findViewById(R.id.lvDir);
        mListDirAdapter = new ListDirAdapter(context,mDatas);
        mListView.setAdapter(mListDirAdapter);
    }

    /**
     * get width and height of popupWindow
     * @param context
     */
    private void calWidthAndHeight(Context context) {

        WindowManager wm = (WindowManager) context.getSystemService(context.WINDOW_SERVICE);
        DisplayMetrics metrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(metrics);
        mWidth = metrics.widthPixels;
        mHeight = (int) (metrics.heightPixels * 0.7);
    }

    private class ListDirAdapter extends ArrayAdapter<FolderBean>{

        private LayoutInflater mLayoutInflater;
        private List<FolderBean> mDatas;


        public ListDirAdapter(Context context, List<FolderBean> data) {
            super(context, 0, data);
            mLayoutInflater = LayoutInflater.from(context);
            this.mDatas = data;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            ViewHolder viewHolder = null;
            if (convertView == null){
                convertView = mLayoutInflater.inflate(R.layout.item_popup_main,parent,false);
                viewHolder = new ViewHolder();
                viewHolder.mImg = (ImageView) convertView.findViewById(R.id.img);
                viewHolder.mDirName = (TextView) convertView.findViewById(R.id.tvName);
                viewHolder.mDirCount = (TextView) convertView.findViewById(R.id.tvNumber);

                convertView.setTag(viewHolder);
            }else {
                viewHolder = (ViewHolder) convertView.getTag();
            }

            viewHolder.mImg.setImageResource(R.mipmap.image_no);
            FolderBean folderBean = mDatas.get(position);
            viewHolder.mDirName.setText(folderBean.getName());
            viewHolder.mDirCount.setText(folderBean.getCount()+"");
            LocalImageLoader.getInstance().loadImage(folderBean.getmFirstImgDir(),viewHolder.mImg);

            return convertView;
        }


        private class ViewHolder{
            TextView mDirName;
            TextView mDirCount;
            ImageView mImg;
        }


    }


}
