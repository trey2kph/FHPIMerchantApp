package com.fhpi.fhpimerchantapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.gson.Gson;

import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    ListView lstData;
    TextView lblComp;
    Button btnRefresh, btnListCancel;
    CustomAdapter adapter;
    ArrayAdapter<CharSequence> adapter2;
    AllItemsDTO allItemsDTO;
    String url = "", compId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intentObj = getIntent();
        Bundle b = intentObj.getExtras();

        lblComp = (TextView) findViewById(R.id.lblComp);

        if (b != null)
        {
            compId = (String) b.get("company");
            lblComp.setText(compId);
        }

        lstData = (ListView)findViewById(R.id.lstData);
        btnRefresh = (Button)findViewById(R.id.btnRefresh);
        btnListCancel = (Button)findViewById(R.id.btnListCancel);
        //spnMerchant2 = (Spinner)findViewById(R.id.spnMerchant2);
        //spnMerchant2.setAdapter(adapter2);
        //adapter2 = new ArrayAdapter(MainActivity.this, R.layout.spinner_item, sMenu);
        url = getString(R.string.app_trans) + "/" + compId;
        //Toast.makeText(this, url, Toast.LENGTH_LONG).show();
        getTransData(url);

        /*spnMerchant2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                String url1 = getString(R.string.app_trans) + "?search=" + sMenuVal[spnMerchant2.getSelectedItemPosition()].toString();
                getTransData(url1);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });*/

        btnListCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        btnRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                startActivity(getIntent());
            }
        });

    }



    @Override
    protected void onResume() {
        compId = lblComp.getText().toString();
        url = getString(R.string.app_trans) + "/" + compId;
        super.onResume();
        getTransData(url);
    }

    private void getTransData(String url){
        String tag_json_obj = "json_obj_req";
        final ProgressDialog dialog = new ProgressDialog(this);
        dialog.setMessage("Please wait...");
        dialog.show();

        JsonObjectRequest obj = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("Volley Response: ", response.toString());
                jsonToGson(response.toString());
                dialog.cancel();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Volley Response: ", error.toString());
                dialog.cancel();
            }
        });

        AppController.getInstance().addToRequestQueue(obj, tag_json_obj);
    }

    private void jsonToGson (String response) {
        Gson gson = new Gson();
        allItemsDTO = gson.fromJson(response, AllItemsDTO.class);
        if (allItemsDTO.getItems().size() == 0) {
            allItemsDTO.getItems().clear();
            Toast.makeText(MainActivity.this, "No record", Toast.LENGTH_SHORT).show();
        }
        adapter = new CustomAdapter(allItemsDTO, this);
        lstData.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }
}
