package com.example.ices.baidu;

import java.util.ArrayList;

import com.example.ices.R;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
 
 

public class BigImageShow extends Activity
{
	private ViewPager mViewPager;
//	private int[] mImgs = new int[] { R.drawable.tbug, R.drawable.a,
//			R.drawable.xx };
	private int index;
	private ArrayList<String> ma = new ArrayList<String>();
	private ImageView[] mImageViews;
 
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.vp);
		index = getIntent().getIntExtra("index", 0);
		ma=getIntent().getStringArrayListExtra("mal");
		
		mImageViews = new ImageView[ma.size()];
		
		mViewPager = (ViewPager) findViewById(R.id.id_viewpager);
		mViewPager.setAdapter(new PagerAdapter()
		{

			@Override
			public Object instantiateItem(ViewGroup container, int position)
			{
				ZoomImageView imageView = new ZoomImageView(
						getApplicationContext());
				//imageView.setImageResource(mImgs[position]);
				ImageBigUtil.IMAGE_CACHE
					.get(ma.get(position),
							imageView);
				container.addView(imageView);
				mImageViews[position] = imageView;
				return imageView;
			}

			@Override
			public void destroyItem(ViewGroup container, int position,
					Object object)
			{
				container.removeView(mImageViews[position]);
			}

			@Override
			public boolean isViewFromObject(View arg0, Object arg1)
			{
				return arg0 == arg1;
			}

			@Override
			public int getCount()
			{
				return ma.size();
			}
		});
		mViewPager.setCurrentItem(index);
	}
}
