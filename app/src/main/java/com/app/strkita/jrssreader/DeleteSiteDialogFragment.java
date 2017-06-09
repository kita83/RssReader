package com.app.strkita.jrssreader;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;

/**
 *
 * Created by strkita on 2017/06/09.
 */

public class DeleteSiteDialogFragment extends DialogFragment {

    public static final  String RESULT_KEY_SITE_ID = "site_id";

    public static DeleteSiteDialogFragment newInstance(Fragment target, int requestCode, long siteId) {
        DeleteSiteDialogFragment fragment = new DeleteSiteDialogFragment();

        Bundle args = new Bundle();
        args.putLong(RESULT_KEY_SITE_ID, siteId);
        fragment.setArguments(args);

        fragment.setTargetFragment(target, requestCode);
        return fragment;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setTitle(R.string.dialog_delete_title)
                .setMessage(R.string.dialog_delete_message)
                .setPositiveButton(R.string.dialog_button_positive, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Fragment fragment = getTargetFragment();

                        if (fragment != null) {
                            Bundle args = getArguments();
                            Intent data = new Intent();
                            data.putExtras(args);

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
