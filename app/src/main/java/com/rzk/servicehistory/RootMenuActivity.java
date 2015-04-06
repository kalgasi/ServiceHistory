package com.rzk.servicehistory;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

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
            listViewVehicle.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    VehicleData vehicleData=vehicleDatas.get(position);
                    showVehicleHistoryMenu(vehicleData);
                   // showData(vehicleData);

                }
            });
            listViewVehicle.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                    try {
                        dataSource.open();
                        dataSource.deleteVehicleData(vehicleDatas.get(position));
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    dataSource.close();
                    return true;
                }
            });
        }
        else{
            listViewVehicle.setVisibility(View.GONE);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (checkDatabase()){
            listViewVehicle.setVisibility(View.VISIBLE);
            listViewVehicle.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    VehicleData vehicleData=vehicleDatas.get(position);
                    //showData(vehicleData);
                    showVehicleHistoryMenu(vehicleData);

                }
            });
        }
    }
    public void showData(VehicleData data){
        Toast.makeText(this,data.getVehicleId(),Toast.LENGTH_SHORT).show();
    }
    public void showVehicleHistoryMenu(VehicleData vehicleData){
        Intent intent=new Intent(this, MenuActivity.class);
        intent.putExtras(createVehicleData(vehicleData));
        startActivity(intent);

    }

    public Bundle createVehicleData(VehicleData vehicleData){
        Bundle vehicleDataBundle=new Bundle();
        vehicleDataBundle.putString("vehicleId",vehicleData.getVehicleId());
        vehicleDataBundle.putString("vehicleName",vehicleData.getVehicleName());
        vehicleDataBundle.putString("vehicleData",vehicleData.getVehicleData());
        if(vehicleData.getVehicleLastServiceDate()==null)
        vehicleDataBundle.putString("vehicleLastServiceData","");
        else vehicleDataBundle.putString("vehicleLastServiceData",vehicleData.getVehicleLastServiceDate());
        return vehicleDataBundle;
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
