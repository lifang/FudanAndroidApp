package com.example.ices.activity;

 
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

import com.examlpe.ices.util.ImageCacheUtil;
import com.examlpe.ices.util.TitleMenuUtil;
import com.example.ices.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
 
 

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
 
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
 

/***
 *  �ϴ���ͼ 
 * 
 * @author Lijinpeng
 * 
 *         comdo
 */

@SuppressLint("NewApi")
public class BigImage extends Activity {

	private ViewPager view_pager;
	private TextView more_tv_detail,my_tv_name,tv_price,tv_yunfei,kucun,adress,on_data;
	private LayoutInflater inflater;
	private String sign,goodsInfoId;
	private String html;
	private RelativeLayout rl_imgs;
 
	// ͼƬ�ĵ�ַ��������Դӷ�������ȡ
	private WebView wbview_show;
	private ArrayList<String> ma = new ArrayList<String>();
	private ImageView image;
	private View item ;
	private MyAdapter adapter ;
	private ImageView[] indicator_imgs  ;//�������ͼƬ����
	List<View> list = new ArrayList<View>();
	private Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 0:
				/**
				 * �������item ��ÿһ��viewPager����һ��item��
				 * �ӷ�������ȡ�����ݣ������±��⡢url��ַ�� ��������������
				 */
	 
				System.out.println(" 1----"+ma.size());
				for (int i = 0; i <ma.size(); i++) {
					System.out.println(" 2--1--"+ma.size());
					item = inflater.inflate(R.layout.item, null);
					System.out.println(" 2--2--"+ma.size());
				//	 ((TextView) item.findViewById(R.id.text_view)).setText("�� " + i+ " �� viewPager");
					list.add(item);
					System.out.println(" 2--3--"+ma.size());
				}
				System.out.println(" 2----"+ma.size());
				
				indicator_imgs	= new ImageView[ma.size()];
				System.out.println(" 3----"+ma.size());
				initIndicator();
				System.out.println(" 4----"+ma.size());
				adapter.notifyDataSetChanged();
				System.out.println(" 5----"+ma.size());
				break;
			case 1:
				Toast.makeText(getApplicationContext(), (String) msg.obj,
						Toast.LENGTH_SHORT).show();
				break;
			case 2: // ����������
				Toast.makeText(getApplicationContext(), "1111",
						Toast.LENGTH_SHORT).show();
				break;
			case 3:
				Toast.makeText(getApplicationContext(),
						"11222211", Toast.LENGTH_SHORT).show();
				break;
			case 4:
				Toast.makeText(getApplicationContext(),"12223333111",
						Toast.LENGTH_SHORT).show();
				break;
			}
		}
	};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.big_image);
		new TitleMenuUtil(BigImage.this, "�ϴ�ƾ֤").show();
		 
		goodsInfoId=getIntent().getStringExtra("goodsInfoId");
		//System.out.println("goodsInfoId"+goodsInfoId+"(---)"+MyApplication.currentUser.getSessionId());
 
		view_pager = (ViewPager) findViewById(R.id.view_pager);
		 
		inflater = LayoutInflater.from(this);
		adapter = new MyAdapter(list);
		view_pager.setAdapter(adapter);
		//�󶨶������������緭ҳ�Ķ���
		view_pager.setOnPageChangeListener(new MyListener());
		 
		ma=getIntent().getStringArrayListExtra("mal");
		
		 if(ma.size()>0){
		 	handler.sendEmptyMessage(0);
			// Toast.makeText(getApplicationContext(), ma.size()+"---URL", 1000).show();
		 }else{
			 
			Toast.makeText(getApplicationContext(), "size==0", 1000).show();
		 }

		
		
		
		//������������ ����װ���������ݽ�ȥ
 
	}

	
	
	/**
	 * ��ʼ������ͼ��
	 * ��̬�������СԲ�㣬Ȼ����װ�����Բ�����
	 */
	private void initIndicator(){
		
		ImageView imgView;
		View v = findViewById(R.id.indicator);// ����ˮƽ���֣�����̬��������ͼ��
		
		for (int i = 0; i < ma.size(); i++) {
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
			

//			Drawable cachedImage = asyncImageLoader.loadDrawable(
//					ma.get(position) , new ImageCallback() {
//
//						public void imageLoaded(Drawable imageDrawable,
//								String imageUrl) {
//
//							View view = mList.get(position);
//							image = ((ImageView) view.findViewById(R.id.image));
//							image.setBackground(imageDrawable);
//							container.removeView(mList.get(position));
//							container.addView(mList.get(position));
//							// adapter.notifyDataSetChanged();
//
//						}
//					});

			View view = mList.get(position);
			image = ((ImageView) view.findViewById(R.id.image));
		//	image.setBackground(cachedImage);
//			ImageCacheUtil.IMAGE_CACHE.get(  ma.get(position),
//					image);
			ImageCacheUtil.IMAGE_CACHE
			.get(ma.get(position),
					image);
			container.removeView(mList.get(position));
			container.addView(mList.get(position));
			// adapter.notifyDataSetChanged();
				

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
