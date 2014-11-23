
package com.cire.gridimagesearch;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;

import com.cire.gridimagesearch.modal.Client;
import com.cire.gridimagesearch.modal.Client.ClientCallback;
import com.cire.gridimagesearch.modal.Photo;
import com.commonsware.cwac.endless.EndlessAdapter;

public class SearchEndlessAdapter extends EndlessAdapter {

    private int start = 0;
    private ArrayList<Photo> mPhotos;
    private Client client;
    private RotateAnimation rotate = null;
    private View pendingView = null;
    private String keyword = "";

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

    public SearchEndlessAdapter(Context context, List<Photo> photos, int start) {
        super(new SearchAdapter(context, photos));
        this.start = start;
        client = new Client(context);
        rotate = new RotateAnimation(0f, 360f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        rotate.setDuration(600);
        rotate.setRepeatMode(Animation.RESTART);
        rotate.setRepeatCount(Animation.INFINITE);
        mPhotos = new ArrayList<Photo>();
    }

    @Override
    protected View getPendingView(ViewGroup parent) {
        View row = LayoutInflater.from(parent.getContext()).inflate(R.layout.row, null);
        pendingView = row.findViewById(R.id.throbber);
        pendingView.setVisibility(View.VISIBLE);
        startProgressAnimation();

        return row;
    }

    @Override
    protected void appendCachedData() {
        if (start <= 56 && keyword != "") {

            client.getPhotos(keyword, start, new ClientCallback() {

                @SuppressWarnings("unchecked")
                @Override
                public void onGetData(Object obj) {
                    SearchAdapter wrappedAdapter = (SearchAdapter)getWrappedAdapter();
                    mPhotos.clear();
                    mPhotos.addAll((ArrayList<Photo>)obj);
                    wrappedAdapter.addAll(mPhotos);
                    wrappedAdapter.notifyDataSetChanged();

                }
            });

            start = start + 8;

        }
    }

    @Override
    protected boolean cacheInBackground() throws Exception {
        SystemClock.sleep(500);
        return (start <= 56 && keyword != "");
    }

    void startProgressAnimation() {
        if (pendingView != null) {
            pendingView.startAnimation(rotate);
        }
    }

}
