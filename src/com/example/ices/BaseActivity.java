package com.example.ices;

 
import android.app.Activity;
import android.view.View;
import android.view.View.OnClickListener;
/***
 * ������
 * @author Lijinpeng
 *
 * comdo
 */

public class BaseActivity extends Activity implements OnClickListener{
	@Override
	protected void onDestroy() {
		//������
		//getRequests().cancelAll(this);
		super.onDestroy();
	}
	
	@Override
	protected void onPause() {
		super.onPause();
	//	StatService.onPause(this);
	}

	@Override
	protected void onResume() {
		super.onResume();
	//	StatService.onResume(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
	}
}
