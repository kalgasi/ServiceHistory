package com.rzk.servicehistory;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;


public class ServiceDetailActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_service);
        Intent intent = getIntent();

        if (intent != null) {
            Bundle serviceInfo = intent.getExtras();
            TextView textView = (TextView) findViewById(R.id.tv_service_name);
            textView.setText(serviceInfo.getString("serviceName"));
            textView = (TextView) findViewById(R.id.tv_date);
            textView.setText(serviceInfo.getString("serviceDate"));
            textView = (TextView) findViewById(R.id.tv_spare);
            textView.setText(serviceInfo.getString("serviceSparePart"));
            textView = (TextView) findViewById(R.id.tv_info);
            textView.setText(serviceInfo.getString("serviceInfo"));
            Toast.makeText(this, "Data Received", Toast.LENGTH_SHORT).show();
        }
    }

    public void backToMenu(View view) {
        finish();
    }

}
