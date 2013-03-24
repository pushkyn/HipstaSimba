package com.pushkyn.hipstasimba;

import java.util.ArrayList;
import java.util.List;

import com.androidquery.AQuery;
import com.pushkyn.hipstasimba.model.HipstaImage;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

public class ImagesAdapter extends BaseAdapter {

	Context ctx;
	LayoutInflater lInflater;
	ArrayList<HipstaImage> objects;
	AQuery aq;

	public ImagesAdapter(Context context, ArrayList<HipstaImage> products) {
		ctx = context;
		objects = products;
		lInflater = (LayoutInflater) ctx
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		aq = new AQuery(context);
	}

	@Override
	public int getCount() {
		return objects.size();
	}

	@Override
	public Object getItem(int position) {
		return objects.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ImageView imageView = new ImageView(ctx);
		// imageView.setImageResource(mThumbIds[position]);
		aq.id(imageView).image(objects.get(position).url);
		imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
		imageView.setLayoutParams(new GridView.LayoutParams(70, 70));
		return imageView;
	}

	public void clear() {
		objects.clear();
	}

	public void addAll(List<HipstaImage> images) {
		objects.addAll(images);
	}

	public void add(HipstaImage node) {
		objects.add(node);
	}

	public HipstaImage getHipstaImage(int position) {
		return ((HipstaImage) getItem(position));
	}

}