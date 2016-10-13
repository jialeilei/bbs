package com.lei.bbs.adapter;


import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
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

public class ChildAdapter extends BaseObjectListAdapter{

	private List<TalkFeed>  bbsList = new ArrayList<TalkFeed>();
	//private List<TalkFeed>  feeds = new ArrayList<TalkFeed>();
	private String TAG = "ChildAdapter";


	public ChildAdapter(Context context, List<TalkFeed> data) {
		super(context,data);
		setData(data);
	}

	private void setData(List<TalkFeed> data){
		for (int i = 0; i < data.size(); i++) {
			bbsList.add(new TalkFeed(
					data.get(i).getTid(),
					data.get(i).getUid(),
					data.get(i).getUname(),
					data.get(i).getMid(),
					data.get(i).getAid(),
					data.get(i).getTo_tid(),
					data.get(i).getTo_tname(),
					data.get(i).getContent(),
					data.get(i).getTalktime()
			));
		}
	}


	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		ViewHolder vHolder = null;
		if (convertView == null) {
			vHolder = new ViewHolder();
			convertView = mInflater.inflate(R.layout.item_talk_bbs, null);
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
				MyLog.i(TAG,"size: "+ bbsList.size()+" name: "+bbsList.get(position).getUname());

			}
		});
		vHolder.linearLayout.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				MyLog.i(TAG,"linearLayout position: "+ position);

				if (mOnChildAdapterListener != null){
					mOnChildAdapterListener.talkFeed(bbsList.get(position));
				}
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

	//二级回复监听事件
	private onChildAdapterListener mOnChildAdapterListener = null;
	public interface onChildAdapterListener{
		void talkFeed(TalkFeed talkFeed);
	}

	public void setOnChildAdapterListener(onChildAdapterListener m){
		mOnChildAdapterListener = m;
	}

}
