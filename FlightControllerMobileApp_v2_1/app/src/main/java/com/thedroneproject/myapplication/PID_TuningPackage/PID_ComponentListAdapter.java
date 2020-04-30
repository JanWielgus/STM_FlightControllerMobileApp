package com.thedroneproject.myapplication.PID_TuningPackage;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.thedroneproject.myapplication.R;

public class PID_ComponentListAdapter extends ArrayAdapter<String>
{
    private int layout;

    public PID_ComponentListAdapter(@NonNull Context context, int resource, @NonNull String[] objects)
    {
        super(context, resource, objects);
        layout = resource;
    }


    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent)
    {
        if (convertView == null)
        {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(layout, parent, false);
            final ViewHolder viewHolder = new ViewHolder();
            viewHolder.nameTextView = convertView.findViewById(R.id.componentID_TextView);
            viewHolder.valueExitText = convertView.findViewById(R.id.componentValue_EditText);
            viewHolder.setZeroCheckBox = convertView.findViewById(R.id.componentSetZeroValue_CheckBox);
            viewHolder.increaseButton = convertView.findViewById(R.id.component_increase_Button);
            viewHolder.decreaseButton = convertView.findViewById(R.id.component_decrease_Button);
            viewHolder.valueSeekBar = convertView.findViewById(R.id.component_value_SeekBar);
            convertView.setTag(viewHolder);

            viewHolder.nameTextView.setText(getItem(position));

            // set test on click listener
            viewHolder.increaseButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getContext(), "Dziala jpdl " + getItem(position), Toast.LENGTH_SHORT).show();
                }
            });
        }

        // there to get viewHolder use
        //ViewHolder viewHolder = (ViewHolder)convertView.getTag();

        return convertView;
    }


    private class ViewHolder
    {
        TextView nameTextView;
        EditText valueExitText;
        CheckBox setZeroCheckBox;
        Button increaseButton;
        Button decreaseButton;
        SeekBar valueSeekBar;
    }
}

