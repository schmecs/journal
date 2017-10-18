package com.schmecs.journal;

import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;

public class EntryFragment extends DialogFragment {

    EntryListener mCallback;

    public interface EntryListener {
        public void onEntrySaved(String content);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception
        try {
            mCallback = (EntryListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement EntryListener");
        }


    }


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View view = getActivity().getLayoutInflater().inflate(R.layout.entry_dialog, null);
        builder.setView(view);

        final TextInputEditText entryTextInput = (TextInputEditText) view.findViewById(R.id.entry_text_input);

        builder.setTitle("New Journal Entry");
        builder.setMessage("Enter Text Below");

        builder.setPositiveButton(R.string.save_entry_dialog, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User clicked OK button
                mCallback.onEntrySaved(entryTextInput.getText().toString());
            }
        });

        builder.setNegativeButton(R.string.cancel_dialog, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User cancelled the dialog
            }
        });

        return builder.create();
    }



}
