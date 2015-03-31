package com.example.ices.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.baidu.mapapi.search.core.PoiInfo;
import com.examlpe.ices.util.ImageCacheUtil;
import com.example.ices.R;
 
import com.example.ices.entity.AroundEntity;
 

public class SearchAroundAdapter extends BaseAdapter{

	private Context context;
	private List<PoiInfo> list;
	private LayoutInflater inflater;
	private ViewHolder holder = null;
	public SearchAroundAdapter (Context context, List<PoiInfo> list) {
		this.context = context;
		this.list = list;
	}
	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		inflater = LayoutInflater.from(context);
 		if(convertView == null){
			holder = new ViewHolder();
			convertView = inflater.inflate(R.layout.expand_menu, null);
		 
			holder.mune_title = (TextView) convertView.findViewById(R.id.mune_title);		 
			holder.mune_img = (ImageView) convertView.findViewById(R.id.mune_img); 
		 
			convertView.setTag(holder);
 		}else{
 		holder = (ViewHolder)convertView.getTag();
 	}
 	 
 		holder.mune_img.setVisibility(View.GONE);
 		holder.mune_title.setText(list.get(position).name);
 		
 	 
 		 
 		
		
		return convertView;
	}

	public final class ViewHolder {
		public TextView mune_title;
		public ImageView mune_img;
	 
		
	}

}
