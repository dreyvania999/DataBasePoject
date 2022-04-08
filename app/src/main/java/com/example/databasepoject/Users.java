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

public class Users extends AppCompatActivity {


    DBHelper DBHelper;
    SQLiteDatabase DB;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users);

        DBHelper = new DBHelper(this);
        DB = DBHelper.getWritableDatabase();
        UpdateDB();

    }

    public void UpdateDB() {
        Cursor cursor = DB.query(DBHelper.Сontacts, null, null, null, null, null, null);
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
                DBoutputROW.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                TableRow.LayoutParams params = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT);

                TextView outputID = new TextView(this);
                params.weight = 1.0f;
                outputID.setLayoutParams(params);
                outputID.setText(cursor.getString(idIndex));
                DBoutputROW.addView(outputID);

                TextView outputName = new TextView(this);
                params.weight = 2.0f;
                outputName.setLayoutParams(params);
                outputName.setText(cursor.getString(nameIndex));
                DBoutputROW.addView(outputName);

                TextView outputSurname = new TextView(this);
                params.weight = 2.0f;
                outputSurname.setLayoutParams(params);
                outputSurname.setText(cursor.getString(surnameIndex));
                DBoutputROW.addView(outputSurname);

                TextView outputPhone = new TextView(this);
                params.weight = 2.0f;
                outputPhone.setLayoutParams(params);
                outputPhone.setText(cursor.getString(phoneIndex));
                DBoutputROW.addView(outputPhone);

                TextView outputAddress = new TextView(this);
                params.weight = 2.0f;
                outputAddress.setLayoutParams(params);
                outputAddress.setText(cursor.getString(addressIndex));
                DBoutputROW.addView(outputAddress);


            } while (cursor.moveToNext());
        }

        cursor.close();
    }

}
