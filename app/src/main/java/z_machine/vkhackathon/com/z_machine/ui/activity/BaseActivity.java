package z_machine.vkhackathon.com.z_machine.ui.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import z_machine.vkhackathon.com.z_machine.core.appinterface.AppBridge;

public abstract class BaseActivity extends AppCompatActivity {

    protected AppBridge appBridge;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        appBridge = (AppBridge) getApplicationContext();
    }
}
