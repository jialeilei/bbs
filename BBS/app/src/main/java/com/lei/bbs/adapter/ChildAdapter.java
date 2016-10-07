package com.lei.bbs.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.lei.bbs.R;
import com.lei.bbs.bean.TalkFeed;
import com.lei.bbs.util.MyLog;
import java.util.ArrayList;
import java.util.List;

/**
 *
 */

public class ChildAdapter extends BaseAdapter{

	private List<TalkFeed>  bbsList = new ArrayList<TalkFeed>();
	private Context context;
	private String TAG = "ChildAdapter";

	public ChildAdapter(Context context, List<TalkFeed> data) {
		//super(context,data);
		this.context = context;
		this.bbsList = data;
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
	public View getView(final int position, View convertView, ViewGroup parent) {
		ViewHolder vHolder = null;
		if (convertView == null) {
			vHolder = new ViewHolder();
			convertView = LayoutInflater.from(context).inflate(R.layout.item_talk_bbs, null);
			vHolder.tvFirst = (TextView) convertView.findViewById(R.id.tvFirst);
			vHolder.tvAnswer = (TextView) convertView.findViewById(R.id.tvAnswer);
			vHolder.tvSecond = (TextView) convertView.findViewById(R.id.tvSecond);
			vHolder.tvContent = (TextView) convertView.findViewById(R.id.tvContent);
			vHolder.linearLayout = (LinearLayout) convertView.findViewById(R.id.ll);
			convertView.setTag(vHolder);
		}else {
			vHolder = (ViewHolder) convertView.getTag();
		}

		vHolder.tvFirst.setText(bbsList.get(position).getUname());
		vHolder.tvContent.setText(bbsList.get(position).getContent());

		if (bbsList.get(position).getTo_tid() == 0){
			vHolder.tvSecond.setVisibility(View.GONE);
			vHolder.tvAnswer.setVisibility(View.GONE);
		}else {
			vHolder.tvSecond.setVisibility(View.VISIBLE);
			vHolder.tvAnswer.setVisibility(View.VISIBLE);
			vHolder.tvSecond.setText(bbsList.get(position).getTo_tname());
		}

		vHolder.tvFirst.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				MyLog.i(TAG,"textView position: "+ position);
				MyLog.i(TAG,"size: "+ bbsList.size());
			}
		});
		vHolder.linearLayout.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				MyLog.i(TAG,"linearLayout position: "+ position);
			}
		});
		//记住是那个button事件
		//vHolder.tvFirst.setTag(position);

		return convertView;
	}
	
	class ViewHolder{
		TextView tvFirst,tvSecond,tvAnswer;

		TextView tvContent;
		LinearLayout linearLayout;
	}

	//父listView的button的监听
	private class ChildClickListener implements View.OnClickListener {
		@Override
		public void onClick(View v) {
			Integer nPosition = (Integer) (v.getTag());
			//MyLog.i(TAG,"name: "+bbsList.get(nPosition).getUname()+" content: "+bbsList.get(nPosition).getContent());
			MyLog.i(TAG,"position: "+ nPosition);
			MyLog.i(TAG,"size: "+ bbsList.size());
			//Toast.makeText(mContext, "提示" + String.valueOf(nPosition.intValue()), Toast.LENGTH_SHORT).show();
		}
	}

}
