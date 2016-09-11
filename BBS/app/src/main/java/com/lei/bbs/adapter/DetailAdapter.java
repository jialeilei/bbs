package com.lei.bbs.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.lei.bbs.R;
import com.lei.bbs.bean.Detail;
import java.util.List;

public class DetailAdapter extends BaseAdapter{
	private List<Detail> bbsList;
	private Context context;

	public DetailAdapter(Context context, List<Detail> bbsList){
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
		Detail bbs=bbsList.get(position);
		View view;
		ViewHolder viewHolder;
		if (convertView==null) {
			view=LayoutInflater.from(context).inflate(R.layout.item_detail_bbs, null);

			viewHolder=new ViewHolder();
			viewHolder.tvName = (TextView)view.findViewById(R.id.tvName);
			viewHolder.tvLevel = (TextView) view.findViewById(R.id.tvLevel);
			viewHolder.tvTopFloor = (TextView) view.findViewById(R.id.tvTopFloor);
			viewHolder.tvFloorNum = (TextView) view.findViewById(R.id.tvFloorNumber);
			viewHolder.tvSendTime = (TextView)view.findViewById(R.id.tvSendTime);
			viewHolder.tvTitle = (TextView) view.findViewById(R.id.tvTitle);
			viewHolder.tvContent = (TextView)view.findViewById(R.id.tvMainContent);
			viewHolder.imgSex = (ImageView) view.findViewById(R.id.imgSex);
			viewHolder.imageView=(ImageView)view.findViewById(R.id.imgHead);
			view.setTag(viewHolder);//存储viewHolder对象
		}else {
			view=convertView;
			viewHolder=(ViewHolder)view.getTag();
		}

		viewHolder.tvName.setText(bbs.getName());
		viewHolder.tvLevel.setText(""+bbs.getLevel());
		if (bbs.getFloor()==0){
			viewHolder.tvTopFloor.setVisibility(View.VISIBLE);
			viewHolder.tvFloorNum.setVisibility(View.GONE);
		}else {
			viewHolder.tvTopFloor.setVisibility(View.INVISIBLE);
			viewHolder.tvFloorNum.setText("第"+bbs.getFloor()+"层");
		}
		viewHolder.tvSendTime.setText(bbs.getSendTime());
		viewHolder.tvTitle.setText(bbs.getTitle());
		viewHolder.tvContent.setText(bbs.getContent());
		if (bbs.getSex().equals("boy")){
			viewHolder.imgSex.setImageResource(R.mipmap.boy);
		}else {
			viewHolder.imgSex.setImageResource(R.mipmap.girl);
		}
		return view;
	}

	private  class  ViewHolder{
		TextView tvName,tvLevel,tvTopFloor,tvFloorNum,tvSendTime,tvTitle,tvContent;
		ImageView imageView,imgSex;
	}
	
}
