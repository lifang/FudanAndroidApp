package com.example.ices.activity;

import java.io.IOException;
import java.lang.ref.SoftReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreConnectionPNames;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.examlpe.ices.util.DialogUtil;
import com.examlpe.ices.util.DialogUtil.CallBackChange;
import com.examlpe.ices.util.ImageCacheUtil;
import com.examlpe.ices.util.LoadingDialog;
import com.examlpe.ices.util.TitleMenuUtil;
 
import com.example.ices.BaseActivity;
import com.example.ices.Config;
import com.example.ices.MyApplication;
import com.example.ices.R;
import com.example.ices.activity.PreDetail.AsyncImageLoader;
 
 
import com.example.ices.activity.PreDetail.AsyncImageLoader.ImageCallback;
import com.example.ices.entity.AroundDetailEntity;
import com.example.ices.entity.EventDetailEneity;
import com.example.ices.entity.EventEntity;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

public class ImgShow extends BaseActivity{
	private int id;
	private TextView eventsFinshTime,tv_detail,name,creat_tv,location,tv_time,tv_tel2;
	private LinearLayout ll_join;
	private ImageView image;
	private RelativeLayout ri_tel;
	private ArrayList<String> ma = new ArrayList<String>();
	private ViewPager view_pager;
	private MyAdapter adapter ;
	private ImageView[] indicator_imgs  ;//存放引到图片数组
	private View item ;
	private LayoutInflater inflater;
	private RelativeLayout rl_imgs,rela_loc;
	private int  index_ima=0;
	private int index=0;
    private Dialog loadingDialog;
	private String phoneNumber,locName;
	private float lat,lng;
	List<View> list = new ArrayList<View>();
	private Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 0:
				/**
				 * 创建多个item （每一条viewPager都是一个item）
				 * 从服务器获取完数据（如文章标题、url地址） 后，再设置适配器
				 */
 
				for (int i = 0; i <ma.size(); i++) {			 
					item = inflater.inflate(R.layout.item, null);
					list.add(item);
				}
			 
				
				indicator_imgs	= new ImageView[ma.size()];
			 
				initIndicator();
				 
				adapter.notifyDataSetChanged();
				view_pager.setCurrentItem(index);
				 
				break;
			case 1:
				Toast.makeText(getApplicationContext(), (String) msg.obj,
						Toast.LENGTH_SHORT).show();
				break;
			case 2: // 网络有问题
				Toast.makeText(getApplicationContext(), "网络未连接",
						Toast.LENGTH_SHORT).show();
				break;
			case 3:
			 
				break;
			case 4:
			 
				break;
			}
		}
	};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.imgshow);
		new TitleMenuUtil(ImgShow.this, "").show();
		 
		innitView();
		getdata();
		//DialogUtil
	}
	private void innitView() {
		// TODO Auto-generated method stub
		
		// p = new URLImageParser(more_tv_detail, EventDetail.this);
	 
		view_pager = (ViewPager) findViewById(R.id.view_pager);
		 
		inflater = LayoutInflater.from(this);
		adapter = new MyAdapter(list);
		 
		view_pager.setAdapter(adapter);
		//绑定动作监听器：如翻页的动画
		view_pager.setOnPageChangeListener(new MyListener());
	 
	}
	private void getdata() { 
		//
		index = getIntent().getIntExtra("index", 0);
		ma=getIntent().getStringArrayListExtra("mal");
 		for(int i=0;i<ma.size();i++){
 			 System.out.println("ma-l"+ma.get(i));
 		}
 		 
		handler.sendEmptyMessage(0);
	}
private void initIndicator(){
		
		ImageView imgView;
		View v = findViewById(R.id.indicator);// 线性水平布局，负责动态调整导航图标
		
		for (int i = 0; i < ma.size(); i++) {
			imgView = new ImageView(this);
			LinearLayout.LayoutParams params_linear = new LinearLayout.LayoutParams(10,10);
			params_linear.setMargins(7, 0, 7, 20);
			imgView.setLayoutParams(params_linear);
			indicator_imgs[i] = imgView;
			
			if (i == 0) { // 初始化第一个为选中状态
				
				indicator_imgs[i].setBackgroundResource(R.drawable.indicator_focused);
			} else {
				indicator_imgs[i].setBackgroundResource(R.drawable.indicator);
			}
			((ViewGroup)v).addView(indicator_imgs[i]);
		}
		
	}
	
	
	
	
	/**
	 * 适配器，负责装配 、销毁  数据  和  组件 。
	 */
	private class MyAdapter extends PagerAdapter {

		private List<View> mList;
		private int index ;
		
		private AsyncImageLoader asyncImageLoader;
		
		public MyAdapter(List<View> list) {
			mList = list;
			asyncImageLoader = new AsyncImageLoader();  
		}

		
		
		public int getIndex() {
			return index;
		}



		public void setIndex(int index) {
			this.index = index;
		}



		/**
		 * Return the number of views available.
		 */
		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return mList.size();
		}

		
		/**
		 * Remove a page for the given position.
		 * 滑动过后就销毁 ，销毁当前页的前一个的前一个的页！
		 * instantiateItem(View container, int position)
		 * This method was deprecated in API level . Use instantiateItem(ViewGroup, int)
		 */
		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			// TODO Auto-generated method stub
			container.removeView(mList.get(position));
			
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			// TODO Auto-generated method stub
			return arg0==arg1;
		}

		
		/**
		 * Create the page for the given position.
		 */
		@Override
		public Object instantiateItem(final ViewGroup container, final int position) {
			loadingDialog = LoadingDialog.getLoadingDialg(ImgShow.this);
			loadingDialog.show();
			AsyncHttpClient client = new AsyncHttpClient(); // 
			View view = mList.get(position);
			image = ((ImageView) view.findViewById(R.id.image));
			client.setTimeout(6000);
			client.post(ma.get(position), new AsyncHttpResponseHandler(){  
		           @Override  
		           public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {  
		        		if (loadingDialog != null) {
							loadingDialog.dismiss();
						}
		               if(statusCode==200){  
		                   BitmapFactory factory=new BitmapFactory();  
		                   Bitmap bitmap=factory.decodeByteArray(responseBody, 0, responseBody.length);  
		                   	 image.setImageBitmap(bitmap);  
		                 //  BitmapDrawable bd= new BitmapDrawable(getResources(), bitmap);  
		                   System.out.println("1231231231");
		               //    image.setBackgroundDrawable(bd);
		               }  
		           }  
		             
		           @Override  
		           public void onFailure(int statusCode, Header[] headers,  
		                   byte[] responseBody, Throwable error) {  
		               error.printStackTrace();  
		           	if (loadingDialog != null) {
						loadingDialog.dismiss();
					}
		        	Toast.makeText(getApplicationContext(),getResources().getString(R.string.no_internet),
							Toast.LENGTH_SHORT).show();
		           }  
		       });  
 		
 		
 		
 		
 		
 		
			container.removeView(mList.get(position));
			container.addView(mList.get(position));
			setIndex(position);
			image.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
//					 Toast.makeText(getApplicationContext(), index_ima+"----", 1000).show();
//					 Intent i=new Intent(ImgShow.this,ImgShow.class);
//					 i.putExtra("image_url", ma.get(index_ima));
//					 startActivityForResult(i, 9);
				}
			});
		  
			
			
			return mList.get(position);
		}
		
	
	}
	
	
	/**
	 * 动作监听器，可异步加载图片
	 *
	 */
	private class MyListener implements OnPageChangeListener{

		@Override
		public void onPageScrollStateChanged(int state) {
			// TODO Auto-generated method stub
			if (state == 0) {
				//new MyAdapter(null).notifyDataSetChanged();
			}
		}

		
		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onPageSelected(int position) {
			
			// 改变所有导航的背景图片为：未选中
			for (int i = 0; i < indicator_imgs.length; i++) {
				
				indicator_imgs[i].setBackgroundResource(R.drawable.indicator);
				 
			}
			
			// 改变当前背景图片为：选中
			index_ima=position;
			indicator_imgs[position].setBackgroundResource(R.drawable.indicator_focused);
		}
		
		
	}
	
	

	/**
	 * 异步加载图片
	 */
	static class AsyncImageLoader {

		// 软引用，使用内存做临时缓存 （程序退出，或内存不够则清除软引用）
		private HashMap<String, SoftReference<Drawable>> imageCache;

		public AsyncImageLoader() {
			imageCache = new HashMap<String, SoftReference<Drawable>>();
		}

		/**
		 * 定义回调接口
		 */
		public interface ImageCallback {
			public void imageLoaded(Drawable imageDrawable, String imageUrl);
		}

		
		/**
		 * 创建子线程加载图片
		 * 子线程加载完图片交给handler处理（子线程不能更新ui，而handler处在主线程，可以更新ui）
		 * handler又交给imageCallback，imageCallback须要自己来实现，在这里可以对回调参数进行处理
		 *
		 * @param imageUrl ：须要加载的图片url
		 * @param imageCallback：
		 * @return
		 */
		public Drawable loadDrawable(final String imageUrl,
				final ImageCallback imageCallback) {
			
			//如果缓存中存在图片  ，则首先使用缓存
			if (imageCache.containsKey(imageUrl)) {
				SoftReference<Drawable> softReference = imageCache.get(imageUrl);
				Drawable drawable = softReference.get();
				if (drawable != null) {
					imageCallback.imageLoaded(drawable, imageUrl);//执行回调
					return drawable;
				}
			}

			/**
			 * 在主线程里执行回调，更新视图
			 */
			final Handler handler = new Handler() {
				public void handleMessage(Message message) {
					imageCallback.imageLoaded((Drawable) message.obj, imageUrl);
				}
			};

			
			/**
			 * 创建子线程访问网络并加载图片 ，把结果交给handler处理
			 */
			new Thread() {
				@Override
				public void run() {
					Drawable drawable = loadImageFromUrl(imageUrl);
					// 下载完的图片放到缓存里
					imageCache.put(imageUrl, new SoftReference<Drawable>(drawable));
					Message message = handler.obtainMessage(0, drawable);
					handler.sendMessage(message);
				}
			}.start();
			
			return null;
		}

		
		/**
		 * 下载图片  （注意HttpClient 和httpUrlConnection的区别）
		 */
		public Drawable loadImageFromUrl(String url) {

			try {
				HttpClient client = new DefaultHttpClient();
				client.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 1000*15);
				HttpGet get = new HttpGet(url);
				HttpResponse response;

				response = client.execute(get);
				if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
					HttpEntity entity = response.getEntity();

					Drawable d = Drawable.createFromStream(entity.getContent(),
							"src");

					return d;
				} else {
					return null;
				}
			} catch (ClientProtocolException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}

			return null;
		}

		//清除缓存
		public void clearCache() {

			if (this.imageCache.size() > 0) {

				this.imageCache.clear();
			}

		}

	}
}

