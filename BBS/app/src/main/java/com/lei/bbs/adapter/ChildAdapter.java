package com.lei.bbs.adapter;


import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.lei.bbs.R;
import com.lei.bbs.bean.TalkFeed;
import com.lei.bbs.bean.ViewHolder;
import com.lei.bbs.util.MyLog;
import java.util.ArrayList;
import java.util.List;

/**
 *
 */

public class ChildAdapter extends CommonAdapter<TalkFeed>{

	private List<TalkFeed>  bbsList = new ArrayList<TalkFeed>();
	private String TAG = "ChildAdapter";


	public ChildAdapter(Context context, List<TalkFeed> data) {

		super(context,data,R.layout.item_talk_bbs);
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
	public void convert(ViewHolder viewHolder, final TalkFeed item, final int position) {
		TextView tvFirst = viewHolder.getView(R.id.tvFirst);
		TextView tvAnswer = viewHolder.getView(R.id.tvAnswer);
		TextView tvSecond = viewHolder.getView(R.id.tvSecond);
		LinearLayout linearLayout = viewHolder.getView(R.id.ll);

		viewHolder.setText(R.id.tvContent,item.getContent());
		tvFirst.setText(item.getUname());


		if (item.getTo_tid() == 0){
			tvSecond.setVisibility(View.GONE);
			tvAnswer.setVisibility(View.GONE);
		}else {
			tvSecond.setVisibility(View.VISIBLE);
			tvAnswer.setVisibility(View.VISIBLE);
			tvSecond.setText(item.getTo_tname());
		}

		tvFirst.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				MyLog.i(TAG,"textView position: "+ position);
				MyLog.i(TAG,"size: "+ bbsList.size()+" name: "+item.getUname());

			}
		});
		linearLayout.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				MyLog.i(TAG,"linearLayout position: "+ position);

				if (mOnChildAdapterListener != null){
					mOnChildAdapterListener.talkFeed(item);
				}
			}
		});
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
