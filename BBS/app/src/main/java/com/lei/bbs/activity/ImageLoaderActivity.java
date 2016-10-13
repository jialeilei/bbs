package com.lei.bbs.activity;


import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import com.lei.bbs.R;
import com.lei.bbs.util.ImageLoader;
import com.lei.bbs.util.MyLog;
import java.util.ArrayList;
import java.util.List;


public class ImageLoaderActivity extends AppCompatActivity implements AbsListView.OnScrollListener{

    ListView gridView;
    ImageAdapter imageAdapter;
    private List<String> list = new ArrayList<String>();
    private ImageLoader mImageLoader;
    private boolean mIsGridViewIdle = true;
    private int mImageWidth = 0;
    private boolean mIsWifi = false;
    private boolean mCanGetBitmapFromNetWork = false;
    private String TAG = "ImageLoaderActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_loader);
        setData();
        initView();
        mImageLoader = ImageLoader.build(ImageLoaderActivity.this);
    }

    private void initView(){
        gridView = (ListView) findViewById(R.id.gv);
        imageAdapter = new ImageAdapter(this);
        gridView.setAdapter(imageAdapter);
        gridView.setOnScrollListener(this);

    }

    private void setData() {
        String[] imageUrls = {
                "http://b.hiphotos.baidu.com/zhidao/pic/item/a6efce1b9d16fdfafee0cfb5b68f8c5495ee7bd8.jpg",
                "http://pic47.nipic.com/20140830/7487939_180041822000_2.jpg",
                "http://pic41.nipic.com/20140518/4135003_102912523000_2.jpg",
                "http://img2.imgtn.bdimg.com/it/u=1133260524,1171054226&fm=21&gp=0.jpg",
                "http://h.hiphotos.baidu.com/image/pic/item/3b87e950352ac65c0f1f6e9efff2b21192138ac0.jpg",
                "http://pic42.nipic.com/20140618/9448607_210533564001_2.jpg",
                "http://pic10.nipic.com/20101027/3578782_201643041706_2.jpg",
                "http://picview01.baomihua.com/photos/20120805/m_14_634797817549375000_37810757.jpg",
                "http://img2.3lian.com/2014/c7/51/d/26.jpg",
                "http://img3.3lian.com/2013/c1/34/d/93.jpg",
                "http://b.zol-img.com.cn/desk/bizhi/image/3/960x600/1375841395686.jpg",
                "http://picview01.baomihua.com/photos/20120917/m_14_634834710114218750_41852580.jpg",
                "http://cdn.duitang.com/uploads/item/201311/03/20131103171224_rr2aL.jpeg",
                "http://imgrt.pconline.com.cn/images/upload/upc/tx/wallpaper/1210/17/c1/spcgroup/14468225_1350443478079_1680x1050.jpg",
                "http://pic41.nipic.com/20140518/4135003_102025858000_2.jpg",
                "http://www.1tong.com/uploads/wallpaper/landscapes/200-4-730x456.jpg",
                "http://pic.58pic.com/58pic/13/00/22/32M58PICV6U.jpg",
                "http://picview01.baomihua.com/photos/20120629/m_14_634765948339062500_11778706.jpg",
                "http://h.hiphotos.baidu.com/zhidao/wh%3D450%2C600/sign=429e7b1b92ef76c6d087f32fa826d1cc/7acb0a46f21fbe09cc206a2e69600c338744ad8a.jpg",
                "http://pica.nipic.com/2007-12-21/2007122115114908_2.jpg",
                "http://cdn.duitang.com/uploads/item/201405/13/20140513212305_XcKLG.jpeg",
                "http://photo.loveyd.com/uploads/allimg/080618/1110324.jpg",
                "http://img4.duitang.com/uploads/item/201404/17/20140417105820_GuEHe.thumb.700_0.jpeg",
                "http://cdn.duitang.com/uploads/item/201204/21/20120421155228_i52eX.thumb.600_0.jpeg",
                "http://img4.duitang.com/uploads/item/201404/17/20140417105856_LTayu.thumb.700_0.jpeg",
                "http://img04.tooopen.com/images/20130723/tooopen_20530699.jpg",
                "http://www.qjis.com/uploads/allimg/120612/1131352Y2-16.jpg",
                "http://pic.dbw.cn/0/01/33/59/1335968_847719.jpg",
                "http://a.hiphotos.baidu.com/image/pic/item/a8773912b31bb051a862339c337adab44bede0c4.jpg",
                "http://h.hiphotos.baidu.com/image/pic/item/f11f3a292df5e0feeea8a30f5e6034a85edf720f.jpg",
                "http://img0.pconline.com.cn/pconline/bizi/desktop/1412/ER2.jpg",
                "http://pic.58pic.com/58pic/11/25/04/91v58PIC6Xy.jpg",
                "http://img3.3lian.com/2013/c2/32/d/101.jpg",
                "http://pic25.nipic.com/20121210/7447430_172514301000_2.jpg",
                "http://img02.tooopen.com/images/20140320/sy_57121781945.jpg",
                "http://www.renyugang.cn/emlog/content/plugins/kl_album/upload/201004/852706aad6df6cd839f1211c358f2812201004120651068641.jpg",
                "http://p0.so.qhmsg.com/t011c00ffa87bbbbc00.jpg" };

        if (list.size() > 0){
            list.clear();
        }
        for (String uri : imageUrls){
            list.add(uri);
        }

    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE){
            MyLog.i(TAG," scrolling ");
            mIsGridViewIdle = true;
            imageAdapter.notifyDataSetChanged();
        }else {
            mIsGridViewIdle = false;
        }
    }


    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

    }


    //adapter
    public class ImageAdapter extends BaseAdapter {
        //private List<String> list;
        private Context mContext;
        private int mImageWidth = 100;
        private int mImageHeight = 100;
        private boolean mIsGridViewIdle = true;


      /*  public ImageAdapter(Context context, List<String> list){
            this.list = list;
            this.mContext = context;
        }*/

        public ImageAdapter(Context context){
            this.mContext = context;
        }


        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            return list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            //String uri = list.get(position);
            View view;
            ViewHolder viewHolder;
            if (convertView==null) {
                view = LayoutInflater.from(mContext).inflate(R.layout.item_grid_image, null);

                viewHolder = new ViewHolder();
                viewHolder.imageView = (ImageView)view.findViewById(R.id.img);
                view.setTag(viewHolder);//存储viewHolder对象
            }else {
                view = convertView;
                viewHolder = (ViewHolder)view.getTag();
            }
            ImageView imageView = viewHolder.imageView;

            final String tag = (String) imageView.getTag();
            final String uri = (String) getItem(position);

            if (!uri.equals(tag)){
                imageView.setImageResource(R.mipmap.head);
            }
            if (mIsGridViewIdle){
                // && mCanGetBitmapFromNetWork
                imageView.setTag(uri);
                mImageLoader.bindBitmap(uri,imageView,mImageWidth,mImageHeight);
            }

            return view;
        }

        private  class  ViewHolder{
            ImageView imageView;
        }

    }


}
