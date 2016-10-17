package com.lei.bbs.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.lei.bbs.R;
import com.lei.bbs.bean.BBS;
import com.lei.bbs.constant.Constants;
import com.lei.bbs.util.CircleImage;
import com.lei.bbs.util.ImageLoader;
import com.lei.bbs.util.MyLog;
import java.util.List;


public class MainAdapter extends BaseAdapter{
	private List<BBS> bbsList;
	private Context context;
	private ImageLoader mImageLoader;
	private boolean mIsScrolling = false;
	private String TAG = "MainAdapter";

	public MainAdapter(Context context, List<BBS> bbsList){
		this.context=context;
		this.bbsList=bbsList;
		mImageLoader = ImageLoader.build(context);
	}

	public void scrollingState(boolean state){
		mIsScrolling = state;
		MyLog.i(TAG,"mIsScrolling result "+state);
	}

	@Override
	public int getCount() {
		return bbsList.size();
	}

	@Override
	public Object getItem(int position) {
		return bbsList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		BBS bbs=bbsList.get(position);
		View view;
		ViewHolder viewHolder;
		if (convertView==null) {
			view=LayoutInflater.from(context).inflate(R.layout.item_main_bbs, null);

			viewHolder=new ViewHolder();
			viewHolder.tvName =(TextView)view.findViewById(R.id.tvName);
			viewHolder.tvSendTime =(TextView)view.findViewById(R.id.tvSendTime);
			viewHolder.tvAnswerTimes=(TextView)view.findViewById(R.id.tvAnswerTimes);
			viewHolder.tvTitle = (TextView) view.findViewById(R.id.tvTitle);
			viewHolder.tvContent=(TextView)view.findViewById(R.id.tvMainContent);
			viewHolder.imageView=(CircleImage)view.findViewById(R.id.imgHead);
			viewHolder.llImg = (LinearLayout) view.findViewById(R.id.llMainImg);
			view.setTag(viewHolder);//存储viewHolder对象
		}else {
			view=convertView;
			viewHolder=(ViewHolder)view.getTag();
		}

		viewHolder.tvName.setText(bbs.getName());
		viewHolder.tvSendTime.setText(bbs.getSendTime());
		viewHolder.tvAnswerTimes.setText("" + bbs.getAnswerNumber());
		viewHolder.tvTitle.setText(bbs.getTitle());
		viewHolder.tvContent.setText(bbs.getContent());

		CircleImage imageView = viewHolder.imageView;
		final String uri = Constants.base_url+bbs.getAvatar();
		final String tag = (String) imageView.getTag();

		//final String tag = (String) imageView.getTag();
		//final String uri = (String) getItem(position);//头像网址

		MyLog.i(TAG,"uri "+bbs.getAvatar());
		if (tag != null && tag.length() > 0){
			if (!uri.equals(tag)){
				imageView.setImageResource(R.mipmap.head);
			}
		}


		if (!mIsScrolling && uri != null){
			imageView.setTag(uri);
			mImageLoader.bindBitmap(uri,imageView,100,100);
		}

		return view;
	}

	private  class  ViewHolder{
		TextView tvTitle,tvContent, tvSendTime, tvName,tvAnswerTimes;
		CircleImage imageView;
		LinearLayout llImg;
	}
	
}
