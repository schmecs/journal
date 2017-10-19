package com.schmecs.journal;

import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
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

    SessionManager mSessionManager;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        mSessionManager = new SessionManager(getActivity().getApplicationContext());

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View view = getActivity().getLayoutInflater().inflate(R.layout.entry_dialog, null);
        builder.setView(view);

        final TextInputEditText entryTextInput = (TextInputEditText) view.findViewById(R.id.entry_text_input);

        builder.setTitle("New Journal Entry");

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
                    launchReader();
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

    public void launchReader() {
        Intent intent = new Intent(getActivity(), ReadActivity.class);
        startActivity(intent);
    }

}
