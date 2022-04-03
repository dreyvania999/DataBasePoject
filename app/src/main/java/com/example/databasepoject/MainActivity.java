package com.example.databasepoject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    Button btnAdd,btnClear;
    EditText editName,editPhone,editAddress,editSurname;
    DBHelper DBHelper;
    SQLiteDatabase DB;
    ContentValues  contentValues;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editPhone =findViewById(R.id.editPhone);
        editAddress =findViewById(R.id.editAddress);
        editSurname =findViewById(R.id.editSurname);
        editName =findViewById(R.id.editName);
        btnClear = findViewById(R.id.btnClear);
        btnAdd = findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(this);
        btnClear.setOnClickListener(this);
        DBHelper = new DBHelper(this);
        DB=DBHelper.getWritableDatabase();
        UpdateDB();

    }
    public  void UpdateDB(){
        Cursor cursor = DB.query(DBHelper.Сontacts, null,null,null,null,null,null);
        if (cursor.moveToFirst()) {
            int idIndex = cursor.getColumnIndex(DBHelper.KEY_ID);
            int nameIndex = cursor.getColumnIndex(DBHelper.Name);
            int phoneIndex = cursor.getColumnIndex(DBHelper.Phone);
            int surnameIndex = cursor.getColumnIndex(DBHelper.Surname);
            int addressIndex = cursor.getColumnIndex(DBHelper.Address);
            TableLayout DBoutput = findViewById(R.id.DBoutput);
            DBoutput.removeAllViews();
            do {
                TableRow DBoutputROW = new TableRow(this);
                DBoutputROW.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT));
                TableRow.LayoutParams params = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,TableRow.LayoutParams.WRAP_CONTENT);

                TextView outputID= new TextView(this);
                params.weight = 1.0f;
                outputID.setLayoutParams(params);
                outputID.setText(cursor.getString(idIndex));
                DBoutputROW.addView(outputID);

                TextView outputName= new TextView(this);
                params.weight = 2.0f;
                outputName.setLayoutParams(params);
                outputName.setText(cursor.getString(nameIndex));
                DBoutputROW.addView(outputName);

                TextView outputSurname= new TextView(this);
                params.weight = 2.0f;
                outputSurname.setLayoutParams(params);
                outputSurname.setText(cursor.getString(surnameIndex));
                DBoutputROW.addView(outputSurname);

                TextView outputPhone= new TextView(this);
                params.weight = 2.0f;
                outputPhone.setLayoutParams(params);
                outputPhone.setText(cursor.getString(phoneIndex));
                DBoutputROW.addView(outputPhone);

                TextView outputAddress= new TextView(this);
                params.weight = 2.0f;
                outputAddress.setLayoutParams(params);
                outputAddress.setText(cursor.getString(addressIndex));
                DBoutputROW.addView(outputAddress);

                Button deleteButton = new Button(this);
                deleteButton.setOnClickListener(this);

                deleteButton.setText("X");
                deleteButton.setId(cursor.getInt(idIndex));
                DBoutputROW.addView(deleteButton);
                DBoutput.addView(DBoutputROW);

            } while (cursor.moveToNext());
        }

        cursor.close();
    }

    @Override
    public void onClick(View view) {




        switch (view.getId()){

            case R.id.btnAdd:
                String name =editName.getText().toString();
                String phone =editPhone.getText().toString();
                String surname =editSurname.getText().toString();
                String address =editAddress.getText().toString();
                contentValues = new ContentValues();
                contentValues.put(DBHelper.Name,name);
                contentValues.put(DBHelper.Phone,phone);
                contentValues.put(DBHelper.Surname,surname);
                contentValues.put(DBHelper.Address,address);
                DB.insert(DBHelper.Сontacts,null,contentValues);
                UpdateDB();
                editAddress.setText("");
                editPhone.setText("");
                editSurname.setText("");
                editName.setText("");
                break;

            case R.id.btnClear:
                DB.delete(DBHelper.Сontacts,null,null);
                TableLayout DBoutput = findViewById(R.id.DBoutput);
                DBoutput.removeAllViews();
                UpdateDB();
                break;
            default:
                View outputROW =  (View) view.getParent();
                ViewGroup outputDB = (ViewGroup) outputROW.getParent();
                outputDB.removeView(outputROW);
                outputDB.invalidate();


                DB.delete(DBHelper.Сontacts,DBHelper.KEY_ID + " = ?",new String[]{String.valueOf(view.getId())});
                Cursor cursorUpdater = DB.query(DBHelper.Сontacts, null, null, null, null, null, null);
                  contentValues = new ContentValues();
                if (cursorUpdater.moveToFirst()) {
                    int idIndex = cursorUpdater.getColumnIndex(DBHelper.KEY_ID);
                    int nameIndex = cursorUpdater.getColumnIndex(DBHelper.Name);
                    int phoneIndex = cursorUpdater.getColumnIndex(DBHelper.Phone);
                    int surnameIndex = cursorUpdater.getColumnIndex(DBHelper.Surname);
                    int addressIndex = cursorUpdater.getColumnIndex(DBHelper.Address);
                    int realID=1;

                    do {
                if(cursorUpdater.getInt(idIndex)>realID){
                contentValues.put(DBHelper.KEY_ID,realID);
                    contentValues.put(DBHelper.Name,cursorUpdater.getString(nameIndex));
                    contentValues.put(DBHelper.Phone,cursorUpdater.getString(phoneIndex));
                    contentValues.put(DBHelper.Surname,cursorUpdater.getString(surnameIndex));
                    contentValues.put(DBHelper.Address,cursorUpdater.getString(addressIndex));
                    DB.replace(DBHelper.Сontacts,null,contentValues);
                }realID++;

                    }while (cursorUpdater.moveToNext());

                    if(cursorUpdater.moveToLast()){
                        DB.delete(DBHelper.Сontacts,DBHelper.KEY_ID + " = ?",new String[]{cursorUpdater.getString(idIndex)});
                    }
                }
                UpdateDB();
                break;

        }
    }
}