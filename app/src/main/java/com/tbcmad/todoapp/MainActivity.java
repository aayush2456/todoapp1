package com.tbcmad.todoapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.renderscript.Sampler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.tbcmad.todoapp.model.ETodo;

import java.util.Date;

public class MainActivity extends AppCompatActivity {

    FragmentManager fragmentManager;
    Fragment fragment;
    FloatingActionButton floatingActionButton;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fragmentManager = getSupportFragmentManager();
        fragment = new ListTodoFragment();
        fragmentManager.beginTransaction()
                .replace(R.id.list_activity_container, fragment)
                .commit();

        floatingActionButton = findViewById(R.id.floatingActionButton);



        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, EditActivity.class);
                startActivity(intent);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.mnu_delete_all:
                Toast.makeText(getApplicationContext(),"Delete all", Toast.LENGTH_LONG).show();
                break;
                case R.id.mnu_delete_cpmpleted:
                    Toast.makeText(getApplicationContext(),"Delete Completed", Toast.LENGTH_LONG).show();
                    break;
            case R.id.mnu_logout:

                android.app.AlertDialog.Builder alertDialog = new AlertDialog.Builder(MainActivity.this);
                alertDialog.setMessage(getString(R.string.alert_cancel))
                        .setTitle(getString(R.string.app_name))
                        //.setIcon(R.mipmap.ic_launcher)
                        .setCancelable(false)
                        .setPositiveButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                SharedPreferences preferences = getApplicationContext().getSharedPreferences("todo_pref", 0);
                                SharedPreferences.Editor editor = preferences.edit();
                                editor.clear();
                                editor.commit();



                                Intent intent= new Intent(MainActivity.this, LoginActivity.class);
                                startActivity(intent);
                                finish();

                            }
                        })
                        .setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });

                alertDialog.show();

                break;
        }
        return super.onOptionsItemSelected(item);
    }
}