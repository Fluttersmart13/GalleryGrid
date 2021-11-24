package com.example.gallerygrid;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    List<GalleryModel> list = new ArrayList<>();
    RecyclerView recyclerView;
    MyListAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        callApi();
    }

    private void callApi() {
        final ProgressDialog dialog = new ProgressDialog(MainActivity.this);
        dialog.setMessage("Loading...");
        dialog.setCancelable(false);
        dialog.show();
        try {
            RequestQueue MyRequestQueue = Volley.newRequestQueue(this);

            String url = "https://api.unsplash.com/search/photos?query=rocket&per_page=40&client_id=3LG8LwNcmXYZQCplMqCw93lAlSu52v2QecSbAe-xWj8"; //
            StringRequest MyStringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        JSONObject json = new JSONObject(response);
                        JSONArray result = json.getJSONArray("results");

                        if(result.length ()> 0){
                            for(int countItem = 0;countItem<result.length();countItem++){
                                JSONObject arrayObject = result.getJSONObject(countItem);
                                JSONObject urls = arrayObject.getJSONObject("urls");
                                GalleryModel model = new GalleryModel();
                                model.setId(arrayObject.getString("id"));
                                model.setRegular(urls.getString("regular"));
                                model.setThumb(urls.getString("thumb"));
                                model.setWidth(Integer.parseInt(arrayObject.getString("width")));
                                model.setHeight(Integer.parseInt(arrayObject.getString("height")));
                                model.setAlt_description(arrayObject.getString("alt_description"));
                                list.add(model);
                                LoadList(list);
                            }

                        }
                        if (dialog.isShowing()) {
                            dialog.dismiss();
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                        if (dialog.isShowing()) {
                            dialog.dismiss();
                        }
                    }
                }
            }, new Response.ErrorListener() { //Create an error listener to handle errors appropriately.
                @Override
                public void onErrorResponse(VolleyError error) {
                    if (dialog.isShowing()) {
                        dialog.dismiss();
                    }
                    Toast.makeText(MainActivity.this, "Check Network Connection", Toast.LENGTH_SHORT).show();
                }
            })
            {
                protected Map<String, String> getParams() {
                    Map<String, String> MyData = new HashMap<String, String>();
                    return MyData;
                }
            };
            MyStringRequest.setRetryPolicy(new DefaultRetryPolicy(
                    300000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            MyRequestQueue.add(MyStringRequest);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void LoadList(List<GalleryModel> list) {
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(this,2));
        adapter = new MyListAdapter(MainActivity.this, this.list);
        recyclerView.setAdapter(adapter);
    }
}