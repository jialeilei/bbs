package com.lei.bbs.util;


import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.database.DataSetObserver;
import android.graphics.Color;
import android.os.Build;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.lei.bbs.R;

public class ListLinearLayout extends LinearLayout {
	private BaseAdapter adapter;
	private int maxItemCount=10;
	private int moreTextColor;
	private int moreBackgroundColor;
	private OnClickListener textMoreOnClickListener;
	
	@SuppressLint("NewApi")
	public ListLinearLayout(Context context, AttributeSet attrs,
			int defStyleAttr, int defStyleRes) {
		super(context, attrs, defStyleAttr, defStyleRes);
		init(context, attrs);
	}

	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	public ListLinearLayout(Context context, AttributeSet attrs,int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init(context, attrs);
	}

	public ListLinearLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context, attrs);
	}

	

	public ListLinearLayout(Context context) {
		super(context);
		init(context,null);
	}
	private void init(Context context, AttributeSet attrs) {
		setOrientation(VERTICAL);
		if(attrs!=null){
			final TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.ListLinearLayout);
			maxItemCount=a.getInt(R.styleable.ListLinearLayout_maxItemCount, maxItemCount);
			moreTextColor=a.getColor(R.styleable.ListLinearLayout_moreTextColor,context.getResources().getColor(R.color.title_blue));
			moreBackgroundColor=a.getColor(R.styleable.ListLinearLayout_moreBackgroundColor,Color.parseColor("#ebebeb"));
			a.recycle();
		}
		
	}

	public BaseAdapter getAdapter() {
		return adapter;
	}

	public void setAdapter(BaseAdapter adapter) {
		if(this.adapter!=null){
			this.adapter.unregisterDataSetObserver(observer);
		}
		this.adapter = adapter;
		if(adapter==null){
			removeAllViews();
		}else{
			adapter.registerDataSetObserver(observer);
			adapter.notifyDataSetChanged();
		}
	}
	
	final DataSetObserver observer = new DataSetObserver() {
		@Override
		public void onChanged() {
			synchronized (this) {
				removeAllViews();
				int count=Math.min(maxItemCount, adapter.getCount());
				for(int position=0;position<count;position++){
					addView(adapter.getView(position, null, ListLinearLayout.this));
				}
				if(adapter.getCount()>maxItemCount){
					TextView textView=new TextView(getContext());
					textView.setBackgroundDrawable(adapter.getView(maxItemCount, null, ListLinearLayout.this).getBackground());
					textView.setText("点击查看更多");
					textView.setTextColor(moreTextColor);
					textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
					LayoutParams params=new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
					params.gravity=Gravity.CENTER;
					int padding=(int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 5, getContext().getResources().getDisplayMetrics());
					textView.setPadding(padding, padding, padding, padding);
					textView.setGravity(Gravity.CENTER);
					addView(textView,params);
					textView.setOnClickListener(textMoreOnClickListener);
				}
			}
			
		}
	};

	public int getMaxItemCount() {
		return maxItemCount;
	}

	public void setMaxItemCount(int maxItemCount) {
		this.maxItemCount = maxItemCount;
		if(adapter!=null)adapter.notifyDataSetChanged();
	}

	public OnClickListener getTextMoreOnClickListener() {
		return textMoreOnClickListener;
	}


	/**应先于setAdapter之前调用*/
	public void setTextMoreOnClickListener(OnClickListener textMoreOnClickListener) {
		this.textMoreOnClickListener = textMoreOnClickListener;
		if(adapter!=null)adapter.notifyDataSetChanged();
	}

}
