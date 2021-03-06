package com.rzk.servicehistory;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.rzk.servicehistory.database.ServiceDataSource;
import com.rzk.servicehistory.database.VehicleData;

import java.sql.SQLException;


public class AddVehicleActivity extends ActionBarActivity {
    private EditText editTextVehicleID, editTextVehicleName,
            editTextVehicleInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_vehicle);
        editTextVehicleID = (EditText) findViewById(R.id.edit_text_vehicle_id);
        editTextVehicleID.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                editTextChecker(editTextVehicleID);
            }
        });
        editTextVehicleName = (EditText) findViewById(R.id.edit_text_vehicle_name);
        editTextVehicleName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                editTextChecker(editTextVehicleName);
            }
        });
        editTextVehicleInfo = (EditText) findViewById(R.id.edit_text_vehicle_info);
    }

    public void saveVehicleData(View v) {
        if (editTextVehicleID.getText().toString().trim().length() < 5 &&
                editTextVehicleName.getText().toString().trim().length() < 3) {
            Toast.makeText(this, "Complete Vehicle ID and Vehicle Name", Toast.LENGTH_SHORT);
        } else {
            //int savedata=0;
            ServiceDataSource dataSource = new ServiceDataSource(this);
            VehicleData vehicleData = new VehicleData();
            vehicleData.setVehicleId(editTextVehicleID.getText().toString());
            vehicleData.setVehicleName(editTextVehicleName.getText().toString());
            vehicleData.setVehicleData(editTextVehicleInfo.getText().toString());
            boolean data = true;
            try {
                dataSource.open();
                data = dataSource.checkVehicleId(vehicleData.getVehicleId());
                if (data) {
                    dataSource.close();
                    Toast.makeText(this, "Vehicle Id already exist", Toast.LENGTH_SHORT).show();
                    editTextVehicleID.setText("");
                    editTextVehicleID.setFocusable(true);
                } else
                    dataSource.createVehicleData(vehicleData);
                //dataSource.close();
                //savedata=1;
            } catch (Exception e) {
                // e.printStackTrace();
                //savedata=0;

            }
            dataSource.close();
            //if(savedata==1){
            if (!data)
                finish();
            // }


        }

    }

    public boolean editTextChecker(EditText editText){
        String text=editText.getText().toString().trim();
        editText.setError(null);
        if(text.length()==0){
            editText.setError(Html.fromHtml("<font color='red'>Cannot Empty "));
            return false;
        }
        return true;
    }


    public void resetVehicleData(View v) {
        EditText editText;
        editText = (EditText) findViewById(R.id.edit_text_vehicle_id);
        editText.setText("");
        editText = (EditText) findViewById(R.id.edit_text_vehicle_name);
        editText.setText("");
        editText = (EditText) findViewById(R.id.edit_text_vehicle_info);
        editText.setText("");
    }

}
