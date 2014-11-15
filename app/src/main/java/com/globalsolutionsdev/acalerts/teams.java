package com.globalsolutionsdev.acalerts;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONException;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request.Method;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.globalsolutionsdev.acalerts.app.AppController;


public class teams extends Activity {

    // json object response url
    private String urlGetTeams = "http://54.84.48.247/acalerts/public/index.php/api/v1/team";
    private String urlGetTeamsa = "http://54.84.48.247/acalerts/public/index.php/api/v1.1/teama";
    private String urlJsonObj = "http://api.androidhive.info/volley/person_object.json";

    private static String TAG = teams.class.getSimpleName();
    private Button btnGetTeams;

    private ProgressDialog pDialog;
    private TextView txtResponse;

    private String jsonResponse;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teams);

        btnGetTeams = (Button) findViewById(R.id.getTeams);
        txtResponse = (TextView) findViewById(R.id.txtResponse);

        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Please wait...");
        pDialog.setCancelable(false);

        btnGetTeams.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                // making json object request
                //makeJsonObjectRequest(); // this works

                makeJsonArrayRequest();
            }
        });

    }

    private void makeJsonObjectRequest(){
        showpDialog();

        JsonObjectRequest jsonObjRequest = new JsonObjectRequest(Method.GET, urlGetTeams,null,
                new Response.Listener<JSONObject>(){

                    @Override
                    public void onResponse(JSONObject response){
                        Log.d(TAG, response.toString());

                        try{
                            //Parsing json object response.
                            //response will be a json object

                            /* Original code is here.  This works
                            String name = response.getString("name");
                            String email = response.getString("email");
                            JSONObject phone = response.getJSONObject("phone");
                            String home = phone.getString("home");
                            String mobile = phone.getString("mobile");

                            jsonResponse = "";
                            jsonResponse += "Name: " + name + "\n\n";
                            jsonResponse += "Email: " + email + "\n\n";
                            jsonResponse += "Home: " + home + "\n\n";
                            jsonResponse += "Mobile: " + mobile + "\n\n";
                            */
                            JSONObject teams = response.getJSONObject("teams");
                            String teamName = teams.getString("TeamName");

                            jsonResponse = "";
                            jsonResponse += "TeamName: " + teamName + "\n\n";

                            txtResponse.setText(jsonResponse);
                        }
                        catch(JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(), "Error: " + e.getMessage(),
                                    Toast.LENGTH_LONG).show();
                        }

                        hidepDialog();

                    }

                },new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                VolleyLog.d(TAG, "Error:" + volleyError.getMessage());
                Toast.makeText(getApplicationContext(), volleyError.getMessage(), Toast.LENGTH_SHORT).show();

                // hide the progress dialog
                hidepDialog();
            }
        }
        );

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(jsonObjRequest);
    }

    private void makeJsonArrayRequest() {

        showpDialog();

        /* This works getting json with no authentication
        JsonArrayRequest req = new JsonArrayRequest(urlGetTeams, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Log.d(TAG, response.toString());
                try {
                    jsonResponse = "";
                    for (int i = 0; i < response.length(); i++) {
                        JSONObject team = (JSONObject) response.get(i);

                        String teamname = team.getString("TeamName");
                        String twilio = team.getString("TwilioPhoneNumber");

                        jsonResponse += "Team Name: " + teamname + "\n\n";
                        jsonResponse += "Twilio: " + twilio + "\n\n";

                    }

                    txtResponse.setText(jsonResponse);
                }
                catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(),
                            "Error:" + e.getMessage(),
                            Toast.LENGTH_LONG).show();

                }
                hidepDialog();
            }

        }, new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error){
                VolleyLog.d(TAG,"Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),error.getMessage(),Toast.LENGTH_SHORT).show();
                hidepDialog();
            }

        });
        */

        JsonArrayRequest req = new ACAlertsRequest(urlGetTeamsa, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Log.d(TAG, response.toString());
                try {
                    jsonResponse = "";
                    for (int i = 0; i < response.length(); i++) {
                        JSONObject team = (JSONObject) response.get(i);

                        String teamname = team.getString("TeamName");
                        String twilio = team.getString("TwilioPhoneNumber");

                        jsonResponse += "Team Name: " + teamname + "\n\n";
                        jsonResponse += "Twilio: " + twilio + "\n\n";

                    }

                    txtResponse.setText(jsonResponse);
                }
                catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(),
                            "Error:" + e.getMessage(),
                            Toast.LENGTH_LONG).show();

                }
                hidepDialog();
            }

        }, new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error){
                VolleyLog.d(TAG,"Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),error.getMessage(),Toast.LENGTH_SHORT).show();
                hidepDialog();
            }

        });

        AppController.getInstance().addToRequestQueue(req);

    }

    private void showpDialog(){
        if(!pDialog.isShowing()) {
            pDialog.show();
        }
    }

    private void hidepDialog(){
        if(pDialog.isShowing()){
            pDialog.dismiss();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_teams, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
