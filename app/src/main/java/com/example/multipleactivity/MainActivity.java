package com.example.multipleactivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashSet;

public class MainActivity extends AppCompatActivity {
    static  ArrayList<String> friends = new ArrayList<>();
    static ArrayAdapter arrayAdapter;
    SharedPreferences sharedPreferences;

    public void goToNext(View view){
        Intent intent = new Intent(getApplicationContext(),SecondActivity.class);
        startActivity(intent);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sharedPreferences = getApplicationContext().getSharedPreferences("com.example.multipleactivity", Context.MODE_PRIVATE);;
        ListView listview = findViewById(R.id.list);

        HashSet<String> set = (HashSet<String>) sharedPreferences.getStringSet("notes",null);

        if(set == null){
            friends.add("Ajay");
        }else{
            friends = new ArrayList(set);
        }

        arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1,friends);
        listview.setAdapter(arrayAdapter);

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(),SecondActivity.class);
                intent.putExtra("noteId",position);
                startActivity(intent);
            }
        });

        listview.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                int itemToDelete = position;

               new AlertDialog.Builder(MainActivity.this)
                       .setIcon(android.R.drawable.ic_dialog_alert)
                       .setTitle("Are you sure")
                       .setMessage("Do you want to delete this note")
                       .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                           @Override
                           public void onClick(DialogInterface dialog, int which) {
                                    friends.remove(itemToDelete);
                                    arrayAdapter.notifyDataSetChanged();


                               HashSet<String> set= new HashSet<>(MainActivity.friends);
                               sharedPreferences.edit().putStringSet("notes",set).apply();
                           }
                       })
                       .setNegativeButton("No",null).show();


                return true;
            }
        });

    }
    //MENU

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.add_note_menu,menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
          super.onOptionsItemSelected(item);

          if (item.getItemId() == R.id.add){
              Intent intent = new Intent(getApplicationContext(),SecondActivity.class);
              startActivity(intent);

              return  true;
          }
            return  false;
    }
}