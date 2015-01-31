package com.example.sample_application;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

public class GridImageDetails extends Activity {

	GridView grid_view = null;

	ArrayList<Bitmap> queue = null;
	
	Handler mHandler = null;
	BaseAdapter adapter = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_grid_image_details);

		grid_view = (GridView) findViewById(R.id.gridView1);

		
		
		queue = new ArrayList<Bitmap>();
		
		mHandler = new Handler(){
			@Override
			public void handleMessage(Message msg) {
				Log.d("Satyainder", "handling message");
				super.handleMessage(msg);
			} 
		};
				adapter = new BaseAdapter() {

			@Override
			public View getView(int position, View convertView, ViewGroup parent) {

				ImageView imageView = new ImageView(getApplicationContext());
				Log.d("Satyainder", "Consume---" + position);

				imageView.setImageBitmap(queue.get(position));
				imageView.setRotation(90);
				return imageView;
			}

			@Override
			public long getItemId(int position) {
				// TODO Auto-generated method stub
				return 0;
			}

			@Override
			public Object getItem(int position) {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public int getCount() {
				// TODO Auto-generated method stub
				return queue.size();
			}
		};
		loadData(25);
		
		grid_view.setAdapter(adapter);
		grid_view.setRotation(270);
		
		grid_view.setOnScrollListener(new OnScrollListener() {
			
			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {
				
				
			}
			
			@Override
			public void onScroll(AbsListView view, int firstVisibleItem,
					int visibleItemCount, int totalItemCount) {
				if(firstVisibleItem + visibleItemCount == totalItemCount)
				//loadData(1);
				Log.d("Satyainder", "firstVisibleItem--" +firstVisibleItem+"  visibleItemCount--"+visibleItemCount+"  totalItemCount--" + totalItemCount);
				
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.grid_image_details, menu);
		return true;
	}
	
	
	private void loadData(int N) {
		for (int i = 0; i < N; i++) {
			new Thread() {
				public void run() {
					InputStream inputStream;
					try {

						
							Log.d("Satyainder", "Gettings");
							URL url = new URL(
									"http://www.prayerdrive.org/wp-content/uploads/2013/10/road-header-image-800-14.jpg");

							HttpURLConnection connection = (HttpURLConnection) url
									.openConnection();
							// connection.setDoInput(true);
							connection.connect();

							inputStream = connection.getInputStream();
							Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
							ImageView imageView = new ImageView(
									getApplicationContext());
							imageView.setImageBitmap(bitmap); 
							queue.add(bitmap);
							mHandler.postDelayed(new Runnable() {
								
								@Override
								public void run() {
								int x = 	grid_view.getScrollX();
									int y = grid_view.getScrollY();
									//grid_view.setAdapter(adapter);
									//grid_view.n
									adapter.notifyDataSetChanged();
									grid_view.setScrollX(x);
									grid_view.setScrollY(y);
									
									Message message = mHandler.obtainMessage();
									message.sendToTarget();
								}
							}, 0);
							Log.d("Satyainder", "Got---");
						
					} catch (MalformedURLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				};
			}.start();
			}


	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
