package com.rzk.servicehistory;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.rzk.servicehistory.database.ServiceDataSource;
import com.rzk.servicehistory.database.VehicleData;

import java.sql.SQLException;


public class AddVehicleActivity extends ActionBarActivity {
    private EditText editTextVehicleID,editTextVehicleName,
            editTextVehicleInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_vehicle);
        editTextVehicleID=(EditText) findViewById(R.id.edit_text_vehicle_id);
        editTextVehicleName=(EditText)findViewById(R.id.edit_text_vehicle_name);
        editTextVehicleInfo=(EditText)findViewById(R.id.edit_text_vehicle_info);
    }

    public void saveVehicleData(View v){
        if(editTextVehicleID.getText().toString().trim().length()<5&&
                editTextVehicleName.getText().toString().trim().length()<3){
            Toast.makeText(this,"Complete Vehicle ID and Vehicle Name",Toast.LENGTH_SHORT);
        }
        else{
            ServiceDataSource dataSource=new ServiceDataSource(this);
            VehicleData vehicleData=new VehicleData();
            vehicleData.setVehicleId(editTextVehicleID.getText().toString());
            vehicleData.setVehicleName(editTextVehicleName.getText().toString());
            vehicleData.setVehicleData(editTextVehicleInfo.getText().toString());
            try {
                dataSource.open();
                dataSource.createVehicleData(vehicleData);
                dataSource.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            finish();
        }

    }

    public void resetVehicleData(View v){
        EditText editText;
        editText=(EditText)findViewById(R.id.edit_text_vehicle_id);
        editText.setText("");
        editText=(EditText)findViewById(R.id.edit_text_vehicle_name);
        editText.setText("");
        editText=(EditText)findViewById(R.id.edit_text_vehicle_info);
        editText.setText("");
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add_vehicle, menu);
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
