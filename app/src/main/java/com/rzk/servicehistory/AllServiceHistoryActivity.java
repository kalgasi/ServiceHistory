package com.rzk.servicehistory;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.rzk.servicehistory.database.ServiceData;
import com.rzk.servicehistory.database.ServiceDataSource;

import java.sql.SQLException;
import java.util.List;


public class AllServiceHistoryActivity extends ActionBarActivity {
    private ServiceDataSource dataSource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.all_service_history_layout);
        dataSource=new ServiceDataSource(this);
        ListView listView=(ListView)findViewById(R.id.list_all_history);

        try {
            dataSource.open();
            List<ServiceData> dataService=dataSource.getAllServiceHistory();

            //listView.set;
           ArrayAdapter<ServiceData> adapter=new ArrayAdapter<ServiceData>(this, android.R.layout.simple_list_item_1,dataService);
            listView.setAdapter(adapter);
        } catch (SQLException e) {
            Toast.makeText(this, "No Data Service", Toast.LENGTH_SHORT).show();
        }
        dataSource.close();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_all_service_history, menu);
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
