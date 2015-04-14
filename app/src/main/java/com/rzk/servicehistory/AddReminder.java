package com.rzk.servicehistory;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.rzk.servicehistory.database.ServiceDataSource;
import com.rzk.servicehistory.database.ServiceReminder;
import com.rzk.servicehistory.database.VehicleData;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;


public class AddReminder extends ActionBarActivity {
    private EditText editTextDate,editTextDetail,editTextVehicleId;
    private VehicleData vehicleData;
    Bundle bundle;
    ServiceDataSource dataSource;
    private SimpleDateFormat dateFormatter;
    private DatePickerDialog dateDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_reminder);
        Intent intent=getIntent();
        if(intent!=null){
            bundle=intent.getExtras();
            vehicleData=new VehicleData();
            vehicleData.setVehicleId(bundle.getString("vehicleId"));
            vehicleData.setVehicleName(bundle.getString("vehicleName"));
            vehicleData.setVehicleData((bundle.getString("vehicleData")));
            vehicleData.setVehicleLastServiceDate(bundle.getString("vehicleLastServiceData"));
        }
        dataSource=new ServiceDataSource(this);
        editTextDate=(EditText) findViewById(R.id.edit_text_reminder_date);
        editTextDetail=(EditText) findViewById(R.id.editText_reminder_detail);
        editTextVehicleId=(EditText) findViewById(R.id.editText_vehicle_id);
        editTextVehicleId.setText(vehicleData.getVehicleId());
    }
    public void setReminderDate(View v ){
        dateFormatter=new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        Calendar newCalendar = Calendar.getInstance();
        dateDialog=new DatePickerDialog(this,new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                editTextDate.setText(dateFormatter.format(newDate.getTime()));
            }
        }, newCalendar.get(Calendar.YEAR),newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH)

        );
        dateDialog.getDatePicker().setMinDate(System.currentTimeMillis()-1000);
        dateDialog.show();

    }

    public void addServiceReminder(View view){
        if(editTextDetail.getText().toString().length()>0&&editTextDate.getText().toString().length()>0){
            ServiceReminder serviceReminder=new ServiceReminder();
            serviceReminder.setVehicleId(vehicleData.getVehicleId());
            serviceReminder.setDate(editTextDate.getText().toString());
            serviceReminder.setDetail(editTextDetail.getText().toString());
            serviceReminder.setStatus("not");

            try {
                dataSource.open();
                dataSource.createServiceReminder(serviceReminder);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            dataSource.close();
            Toast.makeText(this,"Reminder Added",Toast.LENGTH_SHORT).show();
            finish();
        }
        else{
            Toast.makeText(this,"Date And Detail cannot empty",Toast.LENGTH_SHORT).show();
        }
    }

    public void cancelReminder(View view){
        finish();
    }

    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add_reminder, menu);
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
