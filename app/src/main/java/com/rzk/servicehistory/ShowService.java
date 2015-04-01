package com.rzk.servicehistory;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.rzk.servicehistory.database.ServiceData;
import com.rzk.servicehistory.database.ServiceDataSource;
import com.rzk.servicehistory.database.VehicleData;

import java.sql.SQLException;


public class ShowService extends ActionBarActivity {
    private ServiceDataSource dataSource;
    private ServiceData serviceData=null;
    private VehicleData vehicleData;
    private Bundle bundle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_service);
        dataSource=new ServiceDataSource(this);
        Intent intent=getIntent();
        vehicleData=new VehicleData();
        if(intent!=null){
            bundle=intent.getExtras();
            vehicleData.setVehicleId(bundle.getString("vehicleId"));
            vehicleData.setVehicleName(bundle.getString("vehicleName"));
            vehicleData.setVehicleData(bundle.getString("vehicleData"));
            vehicleData.setVehicleLastServiceDate(bundle.getString("vehicleLastServiceData"));
        }
        try {
            //Toast.makeText(this,vehicleData.getVehicleId(),Toast.LENGTH_SHORT).show();
            dataSource.open();
            serviceData=dataSource.getLastServiceData(vehicleData);
            dataSource.close();

        } catch (SQLException e) {
            Toast.makeText(this,"No Data Service",Toast.LENGTH_SHORT).show();
        }

        if(serviceData.getServiceName()!=null){
            TextView textViewServiceName=(TextView)findViewById(R.id.tv_service_name);
            textViewServiceName.setText(serviceData.getServiceName());
            TextView textViewDate=(TextView) findViewById(R.id.tv_date);
            textViewDate.setText(serviceData.getServiceDate());
            TextView textViewSpare=(TextView) findViewById(R.id.tv_spare);
            textViewSpare.setText(serviceData.getServiceSparePart());
            TextView tvInfo=(TextView)findViewById(R.id.tv_info);
            tvInfo.setText(serviceData.getServiceInfo());
            //Toast.makeText(this,serviceData.getVehicleId(),Toast.LENGTH_SHORT).show();

        }
        else{
            Toast.makeText(this,"No Data Service",Toast.LENGTH_SHORT).show();
        }
    }

    public void backToMenu(View view){
        ShowService.this.finish();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_show, menu);
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
