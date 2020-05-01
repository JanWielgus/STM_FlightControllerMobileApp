package com.thedroneproject.myapplication.PID_TuningPackage;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.thedroneproject.myapplication.R;

import java.util.ArrayList;
import java.util.List;

public class PID_ComponentListAdapter extends ArrayAdapter<String>
{
    private int layout;
    private List<PidViewHolder> viewHolderList;

    public PID_ComponentListAdapter(@NonNull Context context, int resource, @NonNull String[] objects)
    {
        super(context, resource, objects);

        layout = resource;
        viewHolderList = new ArrayList<>();
    }


    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent)
    {
        if (convertView == null)
        {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(layout, parent, false);
            final PidViewHolder viewHolder = new PidViewHolder();
            viewHolder.nameTextView = convertView.findViewById(R.id.componentID_TextView);
            viewHolder.valueEditText = convertView.findViewById(R.id.componentValue_EditText);
            viewHolder.setZeroCheckBox = convertView.findViewById(R.id.componentSetZeroValue_CheckBox);
            viewHolder.increaseButton = convertView.findViewById(R.id.component_increase_Button);
            viewHolder.decreaseButton = convertView.findViewById(R.id.component_decrease_Button);
            viewHolder.valueSeekBar = convertView.findViewById(R.id.component_value_SeekBar);
            convertView.setTag(viewHolder);
            viewHolderList.add(viewHolder);

            // set the proper controller name
            viewHolder.nameTextView.setText(getItem(position));

            // set the range of seekBar
            viewHolder.valueSeekBar.setMax(300);



            // set test on click listeners

            // Value edit text changed

            viewHolder.valueEditText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    // update values in the current PID bundle
                    EditText valueET = viewHolder.valueEditText;
                    float value = Float.parseFloat(viewHolder.valueEditText.getText().toString());
                    updateControllerPart(position, value); // update values in the PID_Settings

                    // update seek bar
                    //viewHolder.valueSeekBar.setProgress((int)(value*100.f));
                }
            });


            // Seek bar change listener
            viewHolder.valueSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    viewHolder.valueEditText.setText(String.valueOf((float)progress/100.0));
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {

                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {
                    EditText valueET = viewHolder.valueEditText;
                    float value = Float.parseFloat(viewHolder.valueEditText.getText().toString());
                    updateControllerPart(position, value); // update values in the PID_Settings

                    PID_Settings.getInstance().needToSend();
                }
            });


            // Increase button on click
            viewHolder.increaseButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Toast.makeText(getContext(), "Dziala jpdl " + viewHolder.nameTextView.getText(), Toast.LENGTH_SHORT).show();
                    int lastProgress = viewHolder.valueSeekBar.getProgress();
                    lastProgress += PID_Settings.getInstance().getPidSpinnerStep();
                    viewHolder.valueSeekBar.setProgress(lastProgress);

                    PID_Settings.getInstance().needToSend();
                }
            });


            // Decrease button on click
            viewHolder.decreaseButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int lastProgress = viewHolder.valueSeekBar.getProgress();
                    lastProgress -= PID_Settings.getInstance().getPidSpinnerStep();
                    viewHolder.valueSeekBar.setProgress(lastProgress);

                    PID_Settings.getInstance().needToSend();
                }
            });


            // Set zero checkbox
            viewHolder.setZeroCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    boolean otherStuffState = !isChecked;
                    viewHolder.valueEditText.setEnabled(otherStuffState);
                    viewHolder.valueSeekBar.setEnabled(otherStuffState);
                    viewHolder.increaseButton.setEnabled(otherStuffState);
                    viewHolder.decreaseButton.setEnabled(otherStuffState);

                    updateControllerPartFlag(position, isChecked);

                    PID_Settings.getInstance().needToSend();
                }
            });
        }

        // to use viewHolder there:
        //ViewHolder viewHolder = (ViewHolder)convertView.getTag();

        return convertView;
    }


    public List<PidViewHolder> getViewHolderList()
    {
        return viewHolderList;
    }


    public class PidViewHolder
    {
        TextView nameTextView;
        EditText valueEditText;
        CheckBox setZeroCheckBox;
        Button increaseButton;
        Button decreaseButton;
        SeekBar valueSeekBar;
    }


    private void updateControllerPart(int partID, float value)
    {
        // update values in PID_Settings class
        PID_Bundle current = PID_Settings.getInstance().getActiveController();
        if (partID == 0)
            current.setkP(value);
        else if (partID == 1)
            current.setkI(value);
        else if (partID == 2)
            current.setkD(value);
        else if (partID == 3)
            current.setiMax((int)(value*100));
    }

    private void updateControllerPartFlag(int partID, boolean flag)
    {
        PID_Bundle current = PID_Settings.getInstance().getActiveController();
        if (partID == 0)
            current.setkP_ZeroFlag(flag);
        else if (partID == 1)
            current.setkI_ZeroFlag(flag);
        else if (partID == 2)
            current.setkD_ZeroFlag(flag);
        else if (partID == 3)
            current.setiMax_ZeroFlag(flag);
    }
}

