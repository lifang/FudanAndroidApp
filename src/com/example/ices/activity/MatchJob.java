//package com.example.ices.activity;
//
//import java.util.ArrayList;
//import java.util.List;
//
// 
// 
//import android.net.ConnectivityManager;
//import android.net.NetworkInfo;
//import android.os.Bundle;
//import android.app.Activity;
//import android.content.Context;
//import android.graphics.Bitmap;
//import android.graphics.BitmapFactory;
//import android.graphics.drawable.BitmapDrawable;
//import android.graphics.drawable.Drawable;
//import android.support.v4.view.PagerAdapter;
//import android.support.v4.view.ViewPager;
//import android.support.v4.view.ViewPager.OnPageChangeListener;
//import android.view.LayoutInflater;
//import android.view.Menu;
//import android.view.View;
//import android.view.View.OnClickListener;
//import android.widget.ImageView;
//import android.widget.LinearLayout;
//import android.widget.ProgressBar;
//import android.widget.TextView;
//
//public class MatchJob extends Activity {
//	private ViewPager mViewPager;
//	private ViewPagerAdapter adapter;
//	LayoutInflater inflater;
//	AsyncImageLoader asyncImageLoader;
// 	private ArrayList<ImageView> images;
//	List<ImageAndUrl> imageInfosShow = new ArrayList<ImageAndUrl>();
//	private List<String> titles;
//	Bitmap defaultbmp;
//	private int oldPosition = 0;// 记录上一次点的位�?
//	private int currentItem; // 当前页面
//	private TextView match_title, match_info;
//	private LinearLayout titleback_linear_back1, download_img;
//	private int index;
//	private Bitmap image_bit;
//	private String down_url;
//	private ArrayList<String> ma = new ArrayList<String>();
//
//	@Override
//	protected void onCreate(Bundle savedInstanceState) {
//		super.onCreate(savedInstanceState);
//		setContentView(R.layout.match_job);
//		ma = getIntent().getStringArrayListExtra("mlist");
//		index = getIntent().getIntExtra("index", 0);
//
//		inflater = getLayoutInflater();
//		titleback_linear_back1 = (LinearLayout) findViewById(R.id.titleback_linear_back1);
//		titleback_linear_back1.setOnClickListener(new OnClickListener() {
//
//			@Override
//			public void onClick(View v) {
//				// TODO Auto-generated method stub
//				finish();
//			}
//
//		});
//
//		download_img = (LinearLayout) findViewById(R.id.download_img);
//
//		download_img.setOnClickListener(new OnClickListener() {
//
//			@Override
//			public void onClick(View v) {
//				// TODO Auto-generated method stub
//
//				Bitmap a = FileCache.getInstance().getBmp(down_url);
//
//				if (a == null) {
//					new ToastShow(MatchJob.this, "下载失败，请重试").show();
//				} else {
//					new ToastShow(MatchJob.this, "已经成功下载保存�?Lapel/images/")
//							.show();
//				}
//				FileCache.getInstance().DownBmpData(down_url, a);
//
//			}
//		});
//
//		mViewPager = (ViewPager) findViewById(R.id.match_vp);
//		// 默认图片 defaultbmp = BitmapFactory.decodeResource(getResources(),
//		// R.drawable.defaultbitmap);
//		match_title = (TextView) findViewById(R.id.match_title);
//		match_info = (TextView) findViewById(R.id.match_info);
//
//		asyncImageLoader = new AsyncImageLoader();
//		getdata();
//		down_url = imageInfosShow.get(0).getImageurl();
//		match_title.setText("浏览--" + "1/" + ma.size());
//		adapter = new ViewPagerAdapter();
//		mViewPager.setAdapter(adapter);
//
//		mViewPager.setOnPageChangeListener(new OnPageChangeListener() {
//			@Override
//			public void onPageSelected(int position) {
//				// title.setText(titles[position]);
//				System.out.println("position   " + position);
//				// dots.get(oldPosition).setBackgroundResource(R.drawable.dxt_point_nomral);
//				// dots.get(position).setBackgroundResource(R.drawable.dxt_point_selected);
//				down_url = imageInfosShow.get(position).getImageurl();
//				System.out.println("down_url" + down_url);
//				oldPosition = position;
//				currentItem = position;
//				match_title.setText(titles.get(position));
//			}
//
//			@Override
//			public void onPageScrolled(int arg0, float arg1, int arg2) {
//
//			}
//
//			@Override
//			public void onPageScrollStateChanged(int arg0) {
//
//			}
//		});
//		mViewPager.setCurrentItem(index);
//	}
//
//	private void getdata() {
//		// TODO Auto-generated method stub
//
//		for (int i = 0; i < ma.size(); i++) {
//			ImageAndUrl item = new ImageAndUrl();
//			item.setImageurl(ma.get(i));
//			item.setUrl("item-->" + i);
//			imageInfosShow.add(item);
//		}
//
//		images = new ArrayList<ImageView>();
//		for (int i = 0; i < imageInfosShow.size(); i++) {
//			ImageView item = new ImageView(this);
//			images.add(item);
//		}
//		titles = new ArrayList<String>(imageInfosShow.size());
//		for (int i = 0; i < imageInfosShow.size(); i++) {
//			titles.add("浏览--" + (i + 1) + "/" + imageInfosShow.size());
//		}
//	}
//
//	private class ViewPagerAdapter extends PagerAdapter {
//
//		@Override
//		public void destroyItem(View container, int position, Object object) {
//			((ViewPager) container).removeView((View) object);
//		}
//
//		@Override
//		public int getCount() {
//			return imageInfosShow.size();
//		}
//
//		@Override
//		public boolean isViewFromObject(View view, Object object) {
//			return view.equals(object);
//		}
//
//		@Override
//		public Object instantiateItem(final View view, int position) {
//			ImageAndUrl item = imageInfosShow.get(position);
//			final View imageLayout = inflater.inflate(
//					R.layout.item_pager_image, null);
//			final ImageView imageView = (ImageView) imageLayout
//					.findViewById(R.id.image_match);
//			final ProgressBar progressBar = (ProgressBar) imageLayout
//					.findViewById(R.id.loading_match);
//			final String imgUrl = item.getImageurl();
//			imageView.setTag(imgUrl);
//
//			imageView.setOnClickListener(new OnClickListener() {
//				@Override
//				public void onClick(View v) {
//					System.out.println("save--" + imgUrl);
//
//					// FileCache.getInstance().saveBmpDataByName(imgUrl,
//					// image_bit);
//				}
//			});
//			// final ProgressBar progressBar = (ProgressBar)
//			// imageLayout.findViewById(R.id.loading);
//			// 如果联网
//			if (checkConnection()) {
//				Bitmap bmpFromSD = FileCache.getInstance().getBmp(imgUrl);
//				if (null != bmpFromSD) {
//					imageView.setLayoutParams(new LinearLayout.LayoutParams(
//							LinearLayout.LayoutParams.FILL_PARENT,
//							LinearLayout.LayoutParams.FILL_PARENT)); // bmpFromSD.getHeight()
//					imageView.setImageBitmap(bmpFromSD);
//					progressBar.setVisibility(View.INVISIBLE);
//				} else {
//					Drawable cachedImage = asyncImageLoader.loaDrawable(imgUrl,
//							new ImageCallBack() {
//								@Override
//								public void imageLoaded(Drawable imageDrawable) {
//									Bitmap bitmap = drawToBmp(imageDrawable);
//									// image_bit= drawToBmp(imageDrawable);
//
//									FileCache.getInstance().savaBmpData(imgUrl,
//											bitmap);// 先缓存起�?
//									System.out.println("url``" + imgUrl);
//									// FileCache.getInstance().saveBmpDataByName(imgUrl,
//									// image_bit);
//									ImageView imageViewByTag = null;
//									if (null != bitmap) {
//										imageViewByTag = (ImageView) imageView
//												.findViewWithTag(imgUrl);
//										imageViewByTag
//												.setLayoutParams(new LinearLayout.LayoutParams(
//														LinearLayout.LayoutParams.FILL_PARENT,
//														LinearLayout.LayoutParams.FILL_PARENT));
//									}
//									if (imageViewByTag != null) {
//										if (isWifi(MatchJob.this)) {
//											imageViewByTag
//													.setImageBitmap(bitmap);
//											progressBar
//													.setVisibility(View.INVISIBLE);
//										} else {
//											if (bitmap != null) {
//												imageViewByTag
//														.setLayoutParams(new LinearLayout.LayoutParams(
//																LinearLayout.LayoutParams.FILL_PARENT,
//																LinearLayout.LayoutParams.FILL_PARENT));
//												imageViewByTag
//														.setImageBitmap(bitmap);
//												imageViewByTag
//														.setScaleType(ImageView.ScaleType.MATRIX);
//												progressBar
//														.setVisibility(View.INVISIBLE);
//											}
//										}
//									}
//								}
//							});
//					if (cachedImage == null) {
//						imageView.setImageBitmap(defaultbmp);
//					} else {
//						if (isWifi(MatchJob.this)) {
//							Bitmap bitmap = drawToBmp(cachedImage);
//							imageView
//									.setLayoutParams(new LinearLayout.LayoutParams(
//											LinearLayout.LayoutParams.FILL_PARENT,
//											LinearLayout.LayoutParams.FILL_PARENT));
//							imageView.setImageBitmap(bitmap);
//						} else {
//							imageView
//									.setLayoutParams(new LinearLayout.LayoutParams(
//											LinearLayout.LayoutParams.FILL_PARENT,
//											LinearLayout.LayoutParams.FILL_PARENT));
//							Bitmap bitmap = drawToBmp(cachedImage);
//							imageView.setImageBitmap(bitmap);
//						}
//						progressBar.setVisibility(View.INVISIBLE);
//					}
//				}
//			} else {
//				Bitmap bmpFromSD = FileCache.getInstance().getBmp(imgUrl);
//				if (null != bmpFromSD) {
//					ImageView imageViewByTag = (ImageView) imageView
//							.findViewWithTag(imgUrl);
//					imageViewByTag
//							.setLayoutParams(new LinearLayout.LayoutParams(
//									LinearLayout.LayoutParams.WRAP_CONTENT,
//									bmpFromSD.getHeight())); //
//					imageViewByTag.setImageBitmap(bmpFromSD);
//					progressBar.setVisibility(View.INVISIBLE);
//				} else {
//					imageView.setImageBitmap(defaultbmp);
//					progressBar.setVisibility(View.GONE);
//				}
//			}
//			((ViewPager) view).addView(imageLayout, 0);
//			// return images.get(position % images.size());
//			return imageLayout;
//		}
//	}
//
//	public boolean checkConnection() {
//		ConnectivityManager connectivityManager = (ConnectivityManager) this
//				.getSystemService(CONNECTIVITY_SERVICE);
//		NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
//		if (networkInfo != null) {
//			return networkInfo.isAvailable();
//		}
//		return false;
//	}
//
//	public Bitmap drawToBmp(Drawable d) {
//		if (null != d) {
//			BitmapDrawable bd = (BitmapDrawable) d;
//			return bd.getBitmap();
//		}
//		return null;
//	}
//
//	public boolean isWifi(Context mContext) {
//		ConnectivityManager connectivityManager = (ConnectivityManager) mContext
//				.getSystemService(Context.CONNECTIVITY_SERVICE);
//		NetworkInfo activeNetInfo = connectivityManager.getActiveNetworkInfo();
//		if (activeNetInfo != null && activeNetInfo.getTypeName().equals("WIFI")) {
//			return true;
//		}
//		return false;
//	}
//}
