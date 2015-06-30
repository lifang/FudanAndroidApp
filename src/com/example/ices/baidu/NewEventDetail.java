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
 * ˵����ViewPager ����СԲ�㵼��������������PagerAdapter������������������
 * Ҳ���Բ���FragmentPagerAdapter������˵��Fragment���Ը��õ���Ӧƽ����ֻ���
 * ���ҿ��Ը��õĴ������ã�������Щ�ô������һ�¾�֪���ˡ�
 * 
 * @author andylaw
 * 
 * ����������鿴���ͣ�http://blog.csdn.net/lyc66666666666
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
	private ImageView[] indicator_imgs;//�������ͼƬ����
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
	 * ��ʼ������ͼ��
	 * ��̬�������СԲ�㣬Ȼ����װ�����Բ�����
	 */
	private void initIndicator(){
		
		ImageView imgView;
		View v = findViewById(R.id.indicator);// ����ˮƽ���֣�����̬��������ͼ��
		
		for (int i = 0; i < 7; i++) {
			imgView = new ImageView(this);
			LinearLayout.LayoutParams params_linear = new LinearLayout.LayoutParams(10,10);
			params_linear.setMargins(7, 10, 7, 10);
			imgView.setLayoutParams(params_linear);
			indicator_imgs[i] = imgView;
			
			if (i == 0) { // ��ʼ����һ��Ϊѡ��״̬
				
				indicator_imgs[i].setBackgroundResource(R.drawable.indicator_focused);
			} else {
				indicator_imgs[i].setBackgroundResource(R.drawable.indicator);
			}
			((ViewGroup)v).addView(indicator_imgs[i]);
		}
		
	}
	
	
	
	
	/**
	 * ������������װ�� ������  ����  ��  ��� ��
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
		 * ������������� �����ٵ�ǰҳ��ǰһ����ǰһ����ҳ��
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
	 * ���������������첽����ͼƬ
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
			
			// �ı����е����ı���ͼƬΪ��δѡ��
			for (int i = 0; i < indicator_imgs.length; i++) {
				
				indicator_imgs[i].setBackgroundResource(R.drawable.indicator);
				
			}
			
			// �ı䵱ǰ����ͼƬΪ��ѡ��
			indicator_imgs[position].setBackgroundResource(R.drawable.indicator_focused);
		}
		
		
	}
	
	

	/**
	 * �첽����ͼƬ
	 */
	static class AsyncImageLoader {

		// �����ã�ʹ���ڴ�����ʱ���� �������˳������ڴ治������������ã�
		private HashMap<String, SoftReference<Drawable>> imageCache;

		public AsyncImageLoader() {
			imageCache = new HashMap<String, SoftReference<Drawable>>();
		}

		/**
		 * ����ص��ӿ�
		 */
		public interface ImageCallback {
			public void imageLoaded(Drawable imageDrawable, String imageUrl);
		}

		
		/**
		 * �������̼߳���ͼƬ
		 * ���̼߳�����ͼƬ����handler�������̲߳��ܸ���ui����handler�������̣߳����Ը���ui��
		 * handler�ֽ���imageCallback��imageCallback��Ҫ�Լ���ʵ�֣���������ԶԻص��������д���
		 *
		 * @param imageUrl ����Ҫ���ص�ͼƬurl
		 * @param imageCallback��
		 * @return
		 */
		public Drawable loadDrawable(final String imageUrl,
				final ImageCallback imageCallback) {
			
			//��������д���ͼƬ  ��������ʹ�û���
			if (imageCache.containsKey(imageUrl)) {
				SoftReference<Drawable> softReference = imageCache.get(imageUrl);
				Drawable drawable = softReference.get();
				if (drawable != null) {
					imageCallback.imageLoaded(drawable, imageUrl);//ִ�лص�
					return drawable;
				}
			}

			/**
			 * �����߳���ִ�лص���������ͼ
			 */
			final Handler handler = new Handler() {
				public void handleMessage(Message message) {
					imageCallback.imageLoaded((Drawable) message.obj, imageUrl);
				}
			};

			
			/**
			 * �������̷߳������粢����ͼƬ ���ѽ������handler����
			 */
			new Thread() {
				@Override
				public void run() {
					Drawable drawable = loadImageFromUrl(imageUrl);
					// �������ͼƬ�ŵ�������
					imageCache.put(imageUrl, new SoftReference<Drawable>(drawable));
					Message message = handler.obtainMessage(0, drawable);
					handler.sendMessage(message);
				}
			}.start();
			
			return null;
		}

		
		/**
		 * ����ͼƬ  ��ע��HttpClient ��httpUrlConnection������
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

		//�������
		public void clearCache() {

			if (this.imageCache.size() > 0) {

				this.imageCache.clear();
			}

		}

	}
	
	
	
	
	

}
