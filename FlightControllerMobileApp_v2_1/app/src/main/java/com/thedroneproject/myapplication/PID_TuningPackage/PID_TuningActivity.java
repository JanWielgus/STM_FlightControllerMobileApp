package com.thedroneproject.myapplication.PID_TuningPackage;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.thedroneproject.myapplication.R;

public class PID_TuningActivity extends Activity
{
    Spinner pidControllers_spinner;
    Button pidSend_button;
    CheckBox pidAutoSend_checkbox;

    String[] PID_controllersList = {"Leveling", "Heading", "AltHold"};
    String[] PID_controllerComponents = {"P", "I", "D", "Imax"};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pid_tuning_activity);

        pidControllers_spinner = findViewById(R.id.pidControllers_spinner);
        pidSend_button = findViewById(R.id.pidSend_button);
        pidAutoSend_checkbox = findViewById(R.id.pidAutoSend_checkbox);


        // Set-up pid controllers spinner
        pidControllers_spinner.setOnItemSelectedListener(new PID_ControllerSpinnerActions());
        ArrayAdapter pidControllersSpinnerAdapter = new ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, PID_controllersList);
        pidControllers_spinner.setAdapter(pidControllersSpinnerAdapter);


        // Set-up the list view
        //ListAdapter listAdapter = new //...
        // set adapter to the list view: find it by id and then use list.setAdapter...
        // set onItemClickListener on the list view
        ListView listView = findViewById(R.id.pidComponentsListView);
        listView.setAdapter(new PID_ComponentListAdapter(this, R.layout.pid_component_list_item, PID_controllerComponents));

    }


    public void pidSendButtonOnClick(View view)
    {

    }


    public void autoSendingCheckboxOnClick(View view)
    {
        if (pidAutoSend_checkbox.isChecked())
            pidSend_button.setEnabled(false);
        else
            pidSend_button.setEnabled(true);

        // other actions
        // ...
    }


    class PID_ControllerSpinnerActions implements AdapterView.OnItemSelectedListener
    {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            Toast.makeText(getApplicationContext(), String.valueOf(position), Toast.LENGTH_SHORT).show();
            // leveling
            if (position == 0)
            {

            }

            // heading
            else if (position == 1)
            {

            }

            // alt hold
            else if (position == 2)
            {

            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    }
}
