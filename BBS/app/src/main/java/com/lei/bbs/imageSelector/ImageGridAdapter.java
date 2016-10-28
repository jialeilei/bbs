package com.lei.bbs.imageSelector;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.widget.ImageView;
import com.lei.bbs.R;
import com.lei.bbs.adapter.CommonAdapter;
import com.lei.bbs.bean.ViewHolder;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by lei on 2016/10/27.
 */

public class ImageGridAdapter extends CommonAdapter<String>{

    private static Set<String> mSelectedImage = new HashSet<String>();
    private String mDirPath;

    public ImageGridAdapter(Context context, List<String> datas, String dirPath) {
        super(context, datas,R.layout.item_grid_image);
        this.mDirPath = dirPath;
    }


    @Override
    public void convert(ViewHolder viewHolder, String item, int position) {

        final ImageView img = viewHolder.getView(R.id.imgView);
        final ImageView imgSelected = viewHolder.getView(R.id.imgSelect);

        img.setColorFilter(null);
        img.setImageResource(R.mipmap.image_no);
        imgSelected.setImageResource(R.mipmap.select_no);

        LocalImageLoader.getInstance(2, LocalImageLoader.Type.LIFO)
                .loadImage(mDirPath + "/" + item, img);
        final String filePath = mDirPath+"/"+item;
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mSelectedImage.contains(filePath)) {//have been selected
                    mSelectedImage.remove(filePath);
                    img.setColorFilter(null);
                    imgSelected.setImageResource(R.mipmap.select_no);
                } else {
                    mSelectedImage.add(filePath);
                    img.setColorFilter(Color.parseColor("#77000000"));
                    imgSelected.setImageResource(R.mipmap.select_yes);
                }
            }
        });

        if (mSelectedImage.contains(filePath)){
            img.setColorFilter(Color.parseColor("#77000000"));
            imgSelected.setImageResource(R.mipmap.select_yes);
        }

    }


}
