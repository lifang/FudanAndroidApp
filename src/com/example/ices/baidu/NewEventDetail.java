package com.example.ices.baidu;

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

import com.examlpe.ices.util.LoadingDialog;
import com.examlpe.ices.util.StringUtil;
import com.example.ices.Config;
import com.example.ices.MyApplication;
import com.example.ices.R;
import com.example.ices.activity.LoginActivity;
import com.example.ices.baidu.NewEventDetail.AsyncImageLoader.ImageCallback;
import com.example.ices.entity.EventDetailEneity;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.Drawable;
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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

 



/***
 * 
 * 说明：ViewPager ，带小圆点导航，适配器采用PagerAdapter，基本可以满足需求
 * 也可以采用FragmentPagerAdapter，有人说，Fragment可以更好的适应平板和手机，
 * 并且可以更好的代码重用，具体这些好处大家试一下就知道了。
 * 
 * @author andylaw
 * 
 * 更多内容请查看博客：http://blog.csdn.net/lyc66666666666
 * 
 */

@SuppressLint("NewApi")
public class NewEventDetail extends Activity {

	private ViewPager view_pager;
    private Dialog loadingDialog;
	private LayoutInflater inflater;

 
	private ImageView image;
	private View item ;
	private MyAdapter adapter ;
	private ImageView[] indicator_imgs;//存放引到图片数组
	private ArrayList<String> ma = new ArrayList<String>();
	private ArrayList<String> mal = new ArrayList<String>();
	String url;
	private int id;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.event_detail);
	 
		view_pager = (ViewPager) findViewById(R.id.view_pager);
		id=getIntent().getIntExtra("id", 0);
		url=	Config.getEvent;
		getdata(url);
 
	}

	private void getdata(String url) {
		loadingDialog = LoadingDialog.getLoadingDialg(this);
		loadingDialog.show();
 
		RequestParams params = new RequestParams();
		params.put("id",id);
		params.put("studentId",MyApplication.currentUser.getStudentId());
		params.put("token", MyApplication.getToken()); 
		params.setUseJsonStreamer(true);
		System.out.println("MyApplication.currentUser.getStudentId()---"+id);
		 
		System.out.println("MyApplication.getToken()---"+MyApplication.getToken());
		System.out.println("params--"+MyApplication.currentUser.getStudentId()+"---"+ MyApplication.getToken());
		
		MyApplication.getInstance().getClient().post( url, params, new AsyncHttpResponseHandler() {

			@Override
			public void onSuccess(int statusCode, Header[] headers,
					byte[] responseBody) {
				if (loadingDialog != null) {
					loadingDialog.dismiss();
				}
				String userMsg = new String(responseBody).toString();
	 
				Log.i("ljp", userMsg);
				Gson gson = new Gson();
				//EventEntity
				JSONObject jsonobject = null;
				int code = 0;
				try {
					jsonobject = new JSONObject(userMsg);
					code = jsonobject.getInt("code");
					if(code==-2){
						Intent i =new Intent(getApplication(),LoginActivity.class);
						startActivity(i);
					}else if(code==0){
						EventDetailEneity ee = gson.fromJson(jsonobject.getString("result"),
 		 					new TypeToken<EventDetailEneity>() {
 							}.getType());
						
//						eventsFinshTime.setText(StringUtil.timeUtil(ee.getEventsStartTime())+".-"+StringUtil.timeUtil(ee.getEventsFinshTime()) );
//						finishTime=ee.getEventsFinshTime();
//						creat_tv.setText("Creattime:"+ee.getCreateTime());
//						name.setText(ee.getEventsName());
//						tv_detail.setText(ee.getEventsIntroduction());
//						location.setText(ee.getEventsAddress());
//						tv_tel.setText(ee.getEventsPhone());
//					 	isjion=ee.getEventsIsJoin();
//					 	tv_cost.setText(ee.getEventsCostMoney()+"");
//					 	if(isjion==2){
//					 	 	ll_join.setClickable(false);
//					 		 tv_isjoin.setText("Joined");
//					 		ll_join.setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_finish_dra));
//					 	}
						for(int i=0;i<ee.getPictures().size();i++){
							ma.add(ee.getPictures().get(i).getPictureLargeFilePath());
						//	ma.add("http://su.bdimg.com/static/superplus/img/logo_white_ee663702.png");
							mal.add(ee.getPictures().get(i).getPictureLargeFilePath());
						}
						indicator_imgs = new ImageView[ma.size()];
						List<View> list = new ArrayList<View>();
						inflater = LayoutInflater.from(NewEventDetail.this);
						for (int i = 0; i < 7; i++) {
							item = inflater.inflate(R.layout.item, null);
							list.add(item);
						}
						adapter = new MyAdapter(list);
						view_pager.setAdapter(adapter);
						view_pager.setOnPageChangeListener(new MyListener());
						indicator_imgs = new ImageView[ma.size()];
						initIndicator();
					}else{
						Toast.makeText(getApplicationContext(), jsonobject.getString("message"),
								Toast.LENGTH_SHORT).show();
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				 
				
			}

			@Override
			public void onFailure(int statusCode, Header[] headers,
					byte[] responseBody, Throwable error) {
				// TODO Auto-generated method stub
				if (loadingDialog != null) {
					loadingDialog.dismiss();
				}
				Toast.makeText(getApplicationContext(),getResources().getString(R.string.no_internet),
						Toast.LENGTH_SHORT).show();
			}
		});

	
	
	}
	
	/**
	 * 初始化引导图标
	 * 动态创建多个小圆点，然后组装到线性布局里
	 */
	private void initIndicator(){
		
		ImageView imgView;
		View v = findViewById(R.id.indicator);// 线性水平布局，负责动态调整导航图标
		
		for (int i = 0; i < 7; i++) {
			imgView = new ImageView(this);
			LinearLayout.LayoutParams params_linear = new LinearLayout.LayoutParams(10,10);
			params_linear.setMargins(7, 10, 7, 10);
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

		
		private AsyncImageLoader asyncImageLoader;
		
		public MyAdapter(List<View> list) {
			mList = list;
			asyncImageLoader = new AsyncImageLoader();  
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
			

			Drawable cachedImage = asyncImageLoader.loadDrawable(
					ma.get(position), new ImageCallback() {

						public void imageLoaded(Drawable imageDrawable,
								String imageUrl) {

						 	View view = mList.get(position);
							image = ((ImageView) view.findViewById(R.id.image));
						///	image.setBackground(imageDrawable);
							image.setBackgroundDrawable(imageDrawable);
							container.removeView(mList.get(position));
							container.addView(mList.get(position));
							// adapter.notifyDataSetChanged();

						}
					});

			View view = mList.get(position);
			image = ((ImageView) view.findViewById(R.id.image));
			//image.setBackground(cachedImage);
			image.setBackgroundDrawable(cachedImage);
			container.removeView(mList.get(position));
			container.addView(mList.get(position));
		 
				

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
