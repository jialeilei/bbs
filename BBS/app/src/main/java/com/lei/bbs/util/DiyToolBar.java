package com.lei.bbs.util;


import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;
import com.lei.bbs.R;


public class DiyToolBar extends FrameLayout {
    private ViewGroup content;
    private ImageButton imgLeft;
    private TextView tvCenter;
    private ImageButton imgRight;
    private TextView tvLeft;
    private TextView tvRight;

    public DiyToolBar(Context context) {
        this(context, null);
    }

    public DiyToolBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DiyToolBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
       /* final TintTypedArray a = TintTypedArray.obtainStyledAttributes(getContext(), attrs,
                R.styleable.MyToolBar, defStyleAttr, 0);*/

        this.content = (ViewGroup) LayoutInflater.from(getContext()).inflate(R.layout.toolbar, this, true);
        this.tvCenter = (TextView) content.findViewById(R.id.tvCenter);
        this.imgLeft = (ImageButton) content.findViewById(R.id.imgLeft);
        this.tvLeft = (TextView) content.findViewById(R.id.tvLeft);
        this.imgRight = (ImageButton) content.findViewById(R.id.imgRight);
        this.tvRight = (TextView) content.findViewById(R.id.tvRight);

       /* if (a.getBoolean(R.styleable.MyToolBar_disableRightView, false)) {
            disableRight();
        }
        boolean enableLeftView = a.getBoolean(R.styleable.MyToolBar_enableTvLeft, true);
        if (enableLeftView) {
            enableLeftTextView();
        } else {
            disableLeftTextView();
        }
        if (a.getBoolean(R.styleable.MyToolBar_enableTvRight, false)) {
            enableRightTextView();
        } else {
            disableRightTextView();
        }
        if (a.getBoolean(R.styleable.MyToolBar_enableGoBackEvent, false) && enableLeftView) {
            this.tvLeft.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (getContext() instanceof Activity) {
                        ((Activity) getContext()).finish();
                    }
                }
            });
        }
        a.recycle();*/
    }


    public void setImgRight(@Nullable Drawable icon) {
        this.imgRight.setImageDrawable(icon);
    }

    @Nullable
    public Drawable getNavigationIcon() {
        return imgRight != null ? imgRight.getDrawable() : null;
    }

    @Nullable
    public CharSequence getNavigationContentDescription() {
        return imgRight != null ? imgRight.getContentDescription() : null;
    }

    public void setNavigationContentDescription(@Nullable CharSequence description) {
        if (imgRight != null) {
            imgRight.setContentDescription(description);
        }
    }

    public void setRightImgListener(OnClickListener listener) {
        imgRight.setOnClickListener(listener);
    }

    public void setNavigationContentDescription(@StringRes int resId) {
        setNavigationContentDescription(resId != 0 ? getContext().getText(resId) : null);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
    }

    public void setTitle(@StringRes int resId) {
        tvCenter.setText(resId);
    }

    public void setTitle(String title) {
        tvCenter.setText(title);
    }

    public ImageButton getImgRight() {
        return imgRight;
    }

    public ImageButton getImgLeft() {
        return imgLeft;
    }

    public TextView getTvCenter() {
        return tvCenter;
    }

    public TextView getTvLeft() {
        return this.tvLeft;
    }

    public TextView getTvRight() {
        return tvRight;
    }


    //left
    public void enableLeftTextView() {
        this.imgLeft.setVisibility(View.GONE);
        this.tvLeft.setVisibility(View.VISIBLE);
    }

    public void disableLeftTextView() {
        this.imgLeft.setVisibility(View.VISIBLE);
        this.tvLeft.setVisibility(View.GONE);
        this.tvLeft.setOnClickListener(null);
    }

    public void enableLeft(){
        this.imgLeft.setVisibility(View.VISIBLE);
        this.tvLeft.setVisibility(View.VISIBLE);
    }



    //right
    public void enableRightTextView() {
        this.imgRight.setVisibility(View.GONE);
        this.tvRight.setVisibility(View.VISIBLE);
    }

    public void disableRightTextView() {
        this.imgRight.setVisibility(View.VISIBLE);
        this.tvRight.setVisibility(View.GONE);
    }

    public void disableRight() {
        this.imgRight.setVisibility(View.GONE);
        this.tvRight.setVisibility(View.GONE);
    }

    public void enableRight() {
        this.imgRight.setVisibility(VISIBLE);
        this.tvRight.setVisibility(VISIBLE);
    }

}
