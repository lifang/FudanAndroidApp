package com.example.ices.activity;

import java.io.IOException;
import java.lang.ref.SoftReference;
import java.security.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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
import com.examlpe.ices.util.StringUtil;
import com.examlpe.ices.util.TitleMenuUtil;

import com.example.ices.BaseActivity;
import com.example.ices.Config;
import com.example.ices.MyApplication;
import com.example.ices.R;
import com.example.ices.activity.PreDetail.AsyncImageLoader;


import com.example.ices.activity.PreDetail.AsyncImageLoader.ImageCallback;
import com.example.ices.baidu.BigImageShow;
import com.example.ices.entity.EventDetailEneity;
import com.example.ices.entity.EventEntity;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.zf.myandroidtest_85_photoview.VPImage;

public class EventDetail extends BaseActivity{
	private int id;
	private Date old;
	private TextView eventsFinshTime,tv_detail,name,creat_tv,location,tv_tel,tv_cost;
	private LinearLayout ll_join;
	private ImageView image,testimg;
	private RelativeLayout rl_tel;
	private ArrayList<String> ma = new ArrayList<String>();
	private ArrayList<String> mal = new ArrayList<String>();
	private ViewPager view_pager;
	private MyAdapter adapter ;
	private ImageView[] indicator_imgs  ;//�������ͼƬ����
	private View item ;
	private LayoutInflater inflater;
	private RelativeLayout rl_imgs;
	private int  index_ima=0;

	private int isjion;
	private String finishTime;
	private TextView tv_isjoin;
	String url;
    private Dialog loadingDialog;
	List<View> list = new ArrayList<View>();
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
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.event_detail);
		new TitleMenuUtil(EventDetail.this, "").show();
		id=getIntent().getIntExtra("id", 0);
		innitView();
		url=	Config.getEvent;
		getdata(url);
		//DialogUtil
		//		testimg=(ImageView) findViewById(R.id.testimg);
		// 		ImageCacheUtil.IMAGE_CACHE.get( "http://p2.gexing.com/qqpifu/20121208/0612/50c269e15585a.jpg",
		// 				testimg);
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		view_pager.setCurrentItem(1);
		view_pager.setCurrentItem(0);
	}
	private void innitView() {
		// TODO Auto-generated method stub

		// p = new URLImageParser(more_tv_detail, EventDetail.this);
		rl_imgs=(RelativeLayout) findViewById(R.id.rl_imgs);

		view_pager = (ViewPager) findViewById(R.id.view_pager);

		inflater = LayoutInflater.from(this);
		adapter = new MyAdapter(list);
		tv_isjoin=(TextView) findViewById(R.id.tv_isjoin);
		view_pager.setAdapter(adapter);
		//�󶨶������������緭ҳ�Ķ���
		view_pager.setOnPageChangeListener(new MyListener());
		tv_cost=(TextView) findViewById(R.id.tv_cost);
		tv_tel=(TextView) findViewById(R.id.tv_tel);
		location=(TextView) findViewById(R.id.location);
		creat_tv=(TextView) findViewById(R.id.creat_time);
		name=(TextView) findViewById(R.id.name);
		eventsFinshTime=(TextView) findViewById(R.id.eventsFinshTime);
		tv_detail=(TextView) findViewById(R.id.tv_detail);
		ll_join=(LinearLayout) findViewById(R.id.ll_join);
		ll_join.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (null != finishTime && !"".equals(finishTime)) {
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

					try {
						old =sdf.parse(finishTime);

					} catch (ParseException e) {
						e.printStackTrace();
					}
					Date nowDate = Calendar.getInstance().getTime();

					System.out.println("nowDate```"+nowDate.getTime());
					if(old.getTime()<nowDate.getTime()){
						Toast.makeText(getApplicationContext(), "Event has finished!", 1000).show();
					}else{
						Dialog ddd=	new DialogUtil(EventDetail.this,"Join the event?").getCheck(new CallBackChange() {

							@Override
							public void change() {
								// TODO Auto-generated method stub
								//	Toast.makeText(getApplication(), "DOOOO", 1000).show();
								url=Config.joinEvent;

								join(url);
							}
						});
						ddd.show();
					}
				}
			}

		});
		rl_tel=(RelativeLayout) findViewById(R.id.rl_tel);
		rl_tel.setOnClickListener(new OnClickListener(
				) {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//��绰

				Intent intent = new Intent(Intent.ACTION_CALL,Uri.parse("tel:"+tv_tel.getText().toString()));  
				startActivity(intent);  
			}
		});
	}
	private void getdata(String url) {
		// TODO Auto-generated method stub
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
				// TODO Auto-generated method stub
				String userMsg = new String(responseBody).toString();
				if (loadingDialog != null) {
					loadingDialog.dismiss();
				}
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

						eventsFinshTime.setText(StringUtil.timeUtil(ee.getEventsStartTime())+".-"+StringUtil.timeUtil(ee.getEventsFinshTime()) );
						finishTime=ee.getEventsFinshTime();
						creat_tv.setText("Creattime:"+ee.getCreateTime());
						name.setText(ee.getEventsName());
						tv_detail.setText(ee.getEventsIntroduction());
						location.setText(ee.getEventsAddress());
						tv_tel.setText(ee.getEventsPhone());
						isjion=ee.getEventsIsJoin();
						tv_cost.setText( String.valueOf(ee.getEventsCostMoney()));

						if(MyApplication.currentUser.getStudentStatus().endsWith("2")){
							if(isjion==2){
								ll_join.setClickable(false);
								tv_isjoin.setText("Joined");
								ll_join.setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_finish_dra));
							}
						}else{
							ll_join.setVisibility(View.GONE);
						}


						for(int i=0;i<ee.getPictures().size();i++){
							ma.add(Config.IMAGE_PATH+ee.getPictures().get(i).getPictureLargeFilePath());
							//	ma.add("http://su.bdimg.com/static/superplus/img/logo_white_ee663702.png");
							mal.add(ee.getPictures().get(i).getPictureLargeFilePath());
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
				if (loadingDialog != null) {
					loadingDialog.dismiss();
				}
				Toast.makeText(getApplicationContext(),getResources().getString(R.string.no_internet),
						Toast.LENGTH_SHORT).show();
			}
		});



	}
	private void join(String url) {
		// TODO Auto-generated method stub
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
						Toast.makeText(getApplicationContext(), jsonobject.getString("message"),
								Toast.LENGTH_SHORT).show();
						ll_join.setClickable(false);
						tv_isjoin.setText("Joined");
						ll_join.setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_finish_dra));
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}


			}

			@Override
			public void onFailure(int statusCode, Header[] headers,
					byte[] responseBody, Throwable error) {
				if (loadingDialog != null) {
					loadingDialog.dismiss();
				}
				Toast.makeText(getApplicationContext(),getResources().getString(R.string.no_internet),
						Toast.LENGTH_SHORT).show();
			}
		});



	}
	private void initIndicator(){

		ImageView imgView;
		View v = findViewById(R.id.indicator);// ����ˮƽ���֣�����̬��������ͼ��

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
			container.removeView(mList.get(position));
			container.addView(mList.get(position));
			image.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					//	 Toast.makeText(getApplicationContext(), index_ima+"----", 1000).show();
					Intent i=new Intent(EventDetail.this,VPImage.class);
					// i.putExtra("image_url", ma.get(index_ima));
					i.putExtra("mal", mal);
					i.putExtra("index", index_ima);
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
}
