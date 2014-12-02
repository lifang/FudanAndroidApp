package com.example.ices;

 
import android.app.Activity;
import android.view.View;
import android.view.View.OnClickListener;
/***
 * »ù´¡Àà
 * @author Lijinpeng
 *
 * comdo
 */

public class BaseActivity extends Activity implements OnClickListener{
	@Override
	protected void onDestroy() {
		//´ý²âÊÔ
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
