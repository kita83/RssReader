package com.app.strkita.jrssreader;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

/**
 *
 * Created by strkita on 2017/06/09.
 */

public class AddSiteDialogFragment extends DialogFragment {
    public static final String RESULT_KEY_URL = "url";
    private EditText mEditText;

    public static AddSiteDialogFragment newInstance(Fragment target, int requestCode) {
        AddSiteDialogFragment fragment = new AddSiteDialogFragment();

        fragment.setTargetFragment(target, requestCode);

        return fragment;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Context context = getActivity();

        LayoutInflater inflater = LayoutInflater.from(context);
        View contentView = inflater.inflate(R.layout.dialog_input_url, null);

        mEditText = (EditText)contentView.findViewById(R.id.URLEditText);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setTitle(R.string.add_site)
                .setView(contentView)
                .setPositiveButton(R.string.dialog_button_add, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Fragment fragment = getTargetFragment();

                        if (fragment != null) {
                            Intent data = new Intent();
                            data.putExtra(RESULT_KEY_URL, mEditText.getText().toString());
                            fragment.onActivityResult(getTargetRequestCode(), Activity.RESULT_OK, data);
                        }
                    }
                })
                .setNegativeButton(R.string.dialog_button_cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Fragment fragment = getTargetFragment();

                        if (fragment != null) {
                            fragment.onActivityResult(getTargetRequestCode(), Activity.RESULT_CANCELED, null);
                        }
                    }
                });
        return builder.create();
    }
}
