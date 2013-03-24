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
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.pushkyn.hipstasimba.model.HipstaImage;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Log;

public class HipstaClient {
	
	private static final String clientID = "ab745d68e4094d7fad2d54471e1b6ff6";
	private static final String apiHost = "https://api.instagram.com";
	
	private static final String tag = "symbiosislab";

	private static final String PARAM_USERNAME = "username";
	private static final String PARAM_PASSWORD = "password";
	private static final String PARAM_SESSION_ID = "sessid";
	private static final String PARAM_SESSION_NAME = "session_name";
	private static final String PARAM_USER = "user";
	private static final String PARAM_USER_ID = "uid";

	private HttpGet mServerGet;
	public String mUrl;
	private HttpClient mClient = new DefaultHttpClient();
	private Context mCtx;

	public HipstaClient(Context _ctx) {
		mCtx = _ctx;
	}

	public String callGet(String url, List<NameValuePair> parameters) {
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
		String uri = apiHost + "/v1/tags/" + tag + "/media/recent?callback=?&client_id=" + clientID;
		String response = callGet(uri, null);
		Log.v("response", response);
		return null;
	//	return response == null ? null : Node.createNodeListFromJson(response);
	}

}