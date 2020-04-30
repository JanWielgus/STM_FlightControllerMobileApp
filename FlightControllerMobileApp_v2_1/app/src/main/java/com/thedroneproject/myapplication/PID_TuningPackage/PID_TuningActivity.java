package com.thedroneproject.myapplication.PID_TuningPackage;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.thedroneproject.myapplication.R;

import java.util.List;

public class PID_TuningActivity extends Activity
{
    Spinner pidControllers_spinner;
    Button pidSend_button;
    CheckBox pidAutoSend_checkbox;
    EditText pidStep_editText;

    String[] PID_controllersList = {"Leveling", "Heading", "AltHold"};
    String[] PID_controllerComponents = {"P", "I", "D", "Imax"};
    PID_ComponentListAdapter pidComponentsAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pid_tuning_activity);

        pidControllers_spinner = findViewById(R.id.pidControllers_spinner);
        pidSend_button = findViewById(R.id.pidSend_button);
        pidAutoSend_checkbox = findViewById(R.id.pidAutoSend_checkbox);
        pidStep_editText = findViewById(R.id.pidStep_EditText);


        // Set-up pid controllers spinner
        pidControllers_spinner.setOnItemSelectedListener(new PID_ControllerSpinnerActions());
        ArrayAdapter pidControllersSpinnerAdapter = new ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, PID_controllersList);
        pidControllers_spinner.setAdapter(pidControllersSpinnerAdapter);


        // Setup the list view
        ListView listView = findViewById(R.id.pidComponentsListView);
        pidComponentsAdapter = new PID_ComponentListAdapter(this, R.layout.pid_component_list_item, PID_controllerComponents);
        listView.setAdapter(pidComponentsAdapter);

        // Setup the PID_Settings class
        PID_Settings.getInstance().createControllersFromNames(PID_controllersList);
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

    public void pidStepChangedOnClick(View view)
    {
        float step = Float.parseFloat(pidStep_editText.getText().toString()) * 100.0f;
        PID_Settings.getInstance().setPidSpinnerStep((int)step);
    }


    class PID_ControllerSpinnerActions implements AdapterView.OnItemSelectedListener
    {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            Toast.makeText(getApplicationContext(), String.valueOf(position), Toast.LENGTH_SHORT).show();

            // TODO Store current value to PID_Settings, because setZero could be selected

            PID_Settings.getInstance().setActiveController(position);

            // update showed values for the current controller after change
            List<PID_ComponentListAdapter.PidViewHolder> viewHolderList = pidComponentsAdapter.getViewHolderList();
            PID_Bundle active = PID_Settings.getInstance().getActiveController();
            setViewHolder(viewHolderList.get(0), active.getkP());
            setViewHolder(viewHolderList.get(1), active.getkI());
            setViewHolder(viewHolderList.get(2), active.getkD());
            setViewHolder(viewHolderList.get(3), active.getiMax());
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }


        private void setViewHolder(PID_ComponentListAdapter.PidViewHolder viewHolder, float value)
        {
            viewHolder.setZeroCheckBox.setChecked(false);
            viewHolder.valueEditText.setText(String.valueOf(value));
            viewHolder.valueSeekBar.setProgress((int)(value*100.0f));
        }
    }
}
