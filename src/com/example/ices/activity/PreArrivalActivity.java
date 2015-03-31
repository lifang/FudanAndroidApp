package com.example.ices.activity;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
 
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
 
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
 
import android.widget.AbsListView.LayoutParams;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.examlpe.ices.util.ExpandAnimation;
import com.examlpe.ices.util.TitleMenuUtil;
import com.example.ices.BaseActivity;
import com.example.ices.Config;
import com.example.ices.MyApplication;
import com.example.ices.R;
import com.example.ices.adapter.AroundAdapter;
 
import com.example.ices.entity.AroundEntity;
import com.example.ices.entity.ContentListEntity;
import com.example.ices.entity.DirectoryList;
import com.example.ices.entity.MenuTitle;
import com.example.ices.entity.User;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
 
import com.handmark.pulltorefresh.library.PullToRefreshListView;
 
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

public class PreArrivalActivity extends BaseActivity {
	private ListView lv;
	//private List<DirectoryList> list = new ArrayList<DirectoryList>();
//	private List<ContentListEntity> listChild = new ArrayList<ContentListEntity>();
//	private List<ContentListEntity> listChild2 = new ArrayList<ContentListEntity>();
//	private List<MenuTitle> list= new ArrayList<MenuTitle>();
	private AroundAdapter myAdapter;
	private PullToRefreshListView expandlistview;
	//private Handler handler = new Handler();
	int i = 0;
	private int page=1;
	private RelativeLayout main_rl_Food,main_rl_drink,main_rl_sport,main_rl_sm,main_rl_sb,main_post,
	main_rl_other,  main_rl_Hotel;
	private int type=1;
	 
	List<AroundEntity>  myList = new ArrayList<AroundEntity>();
	List<AroundEntity>  moreList = new ArrayList<AroundEntity>();
	private int rows=Config.ROWS;
	@SuppressLint("NewApi")
	private Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 0:
				System.out.println("size"+myList.size());
			 	myAdapter.notifyDataSetChanged();
			 	expandlistview.onRefreshComplete();
				break;
	 
			}
		}
	};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.pre_arrival_act);
		new TitleMenuUtil(PreArrivalActivity.this, "Around Campus").show();
		expandlistview = (PullToRefreshListView) findViewById(R.id.expandlistview);
		 	
		expandlistview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				//Intent i =new Intent(getApplicationContext(),PreDetail.class);
			//	i.putExtra("directory_contentId", "231");
				//startActivity(i);
				System.out.println( position);
				if(position==1){ 
					
				 
					
				}else{
					Intent i = new Intent(PreArrivalActivity.this,AroundDetail.class);
 
					i.putExtra("id", myList.get(position-2).getId());
					System.out.println("id---"+myList.get(position-2).getId());
					startActivityForResult(i, 10);
				}
			}
		});
		expandlistview.setMode(PullToRefreshBase.Mode.PULL_DOWN_TO_REFRESH);
		expandlistview.setOnRefreshListener(new OnRefreshListener<ListView>() {

			@Override
			public void onRefresh(PullToRefreshBase<ListView> refreshView) {
				// TODO Auto-generated method stub
			//	Toast.makeText(getApplicationContext(), "refresh----" , 1000).show();
				 getData();
			 	//handler.sendEmptyMessage(0);
				 
			}
		});
		LayoutInflater a = LayoutInflater.from(PreArrivalActivity.this);
		 
		myAdapter=new AroundAdapter(PreArrivalActivity.this, myList);
		expandlistview.setAdapter(myAdapter);
		 View view=a.inflate(R.layout.around_head, null);
		 LayoutParams ly= new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);
		 view.setLayoutParams(ly);
		  expandlistview.getRefreshableView().addHeaderView(view);
			initView();
		getData();
	}

	private void getData() {
		// TODO Auto-generated method stub
		myList.clear();
		// TODO Auto-generated method stub
		//AsyncHttpClient client = new rows(); //  
		RequestParams params = new RequestParams();
		params.put("rows",10);
		params.put("page",page); 
		params.put("type", 0); 
		params.put("studentId",MyApplication.currentUser.getStudentId());
		params.put("token", MyApplication.getToken()); 
		
		 
		params.setUseJsonStreamer(true);
 
	 
		MyApplication.getInstance().getClient().post(Config.getArounds, params, new AsyncHttpResponseHandler() {

			@Override
			public void onSuccess(int statusCode, Header[] headers,
					byte[] responseBody) {
				// TODO Auto-generated method stub
				String responseMsg = new String(responseBody).toString();
				System.out.println("MSG" + responseMsg);	
			 
				Gson gson = new Gson();
				JSONObject jsonobject = null;
				int code = 0;
				try {
					jsonobject = new JSONObject(responseMsg);
					 
					 
					code = jsonobject.getInt("code");
					
					if(code==-2){
						Intent i =new Intent(getApplication(),LoginActivity.class);
						startActivity(i);
					}else if(code==0){
						
						
						moreList.clear();
						
		 				moreList = gson.fromJson(jsonobject.getString("result") ,
							new TypeToken<List<AroundEntity>>() {
							}.getType());
		 					 	
		 						if (moreList.size()==0) {
		 							Toast.makeText(getApplicationContext(),
		 									"no more data", Toast.LENGTH_SHORT).show();
		 				 
		 					 
		 						} 
		 			 	myList.addAll(moreList);
		 			//	myAdapter.notifyDataSetChanged();
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
				Toast.makeText(getApplicationContext(), " no 3g or wifi content", 1000).show();
			}
		});
	
	}
 
	private void initView() {
		// TODO Auto-generated method stub
		main_rl_Food=(RelativeLayout) findViewById(R.id.main_rl_Food);
		main_rl_Food.setOnClickListener(this);
		main_rl_drink=(RelativeLayout) findViewById(R.id.main_rl_drink);
		main_rl_drink.setOnClickListener(this);
		main_rl_sport=(RelativeLayout) findViewById(R.id.main_rl_sport);
		main_rl_sport.setOnClickListener(this);
		main_rl_other=(RelativeLayout) findViewById(R.id.main_rl_other);
		main_rl_other.setOnClickListener(this);
		
		main_rl_sm=(RelativeLayout) findViewById(R.id.main_rl_sm);
		main_rl_sm.setOnClickListener(this);
		main_rl_sb=(RelativeLayout) findViewById(R.id.main_rl_sb);
		main_rl_sb.setOnClickListener(this);
		main_rl_Hotel=(RelativeLayout) findViewById(R.id.main_rl_Hotel);
		main_rl_Hotel.setOnClickListener(this);
		main_post=(RelativeLayout) findViewById(R.id.main_post);
		main_post.setOnClickListener(this);
		
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.main_rl_Food:
			Intent main_rl_lec =new Intent(getApplication(),AroundList.class);
			main_rl_lec.putExtra("title", "Food");
			startActivity(main_rl_lec);
			break;
		case R.id.main_rl_drink: //main_rl_sport
			Intent main_rl_trl =new Intent(getApplication(),AroundList.class);
			main_rl_trl.putExtra("title", "Drink");
			startActivity(main_rl_trl);
			break;
		case R.id.main_rl_sport: //main_rl_Ceremony
			Intent main_rl_sport =new Intent(getApplication(),AroundList.class);
			main_rl_sport.putExtra("title", "Sports");
			startActivity(main_rl_sport);
			break;
		case R.id.main_rl_other: //main_rl_other
			Intent main_rl_other =new Intent(getApplication(),AroundList.class);
			main_rl_other.putExtra("title", "Other");
			startActivity(main_rl_other);
			break;
			
		case R.id.main_rl_Ceremony: //main_rl_sb
			Intent Ceremony =new Intent(getApplication(),EventListActivity.class);
			Ceremony.putExtra("title", "Ceremony");
			startActivity(Ceremony);
			break;
			
 
			
 
		case R.id.main_rl_la: //main_rl_other
			Intent main_rl_la =new Intent(getApplication(),EventListActivity.class);
			main_rl_la.putExtra("title", "Literature and art");
			startActivity(main_rl_la);
			break;
 
		case R.id.main_rl_et: //main_rl_et
			Intent main_rl_et =new Intent(getApplication(),EventListActivity.class);
			main_rl_et.putExtra("title", "Entertainment");
			startActivity(main_rl_et);
			break;
			
		case R.id.main_post: //main_rl_la
			Intent Forum =new Intent(getApplication(),SearchAround.class);
			Forum.putExtra("title", "Post");
			startActivity(Forum);
			break;
			
		case R.id.main_rl_Hotel: //main_rl_Forum
			Intent main_rl_Hotel =new Intent(getApplication(),SearchAround.class);
			main_rl_Hotel.putExtra("title", "Hotel");
			startActivity(main_rl_Hotel);
			break;
		 
			
			
			
		case R.id.main_rl_sb: //main_rl_Forum
			Intent main_rl_sb =new Intent(getApplication(),SearchAround.class);
			main_rl_sb.putExtra("title", "Bank");
			startActivity(main_rl_sb);
			break;
			
			
			
  
		case R.id.main_rl_sm:
			Intent main_rl_sm =new Intent(getApplication(),SearchAround.class);
			main_rl_sm.putExtra("title", "SuperMarket");
			startActivity(main_rl_sm);
			break;
		default:
			break;
		}
	}
 
 
}
