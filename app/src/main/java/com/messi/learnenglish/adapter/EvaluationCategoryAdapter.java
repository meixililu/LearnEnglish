package com.messi.learnenglish.adapter;

import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.avos.avoscloud.AVObject;
import com.messi.learnenglish.EvaluationCategoryListActivity;
import com.messi.learnenglish.R;
import com.messi.learnenglish.util.AVOUtil;
import com.messi.learnenglish.util.KeyUtil;

public class EvaluationCategoryAdapter extends BaseAdapter {

	private LayoutInflater mInflater;
	private Context context;
	private List<AVObject> avObjects;
	
	public EvaluationCategoryAdapter(Context mContext, List<AVObject> avObjects) {
		context = mContext;
		this.mInflater = LayoutInflater.from(mContext);
		this.avObjects = avObjects;
	}

	public int getCount() {
		return avObjects.size();
	}

	public Object getItem(int position) {
		return avObjects.get(position);
	}

	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		final ViewHolder holder;
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.studylist_gridview_item, null);
			holder = new ViewHolder();
			holder.cover = (View) convertView.findViewById(R.id.layout_cover);
			holder.name = (TextView) convertView.findViewById(R.id.name);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		final AVObject mAVObject = avObjects.get(position);
		String itemName = "";
		String itemNameOld = mAVObject.getString(AVOUtil.EvaluationCategory.ECName);
		if(itemNameOld.contains("-")){
			itemName = itemNameOld.split("-")[1];
		}else{
			itemName = itemNameOld;
		}
		holder.name.setText( itemName );
		holder.cover.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				onItemClick(mAVObject);
			}
		});
		return convertView;
	}

	static class ViewHolder {
		View cover;
		TextView name;
	}

	private void onItemClick(AVObject mAVObject){
		Intent intent = new Intent(context,EvaluationCategoryListActivity.class);
		intent.putExtra(KeyUtil.ActionbarTitle, mAVObject.getString(AVOUtil.EvaluationCategory.ECName));
		intent.putExtra(AVOUtil.EvaluationCategory.ECCode, mAVObject.getString(AVOUtil.EvaluationCategory.ECCode));
		context.startActivity(intent);
	}
	

}
