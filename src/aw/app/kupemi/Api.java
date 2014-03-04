package aw.app.kupemi;

import android.text.TextUtils;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.ResponseHandlerInterface;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by User on 3/5/14.
 */
public class Api {
	public static final String API_KEY = "78cc1abf180f90c46500f924248e6173";
	public static final String baseUrl = "http://api.pemiluapi.org/candidate/api/";

	/**
	 * Get candidates
	 * @param limit limit of results
	 * @param offset offset of results
	 * @param extraParams extra params to pass in to the query in the form of key1=val1([&key2=val2]+)
	 * @return
	 */
	public static void getCandidates(int limit, int offset, String extraParams, ResponseHandlerInterface handler) {
		AsyncHttpClient client = new AsyncHttpClient();
		String urlToCall = baseUrl + "caleg?apiKey=" + API_KEY;

		//Construct the url
		extraParams += "&limit=" + limit;
		extraParams += "&offset=" + offset;

		if(!TextUtils.isEmpty(extraParams)) {
			urlToCall += "&" + extraParams;
		}

		Rujak.d(urlToCall);
		client.get(urlToCall, null, handler);
	}

	public static ArrayList<Candidate> getProvinces() {
		AsyncHttpClient client = new AsyncHttpClient();
		String urlToCall = baseUrl + "provinsi?apiKey=" + API_KEY;
		Rujak.d(urlToCall);
		client.get(urlToCall, null, new JsonHttpResponseHandler() {
			@Override
			public void onSuccess(JSONObject response) {
				super.onSuccess(response);
				try {
					Rujak.d(response.toString(4));
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		});

		return null;
	}

	public static JSONObject getProvinceById(int id) {
		AsyncHttpClient client = new AsyncHttpClient();
		String urlToCall = baseUrl + "provinsi/" + id + "?apiKey=" + API_KEY;
		Rujak.d(urlToCall);
		client.get(urlToCall, null, new JsonHttpResponseHandler() {
			@Override
			public void onSuccess(JSONObject response) {
				super.onSuccess(response);
				try {
					Rujak.d(response.toString(4));
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		});

		return null;

	}
}
