package com.messi.learnenglish.adapter;

import java.util.List;

import com.avos.avoscloud.AVObject;
import com.messi.learnenglish.BaseApplication;
import com.messi.learnenglish.R;
import com.messi.learnenglish.SymbolDetailActivity;
import com.messi.learnenglish.dao.SymbolListDao;
import com.messi.learnenglish.util.AVOUtil;
import com.messi.learnenglish.util.KeyUtil;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class SymbolListAdapter extends BaseAdapter {

	private LayoutInflater mInflater;
	private Context context;
	private List<SymbolListDao> avObjects;
	
	public SymbolListAdapter(Context mContext, List<SymbolListDao> avObjects) {
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
			convertView = mInflater.inflate(R.layout.symbol_list_item, null);
			holder = new ViewHolder();
			holder.cover = (View) convertView.findViewById(R.id.layout_cover);
			holder.name = (TextView) convertView.findViewById(R.id.name);
			holder.des = (TextView) convertView.findViewById(R.id.des);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		final SymbolListDao mAVObject = avObjects.get(position);
		holder.name.setText( mAVObject.getSDName() );
		holder.des.setText( mAVObject.getSDDes() );
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
		TextView des;
	}

	private void onItemClick(SymbolListDao mAVObject){
		BaseApplication.dataMap.put(KeyUtil.DataMapKey, mAVObject);
		Intent intent = new Intent(context,SymbolDetailActivity.class);
		intent.putExtra(KeyUtil.ActionbarTitle, mAVObject.getSDDes() + mAVObject.getSDName());
		context.startActivity(intent);
	}
	

}
