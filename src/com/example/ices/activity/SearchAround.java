package com.example.ices.activity;
 

import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

 

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.CityInfo;
import com.baidu.mapapi.search.core.PoiInfo;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.poi.OnGetPoiSearchResultListener;
import com.baidu.mapapi.search.poi.PoiDetailResult;
import com.baidu.mapapi.search.poi.PoiNearbySearchOption;
import com.baidu.mapapi.search.poi.PoiResult;
import com.baidu.mapapi.search.poi.PoiSearch;
import com.baidu.mapapi.search.sug.OnGetSuggestionResultListener;
import com.baidu.mapapi.search.sug.SuggestionResult;
import com.baidu.mapapi.search.sug.SuggestionSearch;
import com.examlpe.ices.util.TitleMenuUtil;
import com.examlpe.ices.util.Tools;
import com.examlpe.ices.util.XListView;
import com.examlpe.ices.util.XListView.IXListViewListener;
import com.example.ices.BaseActivity;
import com.example.ices.Config;
import com.example.ices.MyApplication;
import com.example.ices.R;
import com.example.ices.adapter.EventAdapter;
import com.example.ices.adapter.SearchAroundAdapter;
import com.example.ices.entity.EventlistEntity;
import com.example.ices.entity.User;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

public class SearchAround extends BaseActivity implements  IXListViewListener ,
OnGetPoiSearchResultListener, OnGetSuggestionResultListener{
		private XListView event_listview;
		private String title;
		private int page=0;
		private int rows=Config.ROWS;
		private LinearLayout eva_nodata;
		private boolean onRefresh_number = true;
		private SearchAroundAdapter myAdapter;
		private String type ;
		//List<PoiInfo> 
		private PoiSearch mPoiSearch = null;
		private SuggestionSearch mSuggestionSearch = null;
		private BaiduMap mBaiduMap = null;
		private LatLng ptCenter;
		/**
		 * 搜索关键字输入窗口
		 */
		private AutoCompleteTextView keyWorldsView = null;
		private ArrayAdapter<String> sugAdapter = null;
		private int load_Index = 0;

		List<PoiInfo>  myList = new ArrayList<PoiInfo>();
		List<PoiInfo>  moreList = new ArrayList<PoiInfo>();
		private Handler handler = new Handler() {
			public void handleMessage(Message msg) {
				switch (msg.what) {
				case 0:
					onLoad( );
					
					if(myList.size()==0){
					//	norecord_text_to.setText("您没有相关的商品");
						event_listview.setVisibility(View.GONE);
						eva_nodata.setVisibility(View.VISIBLE);
					}
					onRefresh_number = true; 
				 	myAdapter.notifyDataSetChanged();
					break;
				case 1:
					Toast.makeText(getApplicationContext(), (String) msg.obj,
							Toast.LENGTH_SHORT).show();
				 
					break;
				case 2: // 网络有问题
					Toast.makeText(getApplicationContext(), "no 3g or wifi content",
							Toast.LENGTH_SHORT).show();
					break;
				case 3:
					Toast.makeText(getApplicationContext(),  " refresh too much",
							Toast.LENGTH_SHORT).show();
					break;
				}
			}
		};
		@Override
		protected void onCreate(Bundle savedInstanceState) {
			// TODO Auto-generated method stub
			super.onCreate(savedInstanceState);
			setContentView(R.layout.eventlist);
			title=getIntent().getStringExtra("title");
			myAdapter=new SearchAroundAdapter(SearchAround.this, myList);
 
			System.out.println("title``"+title);
			if(title==null){
				title="Hotel";
			}
			initView();
			mPoiSearch = PoiSearch.newInstance();
			mPoiSearch.setOnGetPoiSearchResultListener(this);
	     	mSuggestionSearch = SuggestionSearch.newInstance();
	 	    mSuggestionSearch.setOnGetSuggestionResultListener(this);
//			mPoiSearch.searchInCity((new PoiCitySearchOption())
//			.city("上海")
//			.keyword("酒店")
//			.pageNum(load_Index));
			float lat=getIntent().getFloatExtra("lat",Float.valueOf("31.302201"));
			float lng=getIntent().getFloatExtra("lng",Float.valueOf("121.510767"));
			 ptCenter = new LatLng(Float.valueOf("31.302201"),Float.valueOf("121.510767"));
			getData();
		}
		@Override
		protected void onPause() {
			super.onPause();
		}

		@Override
		protected void onResume() {
			super.onResume();
		}

		@Override
		protected void onDestroy() {
			mPoiSearch.destroy();
			mSuggestionSearch.destroy();
			super.onDestroy();
		}

		@Override
		protected void onSaveInstanceState(Bundle outState) {
			super.onSaveInstanceState(outState);
		}

		@Override
		protected void onRestoreInstanceState(Bundle savedInstanceState) {
			super.onRestoreInstanceState(savedInstanceState);
		}

		private void initView() {
			// TODO Auto-generated method stub
			
			new TitleMenuUtil(SearchAround.this, title).show();
			eva_nodata=(LinearLayout) findViewById(R.id.eva_nodata);
			event_listview=(XListView) findViewById(R.id.event_listview);
			// refund_listview.getmFooterView().getmHintView().setText("已经没有数据了");
			event_listview.setPullLoadEnable(true);
			event_listview.setXListViewListener(this);
			event_listview.setDivider(null);
 
			event_listview.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					// TODO Auto-generated method stub
 				Intent i = new Intent(SearchAround.this,GeoCoderMap.class);
 				i.putExtra("lat", myList.get(position-1).location.latitude+"");
 				i.putExtra("lng",myList.get(position-1).location.longitude+"" );
 				i.putExtra("title",myList.get(position-1).name);
 				startActivityForResult(i, 10);
				}
			});
			event_listview.setAdapter(myAdapter);
			
			if(title.endsWith("SuperMarket")){
				type="超市";
			}
			if(title.endsWith("Bank")){
				type="银行";
			}
			if(title.endsWith("Post")){
				type="邮局";
			}
			if(title.endsWith("Hotel")){
				type="酒店";
			}
 
		}
		@Override
		public void onRefresh() {
			// TODO Auto-generated method stub
			page = 0;
			myList.clear();
			getData();
		}

 
		@Override
		public void onLoadMore() {
			// TODO Auto-generated method stub
			if (onRefresh_number) {
				page = page+1;
				if (Tools.isConnect(getApplicationContext())) {
					onRefresh_number = false;
					getData();
				} else {
					onRefresh_number = true;
					handler.sendEmptyMessage(2);
				}
			}
			else {
				handler.sendEmptyMessage(3);
			}
		}
		private void onLoad() {
			event_listview.stopRefresh();
			event_listview.stopLoadMore();
			event_listview.setRefreshTime(Tools.getHourAndMin());
		}

		public void buttonClick() {
			page = 0;
			myList.clear();
			getData();
		}
		private void getData() { 
			System.out.println("page--"+page);
			mPoiSearch.searchNearby(new PoiNearbySearchOption().location(ptCenter).keyword(type).radius(3000).pageNum(page));
		}
		@Override
		public void onGetSuggestionResult(SuggestionResult arg0) {
			// TODO Auto-generated method stub
			
		}
		@Override
		public void onGetPoiDetailResult(PoiDetailResult arg0) {
			// TODO Auto-generated method stub
			
		}
		@Override
		public void onGetPoiResult(PoiResult result) {
			// TODO Auto-generated method stub
			if (result == null
					|| result.error == SearchResult.ERRORNO.RESULT_NOT_FOUND) {
				Toast.makeText(SearchAround.this, "未找到结果 1", Toast.LENGTH_LONG)
				.show();
				return;
			}
			if (result.error == SearchResult.ERRORNO.NO_ERROR) {
//				mBaiduMap.clear();
//				PoiOverlay overlay = new MyPoiOverlay(mBaiduMap);
//				mBaiduMap.setOnMarkerClickListener(overlay);
//				overlay.setData(result);
//				overlay.addToMap();
//				overlay.zoomToSpan();
				moreList.clear();
				for(int i=0;i<result.getAllPoi().size();i++){
					System.out.println("进入详情+"+result.getAllPoi().get(i).name+result.getAllPoi().get(i).address);
					moreList.add(result.getAllPoi().get(i));
				}
				//System.out.println("进入详情+"+result.get);
				if (moreList.size()==0) {
						Toast.makeText(getApplicationContext(),
								"no more data", Toast.LENGTH_SHORT).show();
						event_listview.getmFooterView().setState2(2);
				 
					} 
 				myList.addAll(moreList);
 				handler.sendEmptyMessage(0);
				return;
			}
			if (result.error == SearchResult.ERRORNO.AMBIGUOUS_KEYWORD) {

				// 当输入关键字在本市没有找到，但在其他城市找到时，返回包含该关键字信息的城市列表
				String strInfo = "在";
				for (CityInfo cityInfo : result.getSuggestCityList()) {
					strInfo += cityInfo.city;
					strInfo += ",";
				}
				strInfo += "找到结果 3";
				Toast.makeText(SearchAround.this, strInfo, Toast.LENGTH_LONG)
						.show();
			}
		}
}
