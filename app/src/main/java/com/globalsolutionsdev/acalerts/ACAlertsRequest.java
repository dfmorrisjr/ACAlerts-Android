package com.globalsolutionsdev.acalerts;

/**
 * Created by dmorris on 11/15/2014.
 */
import android.util.Base64;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import com.android.volley.AuthFailureError;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;

public class ACAlertsRequest extends JsonArrayRequest {

    public static String _username = "";
    public static String _password = "";

    public ACAlertsRequest(String url, Listener<JSONArray> listener, ErrorListener errorListener, String username, String password) {
        //super(url + "?" + URLEncodedUtils.format(params, "UTF-8"), null, listener, errorListener);
        super(url, listener, errorListener);
        //super(Method.GET, url, null, listener, errorListener);

        _username = username;
        _password = password;

    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        Map<String, String> headers = new HashMap<String, String>();
        //String auth = "Bearer " + TwitterValues.ACCESS_TOKEN;
        //String creds = String.format("%s:%s","dfmorrisjr@gmail.com","donttell");
        String creds = String.format("%s:%s",_username,_password);
        String auth = "Basic " + Base64.encodeToString(creds.getBytes(), Base64.NO_WRAP);
        headers.put("Authorization", auth);
        return headers;
    }
}
