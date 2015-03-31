package com.example.ices.adapter;

import java.util.List;

import com.example.ices.R;
import com.example.ices.entity.DirectoryList;

 

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

 

public class PreArrivalAdapter extends BaseAdapter{
	private Context context;
	private List<DirectoryList> list;
	private LayoutInflater inflater;
	private ViewHolder holder = null;
	public PreArrivalAdapter(Context context, List<DirectoryList> list) {
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
			convertView = inflater.inflate(R.layout.list_one_item, null);
			holder.ll_child_item = (LinearLayout) convertView.findViewById(R.id.ll_child_item);
			holder.mune_title = (TextView) convertView.findViewById(R.id.mune_title);
		 
		//	holder.ll_child_item.setVisibility(View.GONE);
			convertView.setTag(holder);
 		}else{
 		holder = (ViewHolder)convertView.getTag();
 	}
		
		holder.mune_title.setText(list.get(position).getDirectoryTitle());
 
		if(list.get(position).getContentList().size()!=0){
			for(int i1 =0;i1<list.get(position).getContentList().size();i1++){
				TextView  tttt= new TextView(context);
				tttt.setText(list.get(position).getContentList().get(i1).getDirectory_contentContentTitle());
				tttt.setTag(list.get(position).getContentList().get(i1).getDirectory_contentId());
				tttt.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						Toast.makeText(context, "++"+v.getTag().toString(), 1000).show();
					}
				});
				holder.ll_child_item.addView(tttt);
			}
		}
		
		
		//((LinearLayout.LayoutParams) holder.ll_child_item.getLayoutParams()).bottomMargin = -120;
		
		return convertView;
	}

	public final class ViewHolder {
		public TextView mune_title;
 
		public LinearLayout ll_child_item;
	}
}
