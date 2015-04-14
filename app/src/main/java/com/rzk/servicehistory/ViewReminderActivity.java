package com.rzk.servicehistory;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.rzk.servicehistory.database.ServiceDataSource;
import com.rzk.servicehistory.database.ServiceReminder;

import java.sql.SQLException;


public class ViewReminderActivity extends ActionBarActivity {
    private ServiceReminder serviceReminder;
    private ServiceDataSource dataSource;
    private String vehicleName;
    private TextView textViewVehicleId,textViewVehicleName,textViewDate,textViewDetail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_reminder);
        Intent intent=getIntent();
        if(intent!=null){
            Bundle bundle=intent.getExtras();
            serviceReminder=new ServiceReminder();
            serviceReminder.setVehicleId(bundle.getString("vehicleId"));
            serviceReminder.setDate(bundle.getString("reminderDate"));
            serviceReminder.setDetail(bundle.getString("reminderDetail"));
            serviceReminder.setStatus(bundle.getString("reminderStatus"));

            dataSource=new ServiceDataSource(this);
            try {
                dataSource.open();
                vehicleName=dataSource.getVehicleName(serviceReminder.getVehicleId());
            } catch (SQLException e) {
                e.printStackTrace();
            }
            dataSource.close();

            textViewVehicleId=(TextView) findViewById(R.id.text_view_vehicle_id);
            textViewVehicleName=(TextView) findViewById(R.id.text_view_vehicle_name);
            textViewDate=(TextView) findViewById(R.id.text_view_reminder_date);
            textViewDetail=(TextView) findViewById(R.id.text_view_detail);

            textViewVehicleId.setText(serviceReminder.getVehicleId());
            textViewVehicleName.setText(vehicleName);
            textViewDate.setText(serviceReminder.getDate());
            textViewDetail.setText(serviceReminder.getDetail());
        }

    }

    public void deleteReminder(View view){
        try {
            dataSource.open();
            dataSource.deleteServiceReminder(serviceReminder);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        dataSource.close();
        finish();
    }

  /*  @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_view_reminder, menu);
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
