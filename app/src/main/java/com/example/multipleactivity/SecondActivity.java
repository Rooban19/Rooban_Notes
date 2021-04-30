package com.example.multipleactivity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.Toast;

import java.util.HashSet;

public class SecondActivity extends AppCompatActivity {
    int id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        EditText editText = findViewById(R.id.editText);
        Intent intent =  getIntent();
        id = intent.getIntExtra("noteId",-1);
        if (id != -1){
            editText.setText(MainActivity.friends.get(id));
        }else{
            MainActivity.friends.add("");
            id = MainActivity.friends.size() -1;
        }
            editText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    MainActivity.friends.set(id,String.valueOf(s));
                    MainActivity.arrayAdapter.notifyDataSetChanged();

                    SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("com.example.multipleactivity", Context.MODE_PRIVATE);
                    HashSet<String> set= new HashSet<>(MainActivity.friends);
                    sharedPreferences.edit().putStringSet("notes",set).apply();
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });




    }
}