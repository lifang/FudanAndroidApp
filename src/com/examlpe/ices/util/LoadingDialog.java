package com.examlpe.ices.util;


import android.app.Activity;
import android.app.Dialog;
import android.util.DisplayMetrics;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.example.ices.R;

public class LoadingDialog {

	public static Dialog getLoadingDialg(Activity context) {

		final Dialog dialog = new Dialog(context, R.style.LoadingDialog);
		dialog.setCancelable(false);
		dialog.setContentView(R.layout.dialog_dark);
		Window window = dialog.getWindow();
		WindowManager.LayoutParams lp = window.getAttributes();

		int screenW = getScreenWidth(context);
		lp.width = (int) (0.6 * screenW);

		TextView titleText = (TextView) dialog.findViewById(R.id.dialog_text);
		titleText.setText(context.getString(R.string.loading_data));
		return dialog;
	}


	public static int getScreenWidth(Activity context) {
		DisplayMetrics dm = new DisplayMetrics();
		context.getWindowManager().getDefaultDisplay().getMetrics(dm);
		return dm.widthPixels;
	}

	public static int getScreenHeight(Activity context) {
		DisplayMetrics dm = new DisplayMetrics();
		context.getWindowManager().getDefaultDisplay().getMetrics(dm);
		return dm.heightPixels;
	}
}