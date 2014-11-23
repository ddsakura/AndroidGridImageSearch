
package com.cire.gridimagesearch;

import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.os.Build;
import android.text.Html;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.cire.gridimagesearch.modal.Photo;
import com.squareup.picasso.Picasso;

class SearchAdapter extends ArrayAdapter<Photo> {

    private int start;
    private String keyword;
    private int mScreenWidth;

    private int getScreenWidth(Context context) {
        int width = 0;

        try {
            WindowManager windowmanager = (WindowManager)context.getSystemService(Context.WINDOW_SERVICE);
            Display display = windowmanager.getDefaultDisplay();

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
                Point size = new Point();
                display.getSize(size);
                width = size.x;

            } else {
                width = display.getWidth();
            }

        } catch (Exception e) {
            e.printStackTrace();
            width = 0;
        }

        return width;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public SearchAdapter(Context context, List<Photo> photos) {
        super(context, 0, photos);
        mScreenWidth = getScreenWidth(context);
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return super.getCount();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final Photo photo = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_image_result, parent, false);
            convertView.getLayoutParams().width = mScreenWidth / 3;
            convertView.getLayoutParams().height = mScreenWidth / 3;

        }

        ImageView ivImage = (ImageView)convertView.findViewById(R.id.ivImage);
        TextView tvTitle = (TextView)convertView.findViewById(R.id.tvTitle);
        ivImage.setImageResource(0);
        tvTitle.setText(Html.fromHtml(photo.getTitle()));
        Picasso.with(getContext()).load(photo.getTbUrl()).into(ivImage);
        convertView.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Intent intent = new Intent();
                intent.setClass(getContext(), FullScreenActivity.class);
                intent.putExtra("url", photo.getUrl());
                getContext().startActivity(intent);
            }
        });
        return convertView;
    }
}
