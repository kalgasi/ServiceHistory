package com.rzk.servicehistory;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.rzk.servicehistory.database.VehicleData;


public class MenuActivity extends ActionBarActivity {
private VehicleData vehicleData;
private Bundle vehicleBundle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_layout);
        Intent intent=getIntent();
        vehicleData=new VehicleData();
        if(intent!=null){
            vehicleBundle=intent.getExtras();
            vehicleData.setVehicleId(vehicleBundle.getString("vehicleId"));
            vehicleData.setVehicleName(vehicleBundle.getString("vehicleName"));
            vehicleData.setVehicleData(vehicleBundle.getString("vehicleData"));
            vehicleData.setVehicleLastServiceDate(vehicleBundle.getString("vehicleLastServiceData"));
        }
    }

    public void addServiceHistory(View v){
        Intent intent=new Intent(this,AddServiceActivity.class);
        intent.putExtras(vehicleBundle);
        startActivity(intent);
    }

    public void viewAllServiceHistory(View v){
        Intent intent=new Intent(this,AllServiceHistoryActivity.class);
        intent.putExtras(vehicleBundle);
        startActivity(intent);

    }

    public void viewLastServiceHistory(View v){
        Intent intent=new Intent(this,ShowService.class);
        intent.putExtras(vehicleBundle);
        startActivity(intent);
    }

    public void createServiceReminder(View v){
        Intent intent=new Intent(this,AddReminder.class);
        intent.putExtras(vehicleBundle);
        startActivity(intent);
    }

    /*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_menu, menu);
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
