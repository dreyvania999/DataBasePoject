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
        editLogin = findViewById(R.id.editLogin);
        editPassword = findViewById(R.id.editPassword);
        ButtonLogin = findViewById(R.id.ButtonLogin);
        ButtonAdd = findViewById(R.id.ButtonAdd);
        ButtonAdd.setOnClickListener(this);
        ButtonLogin.setOnClickListener(this);
        DBHelper = new DBHelper(this);
        DB = DBHelper.getWritableDatabase();

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ButtonLogin:
                Cursor cursorLog = DB.query(DBHelper.Registration, null, null, null, null, null, null);
                boolean zahod = false;
                if (cursorLog.moveToFirst()) {
                    int passwordIndex = cursorLog.getColumnIndex(DBHelper.Password);
                    int loginIndex = cursorLog.getColumnIndex(DBHelper.Login);
                    int adminIndex = cursorLog.getColumnIndex(DBHelper.Password);
                    int l = adminIndex;
                    do {
                        if (editPassword.getText().toString().equals(cursorLog.getString(passwordIndex)) && editLogin.getText().toString().equals(cursorLog.getString(loginIndex))) {
                            if (editPassword.getText().toString().equals("admin") && editLogin.getText().toString().equals("admin")) {
                                Intent intent = new Intent(this, MainActivity.class);
                                startActivity(intent);
                            } else {
                                Intent intent1 = new Intent(this, Users.class);
                                startActivity(intent1);
                            }
                            zahod = true;
                            break;
                        }

                    } while (cursorLog.moveToNext());
                    if (!zahod)
                        Toast.makeText(this, "Комбинация логина и пароля не была найдена", Toast.LENGTH_LONG).show();

                }

                cursorLog.close();
                break;
            case R.id.ButtonAdd:
                Cursor cursorAdd = DB.query(DBHelper.Registration, null, null, null, null, null, null);
                boolean finded = false;
                if (cursorAdd.moveToFirst()) {
                    int loginIndex = cursorAdd.getColumnIndex(DBHelper.Login);
                    do {
                        if (editLogin.getText().toString().equals(cursorAdd.getString(loginIndex))) {
                            Toast.makeText(this, "Введённый логин уже существует", Toast.LENGTH_LONG).show();
                            finded = true;
                            break;
                        }
                    } while (cursorAdd.moveToNext());
                }
                if (!finded) {
                    ContentValues contentValues = new ContentValues();
                    contentValues.put(DBHelper.Login, editLogin.getText().toString());
                    contentValues.put(DBHelper.Password, editPassword.getText().toString());
                    DB.insert(DBHelper.Registration, null, contentValues);
                    Toast.makeText(this, "Вы успешно зарегистрировались", Toast.LENGTH_LONG).show();
                }
                cursorAdd.close();
                break;
        }
    }
}