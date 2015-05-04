package com.rzk.servicehistory;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.rzk.servicehistory.database.ServiceDataSource;
import com.rzk.servicehistory.database.ServiceReminder;

import java.sql.SQLException;
import java.util.List;


public class ViewAllReminder extends ActionBarActivity {
    private ListView listViewReminder;
    private ServiceDataSource dataSource;

    private List<ServiceReminder> serviceReminders;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_all_reminder);

        listViewReminder = (ListView) findViewById(R.id.list_all_reminder);
        dataSource = new ServiceDataSource(this);

    }

    @Override
    protected void onResume() {
        super.onResume();
        try {
            dataSource.open();
            serviceReminders = dataSource.getAllServiceReminder();
            final ArrayAdapter<ServiceReminder> adapter = new ArrayAdapter<ServiceReminder>(this,
                    android.R.layout.simple_list_item_1, serviceReminders);
            listViewReminder.setAdapter(adapter);
            // Toast.makeText(this,serviceReminders.get(0).getVehicleId(),Toast.LENGTH_SHORT).show();
            listViewReminder.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    ServiceReminder serviceReminder = serviceReminders.get(position);
                    showServiceReminderDetail(serviceReminder);
                }
            });
        } catch (SQLException e) {
            e.printStackTrace();
        }
        dataSource.close();

    }

    public void showServiceReminderDetail(ServiceReminder serviceReminder) {
        Intent intent = new Intent(this, ViewReminderActivity.class);
        intent.putExtras(createServiceReminderBundle(serviceReminder));
        startActivity(intent);
    }

    public Bundle createServiceReminderBundle(ServiceReminder serviceReminder) {
        Bundle reminderDetail = new Bundle();

        reminderDetail.putString("vehicleId", serviceReminder.getVehicleId());
        reminderDetail.putString("reminderDate", serviceReminder.getDate());
        reminderDetail.putString("reminderDetail", serviceReminder.getDetail());
        reminderDetail.putString("reminderStatus", serviceReminder.getStatus());

        return reminderDetail;
    }
/*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_view_all_reminder, menu);
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
