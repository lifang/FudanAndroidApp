package com.example.ices.adapter;

import java.util.List;

import com.examlpe.ices.util.ImageCacheUtil;
import com.example.ices.Config;
import com.example.ices.R;
import com.example.ices.entity.DirectoryList;
import com.example.ices.entity.NotifiEntity;
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

 

public class MessageAdapter extends BaseAdapter{
	private Context context;
	private List<NotifiEntity> list;
	private LayoutInflater inflater;
	private ViewHolder holder = null;
	public MessageAdapter(Context context, List<NotifiEntity> list) {
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
			convertView = inflater.inflate(R.layout.item_message, null);
			holder.item_content = (TextView) convertView.findViewById(R.id.item_content);
			holder.mune_title = (TextView) convertView.findViewById(R.id.mune_title);		 
			holder.msg_image = (ImageView) convertView.findViewById(R.id.msg_image);
			convertView.setTag(holder);
 		}else{
 		holder = (ViewHolder)convertView.getTag();
 	}
 		if(list.get(position).getPictureSmallFilePath()!=null){
 			holder.msg_image.setVisibility(View.VISIBLE);
 			ImageCacheUtil.IMAGE_CACHE.get(  Config.IMAGE_PATH+list.get(position).getPictureSmallFilePath(),
 	 				holder.msg_image );
 		 
 			holder.mune_title.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.toumingbg));
 		}else{
 			holder.mune_title.setBackgroundColor(context.getResources().getColor(R.color.EBECEC));
 			holder.msg_image.setVisibility(View.GONE); //toumingbg
 		}
 		System.out.println(" list.get(position).getPictureSmallFilePath()"+ list.get(position).getPictureSmallFilePath());
 		if(list.get(position).getNotificationIsRead()==1){
 			holder.mune_title.setTextColor(context.getResources().getColor(R.color.C012642));
 			holder.item_content.setTextColor(context.getResources().getColor(R.color.C464545));
 		}else{
 			holder.mune_title.setTextColor(context.getResources().getColor(R.color.text_read));
 			holder.item_content .setTextColor(context.getResources().getColor(R.color.C343333)); //343333
 		}
 		
 		holder.mune_title.setText(list.get(position).getNotificationTitle());
 		holder.item_content.setText(list.get(position).getNotificationShortContent());
		 
		
		return convertView;
	}

	public final class ViewHolder {
		public TextView mune_title,item_content;
		public ImageView msg_image;
		public LinearLayout ll_child_item;
	}
}
