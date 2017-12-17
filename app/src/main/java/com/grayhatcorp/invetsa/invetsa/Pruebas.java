package com.grayhatcorp.invetsa.invetsa;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.widget.TextView;

public class Pruebas extends AppCompatActivity {
TextView tv_imei;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pruebas);
        tv_imei=(TextView)findViewById(R.id.tv_imei);

        TelephonyManager telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        tv_imei.setText("Imei:"+telephonyManager.getDeviceId());
    }
}
