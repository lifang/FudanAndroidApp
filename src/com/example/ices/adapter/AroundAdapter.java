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

import com.examlpe.ices.util.ImageCacheUtil;
import com.example.ices.Config;
import com.example.ices.R;
 
import com.example.ices.entity.AroundEntity;
 

public class AroundAdapter extends BaseAdapter{

	private Context context;
	private List<AroundEntity> list;
	private LayoutInflater inflater;
	private ViewHolder holder = null;
	public AroundAdapter (Context context, List<AroundEntity> list) {
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
			convertView = inflater.inflate(R.layout.around_item, null);
			holder.content = (TextView) convertView.findViewById(R.id.content);
			holder.title = (TextView) convertView.findViewById(R.id.title);		 
			holder.evevt_img = (ImageView) convertView.findViewById(R.id.evevt_img); 
			holder.img_type = (ImageView) convertView.findViewById(R.id.img_type);
			convertView.setTag(holder);
 		}else{
 		holder = (ViewHolder)convertView.getTag();
 	}
 		ImageCacheUtil.IMAGE_CACHE.get( Config.IMAGE_PATH+ list.get(position).getPictureSmallFilePath(),
	 				holder.evevt_img );
 		
 		holder.title.setText(list.get(position).getAroundcampusName());
 		
 		holder.content.setText(list.get(position).getAroundcampusIntroductionShort());
		 
 		if(list.get(position).getAroundcampusType()==1){
 			holder.img_type.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.item_food));
 		} 
 		else if(list.get(position).getAroundcampusType()==2){
 			holder.img_type.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.item_sport));
 		} 
 		else if(list.get(position).getAroundcampusType()==3){
 			holder.img_type.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.itemdrink));
 		} 
 		 System.out.println("add");
 		
		
		return convertView;
	}

	public final class ViewHolder {
		public TextView content,title;
		public ImageView evevt_img,img_type;
		public LinearLayout ll_child_item;
		
	}

}
