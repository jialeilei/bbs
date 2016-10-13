package com.lei.bbs.adapter;


import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.lei.bbs.R;
import com.lei.bbs.bean.AnswerFeed;
import com.lei.bbs.bean.TalkFeed;
import com.lei.bbs.util.ListLinearLayout;
import com.lei.bbs.util.MyLog;
import java.util.ArrayList;
import java.util.List;


public class DetailAdapter extends BaseObjectListAdapter{
	private String TAG = "DetailAdapter";
	List<AnswerFeed> bbsList = new ArrayList<AnswerFeed>();
	private List<TalkFeed> mChildList = new ArrayList<TalkFeed>();
	private List<TalkFeed> mChildList2 = new ArrayList<TalkFeed>();
	public static int mParentItem = -1;
	public static boolean mbShowChild = false;


	public DetailAdapter(Context context, List<AnswerFeed> bbsList,List<TalkFeed> childList){
		super(context,bbsList);
		this.bbsList=bbsList;
		this.mChildList = childList;
		//initData();

	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		final AnswerFeed bbs = (AnswerFeed) getData().get(position);
		//List<AnswerFeed> bbsList = (List<AnswerFeed>) getData();
		View view;
		final ViewHolder viewHolder;
		if (convertView == null) {
			view = mInflater.from(mContext).inflate(R.layout.item_detail_bbs, null);

			viewHolder = new ViewHolder();
			viewHolder.tvName = (TextView)view.findViewById(R.id.tvName);
			viewHolder.tvLevel = (TextView) view.findViewById(R.id.tvLevel);
			viewHolder.tvTopFloor = (TextView) view.findViewById(R.id.tvTopFloor);
			viewHolder.tvFloorNum = (TextView) view.findViewById(R.id.tvFloorNumber);
			viewHolder.tvSendTime = (TextView)view.findViewById(R.id.tvSendTime);
			viewHolder.tvTitle = (TextView) view.findViewById(R.id.tvTitle);
			viewHolder.tvContent = (TextView)view.findViewById(R.id.tvMainContent);
			viewHolder.imgSex = (ImageView) view.findViewById(R.id.imgSex);
			viewHolder.imageView = (ImageView)view.findViewById(R.id.imgHead);
			viewHolder.llTalk = (LinearLayout) view.findViewById(R.id.llTalk);
			viewHolder.imgBtnAnswer = (ImageButton) view.findViewById(R.id.imgBtnAnswer);
			// 子View
			viewHolder.listLinearLayout = (ListLinearLayout) view.findViewById(R.id.listLinearLayout);
			view.setTag(viewHolder);//存储viewHolder对象
		}else {
			view = convertView;
			viewHolder = (ViewHolder)view.getTag();
		}

		viewHolder.tvName.setText(bbs.getName());
		viewHolder.tvLevel.setText("LV."+bbs.getScore());
		viewHolder.tvSendTime.setText(bbs.getSendTime());
		viewHolder.tvContent.setText(bbs.getContent());

		//是否显示标题、楼主
		if (position <= 0){
			viewHolder.tvTitle.setVisibility(View.VISIBLE);
			viewHolder.tvTitle.setText(bbs.getTitle());
			viewHolder.tvTopFloor.setVisibility(View.VISIBLE);
			viewHolder.tvFloorNum.setVisibility(View.GONE);
			viewHolder.imgBtnAnswer.setVisibility(View.GONE);
		}else {
			viewHolder.tvTitle.setVisibility(View.GONE);
			viewHolder.tvTopFloor.setVisibility(View.INVISIBLE);
			viewHolder.tvFloorNum.setVisibility(View.VISIBLE);
			viewHolder.imgBtnAnswer.setVisibility(View.VISIBLE);
			viewHolder.tvFloorNum.setText("第"+position+"层");

			if (bbsList.get(position).getUid() == bbsList.get(0).getUid()){
				viewHolder.tvTopFloor.setVisibility(View.VISIBLE);
			}
		}

		//性别
		if (bbs.getSex().equals("boy")){
			viewHolder.imgSex.setImageResource(R.mipmap.boy);
		}else {
			viewHolder.imgSex.setImageResource(R.mipmap.girl);
		}

		//处理楼中楼事务
		if (mChildList2.size() > 0){
			mChildList2.clear();
		}

		for (int i = 0;i < mChildList.size();i++){
			if (bbsList.get(position).getAid() == mChildList.get(i).getAid()){
				mChildList2.add(mChildList.get(i));
			}
			//MyLog.i(TAG,"i: "+i+" bbs aid: "+bbsList.get(position).getAid()+" child list aid: "+mChildList.get(i).getAid());
		}

		if (mChildList2.size() > 0){

			ChildAdapter tempAdapt = new ChildAdapter(mContext, mChildList2);
			viewHolder.listLinearLayout.setAdapter(tempAdapt);
			viewHolder.llTalk.setVisibility(View.VISIBLE);
			tempAdapt.setOnChildAdapterListener(new ChildAdapter.onChildAdapterListener() {
				@Override
				public void talkFeed(TalkFeed talkFeed) {
					if (mOnAnswerFeedListener != null){
						mOnAnswerFeedListener.talkFeed(talkFeed);
					}
				}
			});
		}else {
			viewHolder.llTalk.setVisibility(View.GONE);
		}

		//回复按钮的监听
		viewHolder.imgBtnAnswer.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				MyLog.i(TAG, " imageButton click ");
				if (mOnAnswerFeedListener != null){
					mOnAnswerFeedListener.answerFeed(bbs);
				}
			}
		});

		return view;
	}

	private void dealChildAdapter(){

	}

	private  class  ViewHolder{
		TextView tvName,tvLevel,tvTopFloor,tvFloorNum,tvSendTime,tvTitle,tvContent;
		ImageView imageView,imgSex;
		ImageButton imgBtnAnswer;
		LinearLayout llTalk;
		ListLinearLayout listLinearLayout;
	}

	/*private OnAdapterRefreshListener adapterRefreshListener;

	public interface OnAdapterRefreshListener{
		void onRefresh();
	}

	public void setAdapterRefreshListener(OnAdapterRefreshListener m){
		adapterRefreshListener = m;
	}
*/

	private OnAnswerFeedListener mOnAnswerFeedListener;

	public interface OnAnswerFeedListener{
		void answerFeed(AnswerFeed answerFeed);
		void talkFeed(TalkFeed talkFeed);
	}

	public void setAnswerFeedListener(OnAnswerFeedListener m){
		this.mOnAnswerFeedListener = m;
	}


}
