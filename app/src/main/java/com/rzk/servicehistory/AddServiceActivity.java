package com.rzk.servicehistory;

import android.app.DatePickerDialog;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.rzk.servicehistory.database.ServiceData;
import com.rzk.servicehistory.database.ServiceDataSource;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;


public class AddServiceActivity extends ActionBarActivity {
    private SimpleDateFormat dateFormatter;
    private DatePickerDialog dateDialog;
    private EditText editTextDate;
    private ServiceDataSource dataSource;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_service_layout);

        dataSource=new ServiceDataSource(this);

        editTextDate=(EditText) findViewById(R.id.edit_text_date);
        editTextDate.setInputType(InputType.TYPE_NULL);
        editTextDate.requestFocus();
        editTextDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setDate(v);
            }
        });
    }

    public void saveData(View view) throws SQLException {

        EditText editTextService=(EditText) findViewById(R.id.editText);
        if(editTextService.getText().toString().trim().length()<2){
            Toast.makeText(this,"Service Name Cannot Empty",Toast.LENGTH_SHORT).show();
        }
        else {
            dataSource.open();
            TextView textViewServiceName,textViewDate,textViewSparepart,textViewInfo;
            textViewServiceName=(TextView) findViewById(R.id.editText);
            textViewDate=(TextView) findViewById(R.id.edit_text_date);
            textViewSparepart=(TextView)findViewById(R.id.editText_sparepart);
            textViewInfo=(TextView)findViewById(R.id.editText_detail_service);

            ServiceData serviceData=new ServiceData();
            serviceData.setServiceName(textViewServiceName.getText().toString());
            serviceData.setServiceDate(textViewDate.getText().toString());
            serviceData.setServiceSparePart(textViewSparepart.getText().toString());
            serviceData.setServiceInfo(textViewInfo.getText().toString());

            dataSource.createServiceData(serviceData);
            dataSource.close();

            Toast.makeText(this,"Recent Service Data Saved",Toast.LENGTH_SHORT).show();
            AddServiceActivity.this.finish();
        }

    }

    public void resetData(View view){
        resetText();

    }

    private void resetText(){
        TextView textView=(TextView) findViewById(R.id.editText_detail_service);
        textView.setText("");
        textView=(TextView) findViewById(R.id.edit_text_date);
        textView.setHint("Service Date");
        textView=(TextView) findViewById(R.id.editText_sparepart);
        textView.setText("");
        textView=(TextView) findViewById(R.id.editText);
        textView.setText("");


    }

    public void setDate(View v){

        dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);

       // final EditText editTextDate=(EditText) findViewById(R.id.edit_text_date);
        Calendar newCalendar = Calendar.getInstance();
        dateDialog=new DatePickerDialog(this,new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                editTextDate.setText(dateFormatter.format(newDate.getTime()));
            }
            }, newCalendar.get(Calendar.YEAR),newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH)

        );
        dateDialog.show();
        /*
        Calendar mcurrentDate=Calendar.getInstance();
        mYear=mcurrentDate.get(Calendar.YEAR);
        mMonth=mcurrentDate.get(Calendar.MONTH);
        mDay=mcurrentDate.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog mDatePicker=new DatePickerDialog(AddServiceActivity.this, new DatePickerDialog.OnDateSetListener() {
            public void onDateSet(DatePicker datepicker, int selectedyear, int selectedmonth, int selectedday) {
                // TODO Auto-generated method stub
                    /*      Your code   to get date and time
            }
        },mYear, mMonth, mDay);
        mDatePicker.setTitle("Select date");
        mDatePicker.show();*/
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add, menu);
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
