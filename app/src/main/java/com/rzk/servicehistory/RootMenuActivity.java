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
import android.widget.TextView;
import android.widget.Toast;

import com.rzk.servicehistory.database.ServiceDataSource;
import com.rzk.servicehistory.database.VehicleData;

import java.sql.SQLException;
import java.util.List;


public class RootMenuActivity extends ActionBarActivity {
    private ListView listViewVehicle;
    List<VehicleData> vehicleDatas;
    private ServiceDataSource dataSource;
    ArrayAdapter<VehicleData> arrayAdapter;
    TextView textViewReminder;
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
        textViewReminder=(TextView)findViewById(R.id.textView_notif);
        dataSource=new ServiceDataSource(this);
        int count= checkReminder();
        //System.out.println(count +"**");
           showNotif(count);


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
                        vehicleDatas.remove(position);
                        arrayAdapter.notifyDataSetChanged();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    dataSource.close();
                    int count=checkReminder();
                    showNotif(count);
                    return true;
                }
            });
        }
       else{
            listViewVehicle.setVisibility(View.GONE);
        }
    }
    /*
    *check if theres any reminder
    *
     */
    public int checkReminder(){
        int result=0;
        try {
            dataSource.open();
            result=dataSource.getCountServiceReminder();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        dataSource.close();
        return result;

    }

    public void showNotif(int notifCount){
        if(notifCount>0){
            //Toast.makeText(this,notifCount,Toast.LENGTH_SHORT).show();
            textViewReminder.setText( notifCount+" service reminder");
            textViewReminder.setVisibility(View.VISIBLE);
        }
        else{
            textViewReminder.setVisibility(View.GONE);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        int count= checkReminder();
        //System.out.println(count +"**");
        showNotif(count);
        //textViewReminder.setVisibility(View.GONE);
        if (checkDatabase()){


        }
        else listViewVehicle.setVisibility(View.GONE);
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
        vehicleDataBundle.putString("vehicleId", vehicleData.getVehicleId());
        vehicleDataBundle.putString("vehicleName", vehicleData.getVehicleName());
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
            arrayAdapter=new ArrayAdapter<VehicleData>(this,
                    android.R.layout.simple_list_item_1,vehicleDatas);
            listViewVehicle.setAdapter(arrayAdapter);
        } catch (SQLException e) {
            return false;
        }
        dataSource.close();
        return true;
    }

    public void addVehicle(){
        Intent intent=new Intent(this,AddVehicleActivity.class);
        startActivity(intent);
    }

    public void showReminder(View v){
        Intent intent=new Intent(this,ViewAllReminder.class);
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
        if (id == R.id.action_add_vehicle) {
            addVehicle();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
