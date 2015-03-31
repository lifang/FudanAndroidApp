package com.example.ices.adapter;

import java.util.List;

import com.examlpe.ices.util.ImageCacheUtil;
import com.example.ices.Config;
import com.example.ices.R;
import com.example.ices.entity.DirectoryList;
import com.example.ices.entity.EventlistEntity;
import com.example.ices.entity.NotificationEntity;

 

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

 

public class EventAdapter extends BaseAdapter{
	private Context context;
	private List<EventlistEntity> list;
	private LayoutInflater inflater;
	private ViewHolder holder = null;
	public EventAdapter(Context context, List<EventlistEntity> list) {
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
			convertView = inflater.inflate(R.layout.event_item, null);
			holder.content = (TextView) convertView.findViewById(R.id.content);
			holder.title = (TextView) convertView.findViewById(R.id.title);		 
			holder.evevt_img = (ImageView) convertView.findViewById(R.id.evevt_img);
			convertView.setTag(holder);
 		}else{
 		holder = (ViewHolder)convertView.getTag();
 	}
 		ImageCacheUtil.IMAGE_CACHE.get( Config.IMAGE_PATH+ list.get(position).getPictureSmallFilePath(),
	 				holder.evevt_img );
 		
 		holder.title.setText(list.get(position).getEventsName());
 		
 		holder.content.setText(list.get(position).getEventsIntroductionShort());
		 
		
		return convertView;
	}

	public final class ViewHolder {
		public TextView content,title;
		public ImageView evevt_img;
		public LinearLayout ll_child_item;
	}
}
