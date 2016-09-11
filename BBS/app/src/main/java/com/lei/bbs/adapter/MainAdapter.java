package com.lei.bbs.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.lei.bbs.R;
import com.lei.bbs.bean.BBS;
import java.util.List;

public class MainAdapter extends BaseAdapter{
	private List<BBS> bbsList;
	private Context context;

	public MainAdapter(Context context, List<BBS> bbsList){
		this.context=context;
		this.bbsList=bbsList;
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
			viewHolder.imageView=(ImageView)view.findViewById(R.id.imgHead);
			view.setTag(viewHolder);//存储viewHolder对象
		}else {
			view=convertView;
			viewHolder=(ViewHolder)view.getTag();
		}

		viewHolder.tvName.setText(bbs.getName());
		viewHolder.tvSendTime.setText(bbs.getSendTime());
		viewHolder.tvAnswerTimes.setText("" + bbs.getAnswerTimes());
		viewHolder.tvTitle.setText(bbs.getTitle());
		viewHolder.tvContent.setText(bbs.getContent());
		return view;
	}

	private  class  ViewHolder{
		TextView tvTitle,tvContent, tvSendTime, tvName,tvAnswerTimes;
		ImageView imageView;
	}
	
}
