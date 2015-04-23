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
import android.widget.TextView;
import android.widget.Toast;

import com.rzk.servicehistory.database.ServiceData;
import com.rzk.servicehistory.database.ServiceDataSource;
import com.rzk.servicehistory.database.VehicleData;

import java.sql.SQLException;
import java.util.List;


public class MenuActivity extends ActionBarActivity {
private VehicleData vehicleData;
private Bundle vehicleBundle;
private TextView textViewId,textViewName;
private ServiceDataSource dataSource;
List<ServiceData> dataService;
ListView listView;


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
            textViewId=(TextView) findViewById(R.id.text_vehicle_id);
            textViewId.setText(vehicleData.getVehicleId());
            textViewName=(TextView)findViewById(R.id.text_vehicle_name);
            textViewName.setText(vehicleData.getVehicleName());
            listView=(ListView)findViewById(R.id.listAllHistory);

        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        dataSource=new ServiceDataSource(this);
        try {
            dataSource.open();
            dataService=dataSource.getAllServiceHistory(vehicleData);
            //Toast.makeText(this,dataService.size(),Toast.LENGTH_SHORT).show();
            //listView.set;
            final ArrayAdapter<ServiceData> adapter=new ArrayAdapter<ServiceData>(this, android.R.layout.simple_list_item_1,dataService);
            listView.setAdapter(adapter);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    ServiceData serviceData=dataService.get(position);
                    String text=serviceData.getServiceInfo()+" "+serviceData.getServiceSparePart();
                    //showMessage(text);
                    showServiceDetail(serviceData);
                }
            });
            listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                    deleteData(position);
                    showMessage(dataService.get(position).getServiceName()+" data is deleted");
                    dataService.remove(position);
                    adapter.notifyDataSetChanged();

                    return true;
                }
            });
        } catch (SQLException e) {
            Toast.makeText(this, "No Data Service", Toast.LENGTH_SHORT).show();
        }
        dataSource.close();

    }

    public void showServiceDetail(ServiceData data){
        Intent intent=new Intent(this,ServiceDetailActivity.class);

        intent.putExtras(createServiceDataBundle(data));
        startActivity(intent);
    }

    public Bundle createServiceDataBundle(ServiceData data){
        Bundle serviceInfo=new Bundle();
        serviceInfo.putString("serviceName",data.getServiceName());
        serviceInfo.putString("serviceDate",data.getServiceDate());
        serviceInfo.putString("serviceSparePart", data.getServiceSparePart());
        serviceInfo.putString("serviceInfo", data.getServiceInfo());

        return serviceInfo;
    }

    public void showMessage(String text){
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }

    public void deleteData(int position){
//        Toast.makeText(this,position,Toast.LENGTH_SHORT).show();
        try {
            dataSource.open();
            dataSource.deleteDataService(dataService.get(position));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        dataSource.close();
        //listView.remove
    }

    public void addServiceHistory(){
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

    public void createServiceReminder(){
        Intent intent=new Intent(this,AddReminder.class);
        intent.putExtras(vehicleBundle);
        startActivity(intent);
    }


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
        switch (id){
            case R.id.action_add_reminder:
                createServiceReminder();
                return true;
            case R.id.action_add_service:
                addServiceHistory();
                return true;
            default: return super.onOptionsItemSelected(item);

        }



    }
}
