package com.nb.myflickr;

import android.os.Bundle;


import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements GetFlickrjsonData.OnDataAvailable {
    private static final String TAG = "MainActivity";
    private FlickRecyclerViewAdapter flickRecyclerViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate: starts");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        flickRecyclerViewAdapter = new FlickRecyclerViewAdapter(this, new ArrayList<Photo>());
        recyclerView.setAdapter(flickRecyclerViewAdapter);

        Log.d(TAG, "onCreate: ends");
    }

    @Override
    protected void onResume() {
        Log.d(TAG, "onResume: starts");
        super.onResume();
        GetFlickrjsonData getFlickrjsonData = new GetFlickrjsonData(this, "https://www.flickr.com/services/feeds/photos_public.gne", "en-us", true);
        // getFlickrjsonData.executeonSameThread("android,nougat");
        getFlickrjsonData.execute("android,nougat");
        Log.d(TAG, "onResume: ends");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        Log.d(TAG, "onCreateOptionsMenu() returned: " + true);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDataAvailable(List<Photo> data, DownloadStatus status) {
        Log.d(TAG, "onDataAvailable: starts");
        if(status == DownloadStatus.OK) {
            flickRecyclerViewAdapter.loadNewData(data);
        } else {
            Log.e(TAG, "onDataAvailable: failed with status " + status);
        }
        Log.d(TAG, "onDataAvailable: ends");
    }

}
