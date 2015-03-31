package com.example.ices.baidu;



import java.io.IOException;
import java.lang.ref.SoftReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

import com.examlpe.ices.util.ImageCacheUtil;
import com.examlpe.ices.util.TitleMenuUtil;
import com.examlpe.ices.util.URLImageParser;
import com.example.ices.BaseActivity;
import com.example.ices.Config;
import com.example.ices.MyApplication;
import com.example.ices.R;
import com.example.ices.activity.EventDetail;
import com.example.ices.activity.LoginActivity;
import com.example.ices.entity.DCImageList;
import com.example.ices.entity.DCdetail;
import com.example.ices.entity.EventDetailEneity;
import com.example.ices.entity.MessageDetailEntity;
import com.example.ices.entity.User;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.zf.myandroidtest_85_photoview.VPImage;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
 



 
public class CustomActivity extends BaseActivity {

	private ViewPager view_pager;
	private TextView more_tv_detail,my_tv_name,tv_price,tv_yunfei,kucun,adress,on_data;
	private LayoutInflater inflater;
	private String directory_contentId;
	private String html;
	 
	private int eventId;
	private int  index_ima=0;
	private RelativeLayout rl_imgs;
	URLImageParser p;
	private LinearLayout ll_Go;
	// ͼƬ�ĵ�ַ��������Դӷ�������ȡ
	private WebView wbview_show;
	private ArrayList<String> ma = new ArrayList<String>();
	private ArrayList<String> mal = new ArrayList<String>();
	private ImageView image;
	private TextView tv_title,tv_time;
	private View item ;
	private MyAdapter adapter ;
	private int id;
	private ImageView[] indicator_imgs  ;//�������ͼƬ����
	List<View> list = new ArrayList<View>();
	List<DCImageList> listimg=new ArrayList<DCImageList>();
	private Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 0:
				/**
				 * �������item ��ÿһ��viewPager����һ��item��
				 * �ӷ�������ȡ�����ݣ������±��⡢url��ַ�� ��������������
				 */
 
				for (int i = 0; i <ma.size(); i++) {			 
					item = inflater.inflate(R.layout.item, null);
					list.add(item);
				}
			 
				
				indicator_imgs	= new ImageView[ma.size()];
			 
				initIndicator();
			 
				adapter.notifyDataSetChanged();
			 
				break;
			case 1:
				Toast.makeText(getApplicationContext(), (String) msg.obj,
						Toast.LENGTH_SHORT).show();
				break;
			case 2: // ����������
				Toast.makeText(getApplicationContext(), "����δ����",
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
		super.onCreate(savedInstanceState);
		setContentView(R.layout.message_detail);
		new TitleMenuUtil(CustomActivity.this, "").show();
		
		String a=getIntent().getStringExtra("msg");
		System.out.println("msg-----"+a);
		JSONObject jsonobject;
		try {
			jsonobject = new JSONObject(a);
		//	String b = (String) jsonobject.get("id");
			id=	jsonobject.getInt("id");
		//	System.out.println("id------"+b);
		//	id=Integer.parseInt(b);
			System.out.println("id------"+id);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 
		
		
		
		
		
		directory_contentId=getIntent().getStringExtra("directory_contentId");
		 
		tv_title= (TextView) findViewById(R.id.tv_title);
		tv_time= (TextView) findViewById(R.id.tv_time);
		more_tv_detail = (TextView) findViewById(R.id.tv_more);
		ll_Go=(LinearLayout) findViewById(R.id.ll_Go);
 
		view_pager = (ViewPager) findViewById(R.id.view_pager);
		 
		inflater = LayoutInflater.from(this);
		adapter = new MyAdapter(list);
		 
		view_pager.setAdapter(adapter);
		//�󶨶������������緭ҳ�Ķ���
		view_pager.setOnPageChangeListener(new MyListener());
//		view_pager.setOnClickListener(new OnClickListener() {
//			
//			@Override
//			public void onClick(View v) {
//				// TODO Auto-generated method stub
//			 Toast.makeText(getApplicationContext(), adapter.getIndex()+"", 1000).show();
//			 return true;
//			}
//		});
		 
		 
 
		//���Դ���
	//	handler.sendEmptyMessage(0);
		 
 
	}
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		
		String a=getIntent().getStringExtra("msg");
		System.out.println("msg-----"+a);
		JSONObject jsonobject;
		try {
			jsonobject = new JSONObject(a);
		//	String b = (String) jsonobject.get("id");
		//	id=	jsonobject.getInt("id");
			id=MyApplication.getNotifyId();
			System.out.println("id------"+id);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		ma.clear();
		mal.clear();
		list.clear();
		adapter.notifyDataSetChanged();
		getData();
		
		System.out.println("onResume---id---"+id);
	}
	
	
	/**
	 * ��ʼ������ͼ��
	 * ��̬�������СԲ�㣬Ȼ����װ�����Բ�����
	 */
	private void initIndicator(){
		
		ImageView imgView;
		View v = findViewById(R.id.indicator);// ����ˮƽ���֣�����̬��������ͼ��
		((ViewGroup)v).removeAllViews();
		for (int i = 0; i < ma.size(); i++) {
			imgView = new ImageView(this);
			LinearLayout.LayoutParams params_linear = new LinearLayout.LayoutParams(10,10);
			params_linear.setMargins(7, 0, 7, 20);
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
			
 
			View view = mList.get(position);
			image = ((ImageView) view.findViewById(R.id.image));
 		ImageCacheUtil.IMAGE_CACHE.get(  ma.get(position),
 				image);
//			ImageCacheUtil.IMAGE_CACHE
//			.get(Config.IMAGE_PATH+ma.get(position)+"_300x300",
//					image);
			container.removeView(mList.get(position));
			container.addView(mList.get(position));
			setIndex(position);
			image.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					// Toast.makeText(getApplicationContext(), index_ima+"----", 1000).show();
					 Intent i=new Intent(CustomActivity.this,VPImage.class);
					 i.putExtra("mal", mal);
					 startActivityForResult(i, 9);
				}
			});
		  
			
			
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
			index_ima=position;
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
	
	private void getData() {

		RequestParams params = new RequestParams();
		params.put("id", id);
		params.put("studentId", MyApplication.currentUser.getStudentId()); 
		params.put("token", MyApplication.getToken());
		params.setUseJsonStreamer(true);
		MyApplication.getInstance().getClient().post(Config.getNotification, params, new AsyncHttpResponseHandler() {

			@Override
			public void onSuccess(int statusCode, Header[] headers,
					byte[] responseBody) {
				// TODO Auto-generated method stub
				String userMsg = new String(responseBody).toString();
				System.out.println("userMsg`` `" + userMsg);
			 
				Gson gson = new Gson();
				JSONObject jsonobject = null;
				int code = 0;
				try {
					jsonobject = new JSONObject(userMsg);
					code = jsonobject.getInt("code");
					if(code==-2){
						Intent i =new Intent(getApplication(),LoginActivity.class);
						startActivity(i);
					}else if(code==0){
						MessageDetailEntity ee = gson.fromJson(jsonobject.getString("result"),
 		 					new TypeToken<MessageDetailEntity>() {
 							}.getType());
						more_tv_detail.setText(ee.getNotificationContent());
						tv_title.setText(ee.getNotificationTitle());
					 	tv_time.setText(ee.getCreateTime());
						for(int i=0;i<ee.getPictures().size();i++){
							ma.add(Config.IMAGE_PATH+ee.getPictures().get(i).getPictureLargeFilePath());
							mal.add(ee.getPictures().get(i).getPictureLargeFilePath());
						}
						if(ee.getNotificationType()==2){
							eventId=ee.getEventsId();
							ll_Go.setOnClickListener(new OnClickListener() {
								
								@Override
								public void onClick(View v) {
									// TODO Auto-generated method stub
									Intent i =new Intent (CustomActivity.this,EventDetail.class);
									i.putExtra("id",eventId);
									System.out.println("=----eventId----"+eventId);
									startActivity(i);
								}
							});
						}else{
							ll_Go.setVisibility(View.GONE);
						}
						handler.sendEmptyMessage(0);
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

			}
		});

	
	}
	
	
	

}
