package com.example.ices.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.Toast;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.SupportMapFragment;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.overlayutil.PoiOverlay;
import com.baidu.mapapi.search.core.CityInfo;
import com.baidu.mapapi.search.core.PoiInfo;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.poi.OnGetPoiSearchResultListener;
import com.baidu.mapapi.search.poi.PoiCitySearchOption;
import com.baidu.mapapi.search.poi.PoiDetailResult;
import com.baidu.mapapi.search.poi.PoiDetailSearchOption;
import com.baidu.mapapi.search.poi.PoiNearbySearchOption;
import com.baidu.mapapi.search.poi.PoiResult;
import com.baidu.mapapi.search.poi.PoiSearch;
import com.baidu.mapapi.search.sug.OnGetSuggestionResultListener;
import com.baidu.mapapi.search.sug.SuggestionResult;
import com.baidu.mapapi.search.sug.SuggestionSearch;
import com.baidu.mapapi.search.sug.SuggestionSearchOption;
import com.example.ices.R;

/**
 * 演示poi搜索功能
 */
public class PoiSearchList extends FragmentActivity implements
		OnGetPoiSearchResultListener, OnGetSuggestionResultListener {

	private PoiSearch mPoiSearch = null;
	private SuggestionSearch mSuggestionSearch = null;
	private BaiduMap mBaiduMap = null;
	/**
	 * 搜索关键字输入窗口
	 */
	private AutoCompleteTextView keyWorldsView = null;
	private ArrayAdapter<String> sugAdapter = null;
	private int load_Index = 0;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.about);
		// 初始化搜索模块，注册搜索事件监听
		mPoiSearch = PoiSearch.newInstance();
		mPoiSearch.setOnGetPoiSearchResultListener(this);
     	mSuggestionSearch = SuggestionSearch.newInstance();
 	    mSuggestionSearch.setOnGetSuggestionResultListener(this);
//		mPoiSearch.searchInCity((new PoiCitySearchOption())
//		.city("上海")
//		.keyword("酒店")
//		.pageNum(load_Index));
		float lat=getIntent().getFloatExtra("lat",Float.valueOf("31.302201"));
		float lng=getIntent().getFloatExtra("lng",Float.valueOf("121.510767"));
		LatLng ptCenter = new LatLng(Float.valueOf("31.302201"),Float.valueOf("121.510767"));
		mPoiSearch.searchNearby(new PoiNearbySearchOption().location(ptCenter).keyword("酒店").radius(5000).pageNum(load_Index));
		
		
//		keyWorldsView = (AutoCompleteTextView) findViewById(R.id.searchkey);
//		sugAdapter = new ArrayAdapter<String>(this,
//				android.R.layout.simple_dropdown_item_1line);
//		keyWorldsView.setAdapter(sugAdapter);
//		mBaiduMap = ((SupportMapFragment) (getSupportFragmentManager()
//				.findFragmentById(R.id.map))).getBaiduMap();
//
//		/**
//		 * 当输入关键字变化时，动态更新建议列表
//		 */
//		keyWorldsView.addTextChangedListener(new TextWatcher() {
//
//			@Override
//			public void afterTextChanged(Editable arg0) {
//
//			}
//
//			@Override
//			public void beforeTextChanged(CharSequence arg0, int arg1,
//					int arg2, int arg3) {
//
//			}
//
//			@Override
//			public void onTextChanged(CharSequence cs, int arg1, int arg2,
//					int arg3) {
//				if (cs.length() <= 0) {
//					return;
//				}
//				String city = ((EditText) findViewById(R.id.city)).getText()
//						.toString();
//				/**
//				 * 使用建议搜索服务获取建议列表，结果在onSuggestionResult()中更新
//				 */
//				mSuggestionSearch
//						.requestSuggestion((new SuggestionSearchOption())
//								.keyword(cs.toString()).city(city));
//			}
//		});

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

	/**
	 * 影响搜索按钮点击事件
	 * 
	 * @param v
	 */
//	public void searchButtonProcess(View v) {
//		EditText editCity = (EditText) findViewById(R.id.city);
//		EditText editSearchKey = (EditText) findViewById(R.id.searchkey);
//		mPoiSearch.searchInCity((new PoiCitySearchOption())
//				.city(editCity.getText().toString())
//				.keyword(editSearchKey.getText().toString())
//				.pageNum(load_Index));
//	}

//	public void goToNextPage(View v) {
//		load_Index++;
//		searchButtonProcess(null);
//	}

	public void onGetPoiResult(PoiResult result) {
		if (result == null
				|| result.error == SearchResult.ERRORNO.RESULT_NOT_FOUND) {
			Toast.makeText(PoiSearchList.this, "未找到结果 1", Toast.LENGTH_LONG)
			.show();
			return;
		}
		if (result.error == SearchResult.ERRORNO.NO_ERROR) {
//			mBaiduMap.clear();
//			PoiOverlay overlay = new MyPoiOverlay(mBaiduMap);
//			mBaiduMap.setOnMarkerClickListener(overlay);
//			overlay.setData(result);
//			overlay.addToMap();
//			overlay.zoomToSpan();
			for(int i=0;i<result.getAllPoi().size();i++){
				System.out.println("进入详情+"+result.getAllPoi().get(i).name+result.getAllPoi().get(i).address);
			}
			//System.out.println("进入详情+"+result.get);
			
			
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
			Toast.makeText(PoiSearchList.this, strInfo, Toast.LENGTH_LONG)
					.show();
		}
	}

	public void onGetPoiDetailResult(PoiDetailResult result) {
		if (result.error != SearchResult.ERRORNO.NO_ERROR) {
			Toast.makeText(PoiSearchList.this, "抱歉，未找到结果", Toast.LENGTH_SHORT)
					.show();
		} else {
			Toast.makeText(PoiSearchList.this, result.getName() + ": " + result.getAddress(), Toast.LENGTH_SHORT)
			.show();
		}
	}

	@Override
	public void onGetSuggestionResult(SuggestionResult res) {
		System.out.println("onGetSuggestionResult");
		if (res == null || res.getAllSuggestions() == null) {
			return;
		}
		sugAdapter.clear();
		for (SuggestionResult.SuggestionInfo info : res.getAllSuggestions()) {
//			if (info.key != null)
//				sugAdapter.add(info.key);
			System.out.println(info.toString());
		}
		sugAdapter.notifyDataSetChanged();
	}

	private class MyPoiOverlay extends PoiOverlay {

		public MyPoiOverlay(BaiduMap baiduMap) {
			super(baiduMap);
		}

		@Override
		public boolean onPoiClick(int index) {
			super.onPoiClick(index);
			PoiInfo poi = getPoiResult().getAllPoi().get(index);
			// if (poi.hasCaterDetails) {
				mPoiSearch.searchPoiDetail((new PoiDetailSearchOption())
						.poiUid(poi.uid));
			// }
			return true;
		}
	}
}
