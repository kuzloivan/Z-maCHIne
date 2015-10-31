package z_machine.vkhackathon.com.z_machine.ui;

import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import z_machine.vkhackathon.com.z_machine.R;

/**
 * Created by Ivan on 15.04.2015.
 */
public class MyWaitDialog extends  DialogFragment{

    public static MyWaitDialog initAndShow(Activity pActivity){
        MyWaitDialog myWaitDialog = new MyWaitDialog();
        myWaitDialog.show(pActivity.getFragmentManager(), MyWaitDialog.class.getSimpleName());
        return myWaitDialog;
    }



    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.setCanceledOnTouchOutside(false);
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        return dialog;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setStyle(STYLE_NO_FRAME, getTheme());
        return inflater.inflate(R.layout.wait_dialog,container,false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}
