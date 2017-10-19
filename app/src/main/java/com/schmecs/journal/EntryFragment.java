package com.schmecs.journal;

import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import com.schmecs.journal.model.Journaldb;
import com.schmecs.journal.model.Post;

import java.util.Date;

public class EntryFragment extends DialogFragment {

//    EntryListener mCallback;
    SessionManager mSessionManager;

//    public interface EntryListener {
//        public void onEntrySaved(String content);
//    }

//    @Override
//    public void onAttach(Context context) {
//        super.onAttach(context);
//
//        // This makes sure that the container activity has implemented
//        // the callback interface. If not, it throws an exception
//        try {
//            mCallback = (EntryListener) context;
//        } catch (ClassCastException e) {
//            throw new ClassCastException(context.toString()
//                    + " must implement EntryListener");
//        }
//
//
//    }


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        mSessionManager = new SessionManager(getActivity().getApplicationContext());

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View view = getActivity().getLayoutInflater().inflate(R.layout.entry_dialog, null);
        builder.setView(view);

        final TextInputEditText entryTextInput = (TextInputEditText) view.findViewById(R.id.entry_text_input);

        builder.setTitle("New Journal Entry");
        builder.setMessage("Enter Text Below");

        builder.setPositiveButton(R.string.save_entry_dialog, new DialogInterface.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            public void onClick(DialogInterface dialog, int id) {
                // User clicked OK button
                String content = entryTextInput.getText().toString();
                boolean isSaved = new Journaldb().insert(new Post(mSessionManager.getUserId(),
                        content,
                        new Date()));
                Log.d("Saved?", String.valueOf(isSaved));

                if (isSaved) {
                    Toast.makeText(getActivity().getApplicationContext(), "Post saved!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getActivity().getApplicationContext(), "Hmm ... something went wrong.", Toast.LENGTH_SHORT).show();
                }
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
