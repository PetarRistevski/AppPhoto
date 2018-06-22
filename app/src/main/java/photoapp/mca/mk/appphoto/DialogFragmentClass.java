package photoapp.mca.mk.appphoto;

import android.app.Activity;
import android.app.DialogFragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

public class DialogFragmentClass extends DialogFragment {

    public static interface OnCompleteListener {
         abstract void onComplete(String time);
    }

    private OnCompleteListener mListener;
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            this.mListener = (OnCompleteListener)activity;
        }
        catch (final ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement OnCompleteListener");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.dataentrydialog, container, false);
        Button bttnOk = rootView.findViewById(R.id.btnOk);
        EditText editText = rootView.findViewById(R.id.etTitle);



        bttnOk.setOnClickListener(view -> {
                mListener.onComplete(String.valueOf(editText.getText()));
                dismiss();

       });



        Button bttnCancel = rootView.findViewById(R.id.btnCancel);
        bttnCancel.setOnClickListener(view -> {

            dismiss();
        });
        return rootView;
    }



}
