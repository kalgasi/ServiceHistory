package com.rzk.servicehistory;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.rzk.servicehistory.database.ServiceDataSource;
import com.rzk.servicehistory.database.VehicleData;

import java.sql.SQLException;
import java.util.List;


public class RootMenuActivity extends ActionBarActivity {
    private ListView listViewVehicle;
    List<VehicleData> vehicleDatas;
    private ServiceDataSource dataSource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_root_menu);
        /*
        Check If Vehicle data already exist
        if vehicle data exist, show history menu
        else show add vehicle menu
         */
        listViewVehicle=(ListView) findViewById(R.id.list_vehicle);
        dataSource=new ServiceDataSource(this);
        //listViewVehicle=(ListView) findViewById(R.id.list_vehicle);
        if(checkDatabase()){

            listViewVehicle.setVisibility(View.VISIBLE);
        }
        else{
            listViewVehicle.setVisibility(View.GONE);
        }
    }

    private boolean checkDatabase(){
        try {
            dataSource.open();
            vehicleDatas=dataSource.getAllvehicleData();
            ArrayAdapter<VehicleData> arrayAdapter=new ArrayAdapter<VehicleData>(this,
                    android.R.layout.simple_list_item_1,vehicleDatas);
            listViewVehicle.setAdapter(arrayAdapter);
        } catch (SQLException e) {
            return false;
        }
        dataSource.close();
        return true;
    }

    public void addVehicle(View view){
        Intent intent=new Intent(this,AddVehicleActivity.class);
        startActivity(intent);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_root_menu, menu);
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
