package com.pushkyn.hipstasimba.core;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.pushkyn.hipstasimba.model.HipstaImage;

import android.content.Context;
import android.util.Log;

public class HipstaClient {

	private static final String clientID = "ab745d68e4094d7fad2d54471e1b6ff6";
	private static final String apiHost = "https://api.instagram.com";

	private static final String tag = "symbiosislab";

	private HttpGet mServerGet;
	public String mUrl;
	private HttpClient mClient = new DefaultHttpClient();
	private Context mCtx;

	public HipstaClient(Context _ctx) {
		mCtx = _ctx;
	}

	private String callGet(String url, List<NameValuePair> parameters) {
		if (parameters != null && !parameters.isEmpty()) {
			url = url + "?" + URLEncodedUtils.format(parameters, "UTF-8");
		}
		mServerGet = new HttpGet(url);
		mServerGet.setHeader("Content-type", "application/json");
		try {
			HttpResponse response = mClient.execute(mServerGet);

			System.out.println("Status " + response.getStatusLine());

			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				InputStream is = response.getEntity().getContent();
				String result = toString(is);
				System.out.println("response url " + url);
				System.out.println("response get " + result);
				return result;
			} else {
				return null;
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	private static String toString(InputStream is) {
		StringBuilder stringBuilder = new StringBuilder();
		try {
			String string;
			if (is != null) {
				BufferedReader br = new BufferedReader(
						new InputStreamReader(is));
				while (null != (string = br.readLine())) {
					stringBuilder.append(string).append('\n');
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return stringBuilder.toString();
	}

	public List<HipstaImage> imagesGet(String id) {
		String uri = apiHost + "/v1/tags/" + tag
				+ "/media/recent?callback=?&client_id=" + clientID;
		String response = callGet(uri, null);
		Log.v("response", response);
		List<HipstaImage> result = new ArrayList<HipstaImage>();
		try {
			JSONObject jo = new JSONObject(response);
			JSONArray data = jo.getJSONArray("data");
			for (int i = 0; i < data.length(); i++) {
				JSONObject jso = data.getJSONObject(i);
				JSONObject images = jso.getJSONObject("images");
				JSONObject low = images.getJSONObject("low_resolution");
				String url = low.getString("url");
				Log.v("HipstaClient", url);
				result.add(new HipstaImage(url));

			}
			return result;
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

}