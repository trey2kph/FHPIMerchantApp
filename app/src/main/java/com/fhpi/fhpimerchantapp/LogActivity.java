package com.fhpi.fhpimerchantapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class LogActivity extends AppCompatActivity implements View.OnClickListener {

    EditText txtLogin, txtPassword;
    Button btnLogin;

    String logurl, logSuccess, compid, username, userid;

    JSONParser jsonParser=new JSONParser();

    int i=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log);

        castObj();

        btnLogin.setOnClickListener(this);
    }

    private void castObj() {
        txtLogin = (EditText)findViewById(R.id.txtLogin);
        txtPassword = (EditText)findViewById(R.id.txtPassword);
        btnLogin = (Button)findViewById(R.id.btnLogin);
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()) {
            case R.id.btnLogin:
                AttemptLogin attemptLogin= new AttemptLogin();
                attemptLogin.execute(txtLogin.getText().toString(), txtPassword.getText().toString(), "");
                break;
        }

    }

    private class AttemptLogin extends AsyncTask<String, String, JSONObject> {

        @Override

        protected void onPreExecute() {

            super.onPreExecute();

        }

        @Override

        protected JSONObject doInBackground(String... args) {

            String password = args[1];
            String name= args[0];

            logurl = getString(R.string.app_log);

            ArrayList params = new ArrayList();
            params.add(new BasicNameValuePair("username", name));
            params.add(new BasicNameValuePair("password", password));

            JSONObject json = jsonParser.makeHttpRequest(logurl, "POST", params);

            return json;

        }

        protected void onPostExecute(JSONObject result) {

            // dismiss the dialog once product deleted
            //Toast.makeText(getApplicationContext(),result,Toast.LENGTH_LONG).show();

            try {
                if (result != null) {
                    userid = result.getString("userid");
                    username = result.getString("username");
                    compid = result.getString("usercomp");
                    logSuccess = result.getString("success");
                    if (logSuccess.equals("1")) {
                        Intent intentObj = new Intent(LogActivity.this, MainActivity.class);
                        intentObj.putExtra("userid", userid);
                        intentObj.putExtra("username", username);
                        intentObj.putExtra("company", compid);
                        startActivity(intentObj);
                    } else {
                        Toast.makeText(getApplicationContext(), result.getString("message"), Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Unable to retrieve any data from server", Toast.LENGTH_LONG).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }


        }

    }
}