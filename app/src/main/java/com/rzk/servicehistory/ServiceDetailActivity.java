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
        Intent intent=getIntent();

        if(intent!=null){
            Bundle serviceInfo=intent.getExtras();
            TextView textView=(TextView)findViewById(R.id.tv_service_name);
            textView.setText(serviceInfo.getString("serviceName"));
            textView=(TextView)findViewById(R.id.tv_date);
            textView.setText(serviceInfo.getString("serviceDate"));
            textView=(TextView) findViewById(R.id.tv_spare);
            textView.setText(serviceInfo.getString("serviceSparePart"));
            textView=(TextView) findViewById(R.id.tv_info);
            textView.setText(serviceInfo.getString("serviceInfo"));
            Toast.makeText(this,"Data Received",Toast.LENGTH_SHORT).show();
        }
    }

    public void backToMenu(View view){
        finish();
    }
  /*  @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_service_detail, menu);
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
    }*/
}
