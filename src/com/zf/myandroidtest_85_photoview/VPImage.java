package com.zf.myandroidtest_85_photoview;

import java.util.ArrayList;

import com.example.ices.Config;
import com.example.ices.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.ImageLoadingListener;
import com.nostra13.universalimageloader.core.assist.SimpleImageLoadingListener;
import com.zf.myandroidtest_85_photoview.photoview.PhotoView;
import com.zf.myandroidtest_85_photoview.photoview.PhotoViewAttacher.OnPhotoTapListener;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

/**
 * 
 * @author 
 * @Date  
 * @ClassInfo  
 * @Description
 */
public class VPImage extends Activity implements OnClickListener {
	private RelativeLayout rLayout_top;
	private RelativeLayout rLayout_bottom;
	private ViewPager vp;
	private LinearLayout lLayout_back;
	private ImageLoader imageLoader;
	private DisplayImageOptions imageOptions;
	private Button btn_download;
	private String curPicUrl;
	
	private ArrayList<String> ma = new ArrayList<String>();
	private ImageView[] indicator_imgs  ;//存放引到图片数组
	
	private String[] urls1 = {
			"http://imgt1.bdstatic.com/it/u=526121001,703673092&fm=116&gp=0.jpg",
			"http://imgt2.bdstatic.com/it/u=3641509810,2457055874&fm=116&gp=0.jpg",
			"http://imgt2.bdstatic.com/it/u=2201589579,464456501&fm=116&gp=0.jpg",
			"http://t12.baidu.com/it/u=581625827,2096256051&fm=120",
			"http://t12.baidu.com/it/u=345569558,1948159766&fm=120",
			"http://t11.baidu.com/it/u=54193081,2947366742&fm=120",
			"http://t12.baidu.com/it/u=1162108991,2789837635&fm=120",
			"http://imgt2.bdstatic.com/it/u=816391611,3552374001&fm=116&gp=0.jpg",
			"http://imgt4.bdstatic.com/it/u=1518282930,1390710250&fm=116&gp=0.jpg",
			"http://t12.baidu.com/it/u=417650363,2605927490&fm=56",
			"http://t10.baidu.com/it/u=1349210034,784611462&fm=56",
			"http://imgt1.bdstatic.com/it/u=1017001232,2349586055&fm=116&gp=0.jpg" };

	private boolean isHide = false;
	private int index;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_vp_img);
		initView();
		index = getIntent().getIntExtra("index", 0);
		ma=getIntent().getStringArrayListExtra("mal");
		indicator_imgs	= new ImageView[ma.size()];
		imageLoader = ImageLoader.getInstance();
		imageOptions = new DisplayImageOptions.Builder().cacheInMemory(true)
				.cacheOnDisc(true).build();
		
		vp.setPageMargin(30);
		vp.setAdapter(new MyVpAdapter());
		initIndicator();
		vp.setOnPageChangeListener(new OnPageChangeListener() {
			@Override
			public void onPageSelected(int position) {
				System.out.println("arg0"+position);
				curPicUrl = ma.get(position);// 标记当前图片地址
			for (int i = 0; i < indicator_imgs.length; i++) {
					
					indicator_imgs[i].setBackgroundResource(R.drawable.indicator);
					 
				}
				
				// 改变当前背景图片为：选中
				index=position;
			 indicator_imgs[position].setBackgroundResource(R.drawable.indicator_focused);
				
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
			}

			@Override
			public void onPageScrollStateChanged(int arg0) {
				System.out.println("arg0"+arg0);
	 
				
			}
		});
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
	private void initView() {
	 
		vp = (ViewPager) findViewById(R.id.vp);
 
 
 
	}

	@Override
	public void onClick(View v) {
		 
	}

	private class MyVpAdapter extends PagerAdapter {
		@Override
		public int getCount() {
			return ma.size();
		}

		@Override
		public boolean isViewFromObject(View view, Object object) {
			return view == object;
		}

		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			String picUrl = Config.IMAGE_PATH+ma.get(position);
			View view = getLayoutInflater()
					.inflate(R.layout.view_vp_item, null);
			final PhotoView photoView = (PhotoView) view
					.findViewById(R.id.img_photo_view);
			final ProgressBar pb = (ProgressBar) view.findViewById(R.id.pb);

			/*
			 * 点击事件:隐藏头部和底部布局，在PhotoViewAttacher中的onDoubleTap()已经注释掉双击缩放的逻辑
			 */
			photoView.setOnPhotoTapListener(new OnPhotoTapListener() {
				@Override
				public void onPhotoTap(View view, float x, float y) {
					Animation top_in = AnimationUtils.loadAnimation(
							VPImage.this, R.anim.top_in);
					top_in.setAnimationListener(new AnimationListener() {
						@Override
						public void onAnimationStart(Animation animation) {
						}

						@Override
						public void onAnimationRepeat(Animation animation) {
						}

						@Override
						public void onAnimationEnd(Animation animation) {
							lLayout_back.setEnabled(true);
							btn_download.setEnabled(true);
						}
					});
					Animation top_out = AnimationUtils.loadAnimation(
							VPImage.this, R.anim.top_out);
					top_out.setAnimationListener(new AnimationListener() {
						@Override
						public void onAnimationStart(Animation animation) {
							lLayout_back.setEnabled(false);
							btn_download.setEnabled(false);
						}

						@Override
						public void onAnimationRepeat(Animation animation) {
						}

						@Override
						public void onAnimationEnd(Animation animation) {
						}
					});
					Animation bottom_in = AnimationUtils.loadAnimation(
							VPImage.this, R.anim.bottom_in);
					Animation bottom_out = AnimationUtils.loadAnimation(
							VPImage.this, R.anim.bottom_out);
					isHide = !isHide;
					if (isHide) {
					//	rLayout_top.startAnimation(top_out);
					//	rLayout_bottom.startAnimation(bottom_out);
					} else {
					//	rLayout_top.startAnimation(top_in);
					//	rLayout_bottom.startAnimation(bottom_in);
					}
				}
			});

			// 加载图片
			imageLoader.loadImage(picUrl, imageOptions,
					new ImageLoadingListener() {
						@Override
						public void onLoadingStarted(String imageUri, View view) {
							pb.setVisibility(View.VISIBLE);
						}

						@Override
						public void onLoadingFailed(String imageUri, View view,
								FailReason failReason) {
							Toast.makeText(VPImage.this, "图片加载异常",
									Toast.LENGTH_SHORT).show();
						}

						@Override
						public void onLoadingComplete(String imageUri,
								View view, Bitmap loadedImage) {
							pb.setVisibility(View.GONE);
							photoView.setImageBitmap(loadedImage);
						}

						@Override
						public void onLoadingCancelled(String imageUri,
								View view) {
						}
					});
			container.addView(view);
			return view;
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			container.removeView((View) object);
		}
	}
}
