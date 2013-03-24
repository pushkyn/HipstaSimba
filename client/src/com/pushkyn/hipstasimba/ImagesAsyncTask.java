package com.pushkyn.hipstasimba;

import java.lang.ref.WeakReference;
import java.util.List;

import com.pushkyn.hipstasimba.model.HipstaImage;

import android.content.Context;
import android.os.AsyncTask;

public class ImagesAsyncTask extends AsyncTask<Void, Void, List<HipstaImage>> {

	public static interface ImagesResultListener {
		void onImagesLoaded(List<HipstaImage> images);
	}

	private final WeakReference<Context> mContext;
	private final WeakReference<ImagesResultListener> mListener;

	public ImagesAsyncTask(Context context, ImagesResultListener listener) {
		mContext = new WeakReference<Context>(context);
		mListener = new WeakReference<ImagesResultListener>(listener);
	}

	@Override
	protected List<HipstaImage> doInBackground(Void... params) {
		Context context = mContext.get();
		if (null != context) {
			//
		}
		return null;
	}

	@Override
	protected void onPostExecute(List<HipstaImage> result) {
		super.onPostExecute(result);

		ImagesResultListener listener = mListener.get();
		if (null != listener && null != result) {
			listener.onImagesLoaded(result);
		}
	}

}