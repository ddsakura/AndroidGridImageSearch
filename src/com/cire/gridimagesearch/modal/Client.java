
package com.cire.gridimagesearch.modal;

import java.util.ArrayList;

import org.apache.http.Header;
import org.codehaus.jackson.map.ObjectMapper;
import org.json.JSONArray;
import org.json.JSONObject;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

public class Client {
    // https://ajax.googleapis.com/ajax/services/search/images?v=1.0&q=android&rsz=8&start=0
    private static final String BASE_URL = "https://ajax.googleapis.com/ajax/services/search/images?v=1.0&rsz=8";
    private SharedPreferences sharedPreferences;

    public Client(Context context) {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public interface ClientCallback {
        public void onGetData(Object obj);
    }

    private static AsyncHttpClient client = new AsyncHttpClient();

    public static void get(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        client.get(getAbsoluteUrl(url), params, responseHandler);
    }

    public static void post(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        client.post(getAbsoluteUrl(url), params, responseHandler);
    }

    private static String getAbsoluteUrl(String relativeUrl) {
        return BASE_URL + relativeUrl;
    }

    public void getPhotos(String keyword, int start, final ClientCallback clientCallback) {
        String paramString = "&q=" + keyword + "&start=" + start;
        for (String key : sharedPreferences.getAll().keySet()) {
            paramString += "&" + key + "=" + sharedPreferences.getString(key, "");
        }
        Client.get(paramString, null, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {

                ObjectMapper mapper = new ObjectMapper();
                ArrayList<Photo> mPhotos = new ArrayList<Photo>();
                try {
                    JSONArray photosJSON = response.getJSONObject("responseData").getJSONArray("results");
                    for (int i = 0; i < photosJSON.length(); i++) {
                        JSONObject photoJSON = photosJSON.getJSONObject(i);
                        Photo photo = mapper.readValue(photoJSON.toString(), Photo.class);
                        mPhotos.add(photo);
                    }
                    clientCallback.onGetData(mPhotos);
                } catch (Exception e) {
                    clientCallback.onGetData(null);
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                clientCallback.onGetData(null);
            }
        });
    }
}
