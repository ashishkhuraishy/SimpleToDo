package com.blogspot.codecampanion.simpletodo;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.textfield.TextInputEditText;

public class BottomSheetTask extends BottomSheetDialogFragment {

    private BottomSheetTaskListner listner;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.bottom_sheet_task_activity, container, false);

        final TextInputEditText textInputEditText = v.findViewById(R.id.editTextTaskText);
        Button addButton = v.findViewById(R.id.taskTextAddButton);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String task = textInputEditText.getText().toString();

                if(!task.trim().isEmpty()){
                    listner.onAddClicked(task);
                    Toast.makeText(getContext(), "Sucess", Toast.LENGTH_SHORT).show();
                    dismiss();
                }

                Toast.makeText(getContext(), "Please Enter a SubTask", Toast.LENGTH_SHORT).show();
            }
        });

        return v;
    }

    public interface BottomSheetTaskListner {
        void onAddClicked(String string);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        try {
            listner = (BottomSheetTaskListner) context;
        }catch (ClassCastException e){
            throw new ClassCastException(context.toString()
                    + " implement Methods");
        }
    }
}
