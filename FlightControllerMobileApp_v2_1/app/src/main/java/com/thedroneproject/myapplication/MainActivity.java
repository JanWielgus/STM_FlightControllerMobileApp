package com.thedroneproject.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.thedroneproject.myapplication.PID_TuningPackage.PID_TuningActivity;

public class MainActivity extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item)
    {
        int id = item.getItemId();

        if (id == R.id.PID_tuning_menu_item)
        {
            Intent PID_TuningIntent = new Intent(this, PID_TuningActivity.class);
            startActivity(PID_TuningIntent);
        }

        return super.onOptionsItemSelected(item);
    }
}
