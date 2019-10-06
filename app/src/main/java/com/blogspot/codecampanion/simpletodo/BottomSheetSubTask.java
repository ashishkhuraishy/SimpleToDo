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

public class BottomSheetSubTask extends BottomSheetDialogFragment {

    private BottomSheetListner listner;
    private int subTaskId = -1;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.bottom_sheet_subtask_activity, container, false);
        final TextInputEditText textInputEditText = v.findViewById(R.id.editTextSubText);

        Bundle bundle = getArguments();
        if (bundle != null && bundle.containsKey("SUB_TASK")) {
            textInputEditText.setText(bundle.getString("SUB_TASK"));
            subTaskId = bundle.getInt("SUB_ID");
        }


        Button saveButton = v.findViewById(R.id.subTextSaveButton);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String subTask = textInputEditText.getText().toString();

                if (!subTask.trim().isEmpty()) {
                    listner.onSaveClicked(subTask, subTaskId);
                    Toast.makeText(getContext(), "Sucess", Toast.LENGTH_SHORT).show();
                    dismiss();
                } else {
                    Toast.makeText(getContext(), "Please Enter a SubTask", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return v;
    }

    public interface BottomSheetListner {
        void onSaveClicked(String string, int subTaskId);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        try {
            listner = (BottomSheetListner) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " implement Methods");
        }

    }
}
