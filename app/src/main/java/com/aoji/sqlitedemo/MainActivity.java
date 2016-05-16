package com.aoji.sqlitedemo;

import android.app.AlertDialog;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    DatabaseHelper myDb;
    EditText editTextMake, editTextModel, editTextYear,editTextId;
    Button btnAdd;
    Button btnShowData;
    Button btnUpdateData;
    Button btnDeleteData;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myDb = new DatabaseHelper(this);

        editTextMake = (EditText) findViewById(R.id.editTextMake);
        editTextModel = (EditText) findViewById(R.id.editTextModel);
        editTextYear = (EditText) findViewById(R.id.editTextYear);
        editTextId = (EditText) findViewById(R.id.editTextId);
        btnAdd = (Button) findViewById(R.id.btnAdd);
        btnShowData = (Button) findViewById(R.id.btnShow);
        btnUpdateData = (Button) findViewById(R.id.btnUpdate);
        btnDeleteData = (Button) findViewById(R.id.btnDelete);

        AddData();
        showData();
        UpdateData();
        DeleteData();

    }

    public void DeleteData() {
        btnDeleteData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Integer deletedRecord = myDb.DeleteData(editTextId.getText().toString());
                if (deletedRecord > 0) {
                    Toast.makeText(MainActivity.this, "Record Deleted", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(MainActivity.this, "Record Not Deleted", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void AddData(){
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               boolean isInserted = myDb.insertData(editTextMake.getText().toString(),
                        editTextModel.getText().toString(),
                        editTextYear.getText().toString() );
                if (isInserted == true)
                    Toast.makeText(MainActivity.this, "Data Inserted", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(MainActivity.this, "Data was not Inserted", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void UpdateData() {
        btnUpdateData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isUpdated = myDb.updateData(editTextId.getText().toString(),
                        editTextMake.getText().toString(), editTextModel.getText().toString(),
                        editTextYear.getText().toString());
                if(isUpdated == true) {
                    Toast.makeText(MainActivity.this, "Record Updated", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(MainActivity.this, "Record Not Updated", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void showData() {
        btnShowData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor res = myDb.getAllData();
                if(res.getCount() == 0) {
                    showMessage("Error", "No Data Found");
                    return;
                }

                StringBuffer buffer = new StringBuffer();
                while (res.moveToNext()) {
                    buffer.append("Id :" + res.getString(0)+"\n");
                    buffer.append("Make :" + res.getString(1)+"\n");
                    buffer.append("Model :" + res.getString(2)+"\n");
                    buffer.append("Year :" + res.getString(3)+"\n\n");
                }

                showMessage("List of Cars", buffer.toString());
            }
        });
    }

    public void showMessage(String title, String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();

    }

}
