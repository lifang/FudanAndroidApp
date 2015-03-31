package com.examlpe.ices.util;

import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.LinearLayout.LayoutParams;

/**
 * listview item 
 *  
 *
 */
public class ExpandAnimation extends Animation {
	private View mAnimatedView;
	private LayoutParams mViewLayoutParams;
	private int mMarginStart, mMarginEnd;
	private boolean mIsVisibleAfter = false;
	private boolean mWasEndedAlready = false;

	public ExpandAnimation(View view, int duration) {

		setDuration(duration);
		mAnimatedView = view;
		mViewLayoutParams = (LayoutParams) view.getLayoutParams();

		mIsVisibleAfter = (mViewLayoutParams.bottomMargin == 0);

		mMarginStart = mViewLayoutParams.bottomMargin;
		mMarginEnd = (mMarginStart == 0 ? (0 - view.getHeight()) : 0);

		view.setVisibility(View.VISIBLE);
	}

	@Override
	protected void applyTransformation(float interpolatedTime, Transformation t) {
		super.applyTransformation(interpolatedTime, t);

		if (interpolatedTime < 1.0f) {

			mViewLayoutParams.bottomMargin = mMarginStart
					+ (int) ((mMarginEnd - mMarginStart) * interpolatedTime);

			mAnimatedView.requestLayout();

		} else if (!mWasEndedAlready) {
			mViewLayoutParams.bottomMargin = mMarginEnd;
			mAnimatedView.requestLayout();

			if (mIsVisibleAfter) {
				mAnimatedView.setVisibility(View.GONE);
			}
			mWasEndedAlready = true;
		}
	}
}