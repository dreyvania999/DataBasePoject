package com.example.databasepoject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Activation extends AppCompatActivity implements View.OnClickListener {

    Button ButtonLogin, ButtonAdd;
    EditText editLogin, editPassword;
    DBHelper DBHelper;
    SQLiteDatabase DB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activation);
        editLogin = findViewById(R.id.editSurname);
        editPassword = findViewById(R.id.editName);
        ButtonLogin = findViewById(R.id.btnClear);
        ButtonAdd = findViewById(R.id.btnAdd);
        ButtonAdd.setOnClickListener(this);
        ButtonLogin.setOnClickListener(this);
        DBHelper = new DBHelper(this);
        DB = DBHelper.getWritableDatabase();

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ButtonLogin:
                Cursor cursor = DB.query(DBHelper.Registration, null, null, null, null, null, null);

                if (cursor.moveToFirst()) {
                    int passwordIndex = cursor.getColumnIndex(DBHelper.Password);
                    int loginIndex = cursor.getColumnIndex(DBHelper.Login);
                    do {
                        if (editPassword.getText().toString().equals(cursor.getString(passwordIndex)) && editLogin.getText().toString().equals(cursor.getString(loginIndex))) {
                            if (editPassword.getText().toString().equals(cursor.getString(1)) && editLogin.getText().toString().equals(cursor.getString(1))) {
                                Intent intent = new Intent(this, MainActivity.class);
                                startActivity(intent);
                            } else {
                                Intent intent = new Intent(this, Users.class);
                                startActivity(intent);
                            }
                            break;
                        }

                    } while (cursor.moveToNext());
                    Toast.makeText(this,"Комбинация логина и пароля не была найдена",Toast.LENGTH_LONG).show();
                    break;
                }
        }
    }
}