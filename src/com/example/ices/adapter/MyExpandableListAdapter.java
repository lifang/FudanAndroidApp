package com.example.ices.adapter;

import java.util.List;

import com.examlpe.ices.util.ItemLinear;
import com.example.ices.R;
import com.example.ices.entity.DirectoryList;
import com.example.ices.entity.MenuTitle;
 
 

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MyExpandableListAdapter extends BaseExpandableListAdapter{
	
	List<MenuTitle> list;
    private Context context;
	private LayoutInflater child_inflater;
	private LayoutInflater menu_inflater;
	private menuView menuview = null;
	private chlidView childview = null;
	
	public MyExpandableListAdapter(Context context, List<MenuTitle> list
		 ) {
	//	this.onClick = onClick;
		this.context = context;
		this.list = list;

	}
	
	@Override
	public int getGroupCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		// TODO Auto-generated method stub
		return list.get(groupPosition).getContentList().size();
	}

	@Override
	public Object getGroup(int groupPosition) {
		// TODO Auto-generated method stub
		return list.get(groupPosition);
	}

	@Override
	public Object getChild(int groupPosition, int childPosition) {
		// TODO Auto-generated method stub
		return list.get(groupPosition).getContentList().get(childPosition);
	}

	@Override
	public long getGroupId(int groupPosition) {
		// TODO Auto-generated method stub
		return groupPosition;
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		// TODO Auto-generated method stub
		return childPosition;
	}

	@Override
	public boolean hasStableIds() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public View getGroupView(int groupPosition, boolean isExpanded,
			View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		menu_inflater = LayoutInflater.from(context);
		if (convertView == null) {
			menuview = new menuView();
			convertView = menu_inflater
					.inflate(R.layout.expand_menu, null);
		 
			
			
			
			menuview.mune_title = (TextView) convertView
					.findViewById(R.id.mune_title);	
			menuview.mune_img=(ImageView) convertView.findViewById(R.id.mune_img);
			convertView.setTag(menuview);
		} else {
			menuview = (menuView) convertView.getTag();
		}
		
		if(list.get(groupPosition).getContentList().size()==0||list.get(groupPosition).getContentList().size()==1 ){
			menuview.mune_img.setVisibility(View.GONE);
		}else{
			 
			 if(isExpanded){
				 menuview.mune_img.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.top));
			 }else{
				 menuview.mune_img.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.item_down));
			 }
			 menuview.mune_img.setVisibility(View.VISIBLE);
		}
		menuview.mune_title.setText(list.get(groupPosition).getDirectoryName());
		System.out.println("list.get(groupPosition).getDirectoryName()"+list.get(groupPosition).getDirectoryName());
		//menuview.setOn
		return convertView;
	}

	@Override
	public View getChildView(int groupPosition, int childPosition,
			boolean isLastChild, View convertView1, ViewGroup parent) {
		// TODO Auto-generated method stub
 
 
		convertView1 = menu_inflater.inflate(R.layout.expand_menu, null); 
		TextView tv =(TextView) convertView1.findViewById(R.id.mune_title);
		ImageView IVV=(ImageView) convertView1.findViewById(R.id.mune_img);
		RelativeLayout ll= (RelativeLayout) convertView1.findViewById(R.id.menu_rl_c);
		IVV.setVisibility(View.GONE);
		ll.setBackgroundColor(context.getResources().getColor(R.color.DEF0FA));
		tv.setPadding(50,  0, 0,  0);
		tv.setText(list.get(groupPosition).getContentList().get(childPosition).getContentTitle());
		return convertView1;
            
           
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		// TODO Auto-generated method stub
		return true;
	}
	 
	 final static class menuView {
		public TextView mune_title;
		public ImageView mune_img;
	 
	}
	final static class chlidView {
		public TextView expand_child_tv;
	}
}
