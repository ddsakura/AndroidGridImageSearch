
package com.cire.gridimagesearch;

import java.util.ArrayList;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.GridView;
import android.widget.SearchView;

import com.cire.gridimagesearch.modal.Photo;

public class SearchActivity extends Activity implements SearchView.OnQueryTextListener {

    private GridView gvResult;
    private ArrayList<Photo> mPhotos;
    private SearchEndlessAdapter mPhotoAdapter;
    private SearchView mSearchView;
    private String mKeyword = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        setup();
    }

    private void setup() {
        gvResult = (GridView)findViewById(R.id.gvResult);
        gvResult.setNumColumns(3);
        mPhotos = new ArrayList<Photo>();
        mPhotoAdapter = new SearchEndlessAdapter(this, mPhotos, 0);
        mPhotoAdapter.setKeyword(mKeyword);
        gvResult.setAdapter(mPhotoAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search, menu);
        SearchManager searchManager = (SearchManager)getSystemService(Context.SEARCH_SERVICE);
        mSearchView = (SearchView)menu.findItem(R.id.action_search).getActionView();
        mSearchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        mSearchView.setOnQueryTextListener(this);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            Intent intent = new Intent();
            intent.setClass(this, PrefActivity.class);
            startActivity(intent);

            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        mKeyword = query;
        gvResult.setAdapter(null);
        setup();
        mSearchView.clearFocus();
        return true;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }
}
