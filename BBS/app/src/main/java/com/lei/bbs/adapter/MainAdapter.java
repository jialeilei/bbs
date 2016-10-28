package com.lei.bbs.adapter;


import android.content.Context;
import android.widget.ImageView;
import android.widget.LinearLayout;
import com.lei.bbs.R;
import com.lei.bbs.bean.BBS;
import com.lei.bbs.constant.Constants;
import com.lei.bbs.util.CircleImage;
import com.lei.bbs.util.imageLoader.MagicImageLoader;
import com.lei.bbs.util.MyLog;
import java.util.List;


public class MainAdapter extends CommonAdapter<BBS>{

	private MagicImageLoader mMagicImageLoader;
	private boolean mIsScrolling = false;
	private String TAG = "MainAdapter";

	public MainAdapter(Context context, List<BBS> datas) {
		super(context, datas, R.layout.item_main_bbs);
		mMagicImageLoader = MagicImageLoader.build(context);
	}

	public void scrollingState(boolean state){
		mIsScrolling = state;
		MyLog.i(TAG,"mIsScrolling result "+state);
	}

	@Override
	public void convert(com.lei.bbs.bean.ViewHolder viewHolder, BBS item, int position) {

		viewHolder.setText(R.id.tvName,item.getName());
		viewHolder.setText(R.id.tvSendTime,item.getSendTime());
		viewHolder.setText(R.id.tvAnswerTimes,item.getAnswerNumber()+"");
		viewHolder.setText(R.id.tvTitle,item.getTitle());
		viewHolder.setText(R.id.tvMainContent,item.getContent());
		CircleImage imageView = viewHolder.getView(R.id.imgHead);
		LinearLayout linearLayout = viewHolder.getView(R.id.llMainImg);

		//head image
		final String uri = Constants.base_url+item.getAvatar();
		final String tag = (String) imageView.getTag();

		MyLog.i(TAG,"uri "+item.getAvatar());
		if (tag != null && tag.length() > 0){
			if (!uri.equals(tag)){
				imageView.setImageResource(R.mipmap.head);
			}
		}

		if (!mIsScrolling && uri != null){
			imageView.setTag(uri);
			mMagicImageLoader.bindBitmap(uri,imageView,100,100);
		}

		//ContentImage
		linearLayout.removeAllViews();
		if(item.getImageList().get(0).getImageCount() > 0){

			ImageView  img = new ImageView(mContext);
			linearLayout.addView(img);
			String contentUri = Constants.base_url+item.getImageList().get(0).getContentImage();
			String contentTag = (String) img.getTag();
			if (contentTag != null && contentTag.length() > 0){
				if (!contentUri.equals(contentTag)){
					linearLayout.removeAllViewsInLayout();
				}
			}
			if (!mIsScrolling && contentUri != null){
				img.setTag(contentUri);
				mMagicImageLoader.bindBitmap(contentUri,img,200,200);
			}
		}

	}
	
}
